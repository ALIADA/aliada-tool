// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import eu.aliada.rdfizer.Constants;
import eu.aliada.rdfizer.datasource.Cache;
import eu.aliada.rdfizer.datasource.rdbms.JobInstance;
import eu.aliada.rdfizer.datasource.rdbms.JobInstanceRepository;
import eu.aliada.rdfizer.datasource.rdbms.JobStats;
import eu.aliada.rdfizer.datasource.rdbms.JobStatsRepository;
import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.rdfizer.mx.InMemoryJobResourceRegistry;
import eu.aliada.rdfizer.mx.ManagementRegistrar;
import eu.aliada.rdfizer.rest.JobResource;
import eu.aliada.shared.log.Log;

/**
 * A processor that captures and collect the (XML) incoming stream.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class CaptureValidationSampleAndRunValidation implements Processor {
	private final Log log = new Log(CaptureValidationSampleAndRunValidation.class); 
	
	private final String recordElementName;
	private final String recordElementNameWithNamespace;
	
	private ProducerTemplate validationChannelProducer;
	
	@Autowired
	protected Cache cache;
	
	@Autowired
	protected InMemoryJobResourceRegistry jobRegistry;
		
	@Autowired
	protected JobInstanceRepository jobInstanceRepository;
	
	@Autowired
	protected JobStatsRepository jobStatsRepository;
	
	protected int size;
	
	public void setProducer(final ProducerTemplate validationChannelProducer)
	{
		this.validationChannelProducer = validationChannelProducer;
	}
	
	/**
	 * Builds a new {@link CaptureValidationSampleAndRunValidation} with the following element name (as a split criteria).
	 * 
	 * @param recordElementName the record element name.
	 */
	public CaptureValidationSampleAndRunValidation(final String recordElementName, final int size) {
		this.recordElementName = recordElementName + ">";
		this.recordElementNameWithNamespace = ":" + recordElementName + ">";
		this.size = size;
	}
	
	@Override
	public void process(final Exchange exchange) throws Exception {
		final File inputFile = exchange.getIn().getBody(File.class);
		
		final Integer jobId = exchange.getIn().getHeader(Constants.JOB_ID_ATTRIBUTE_NAME, Integer.class);
		final JobInstance configuration = cache.getJobInstance(jobId);
		if (configuration == null) {
			log.error(MessageCatalog._00038_UNKNOWN_JOB_ID, jobId);
			throw new IllegalArgumentException(String.valueOf(jobId));
		}

		BufferedReader reader = null;
		
		final List<String> validationSample = new ArrayList<String>(size);
		try {
			reader = new BufferedReader(new FileReader(inputFile));
			final StringBuilder builder = new StringBuilder();
			String actLine = null;
			boolean withinRecord = false;
			int count = 0;
			while ( (actLine = reader.readLine()) != null && count < size) {
				if (actLine.contains(recordElementNameWithNamespace) || actLine.equals(recordElementName)) {
					if (withinRecord) {
						builder.append(actLine);
						validationSample.add(builder.toString());
						count++;
						withinRecord = false;
					} else {
						withinRecord = true;
					}
				}
				
				if (withinRecord) {
					builder.append(actLine);
				}
				
			}
		} catch (final Exception exception) {
			log.error(MessageCatalog._00027_INPUSTREAM_IO_FAILURE, exception, jobId);
		} finally {
			try { reader.close(); } catch (Exception ignore) { }
		}
		
		for (final String record : validationSample) {
			validationChannelProducer.send(new Processor() {
				public void process(final Exchange outExchange) {
					outExchange.getIn().getHeaders().putAll(exchange.getIn().getHeaders());
					outExchange.getIn().setHeader(Constants.SAMPLE_SIZE, size);
					outExchange.getIn().setBody(record);
				}
			});
		}
		
		final JobResource resource = jobRegistry.getJobResource(jobId);
		if (!resource.isRunning()) {
			markJobAsCompleted(resource);
			persistJobStats(resource);
			unregisterJobFromMxSystem(resource);
			removeFromInMemoryCache(resource);
			exchange.setProperty(Exchange.ROUTE_STOP, Boolean.TRUE); 
		}
	}
	
	/**
	 * Marks a given job as completed.
	 * 
	 * @param job the job.
	 */
	void markJobAsCompleted(final JobResource job) {
		final JobInstance instance = cache.getJobInstance(job.getID());
		instance.setEndDate(new Timestamp(System.currentTimeMillis()));
		jobInstanceRepository.save(instance);		
	
		log.info(MessageCatalog._00048_JOB_COMPLETED, job.getID());
	}		
	
	/**
	 * Records the stats of a given (completed) job.
	 * 
	 * @param job the job.
	 */
	void persistJobStats(final JobResource job) {
		final JobStats stats = new JobStats();
		stats.setId(job.getID());
		stats.setTotalRecordsCount(job.getTotalRecordsCount());
		stats.setTotalTriplesProduced(job.getOutputStatementsCount());
		stats.setRecordsThroughput(
				job.getRecordsThroughput() > 0 
					? BigDecimal.valueOf(job.getRecordsThroughput())
					: BigDecimal.ZERO);
		stats.setTriplesThroughput(
				job.getStatementsThroughput() > 0
					? BigDecimal.valueOf(job.getStatementsThroughput())
					: BigDecimal.ZERO);		
		stats.setStatusCode(-1);
		jobStatsRepository.save(stats);		
	}		
	
	/**
	 * Unregisters a job from managemenent (sub)system.
	 * 
	 * @param job the job.
	 */
	void unregisterJobFromMxSystem(final JobResource job) {
		ManagementRegistrar.unregister(
				ManagementRegistrar.createJobObjectName(
						job.getFormat(), 
						job.getID()));
		
		jobRegistry.removeJob(job.getID());
	}
	
	/**
	 * Removes a job from local in memory cache.
	 * 
	 * @param job the job.
	 */
	void removeFromInMemoryCache(final JobResource job) {
		cache.removeJobInstance(job.getID());		
	}		
}