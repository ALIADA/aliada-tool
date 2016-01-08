// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PreDestroy;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.semanticweb.yars.nx.Triple;

import com.google.common.util.concurrent.AtomicDouble;

import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.shared.log.Log;

/**
 * RDF store outbound connector.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class AddToRDFStore implements Processor {

	/**
	 * A simple value object that encapsulates a change operation result.
	 * 
	 * @author Andrea Gazzarini
	 * @since 1.0.1
	 */
	final class InsertStatResult {
		private final long elapsed;
		private final int affectedCount;

		/**
		 * Builds a new result with the given data.
		 * 
		 * @param elapsed the elapsed time of the change.
		 * @param affectedCount how many affected triple / quads by the change.
		 */
		InsertStatResult(final long elapsed, final int affectedCount) {
			this.elapsed = elapsed;
			this.affectedCount = affectedCount;
		}
	}

	/**
	 * A {@link Callable} implementation for defining a bulk insert task.
	 * 
	 * @author Andrea Gazzarini
	 * @since 1.0
	 */
	class BulkInsertCallable implements Callable<InsertStatResult> {
		private final List<Triple> statements;
		private final int statementsCount;

		/**
		 * Builds a new task for a given list of statements.
		 * 
		 * @param statements the statement that will be inserted.
		 */
		public BulkInsertCallable(final List<Triple> statements) {
			statementsCount = statements.size();
			this.statements = new ArrayList<Triple>(statements);
		}

		/**
		 * Executes the chunk insert.
		 * 
		 * @return a stat object containing (estimated) inserted triples and elapsed time.
		 * @throws Exception if something goes wrong during the insertion.
		 */
		@Override
		public InsertStatResult call() throws Exception {
			long begin = System.currentTimeMillis();
			final StringBuilder builder = new StringBuilder();
			for (final Triple statement : statements) {
				builder.append(statement).append("\n");
			}
			target.request().post(Entity.text(builder.toString()));
			return new InsertStatResult(System.currentTimeMillis() - begin, statementsCount);
		}
	}	
	private final Log log = new Log(AddToRDFStore.class);
	
	private final ExecutorService workers;
	private int batchSize = 1000;
	private long cleanUpPeriod = 2000;
	
	private final Queue<Future<InsertStatResult>> futures;

	private final List<Triple> buffer;
	
	private AtomicInteger failures = new AtomicInteger();
	
	private AtomicInteger totalJobsCount = new AtomicInteger();	
	
	private AtomicLong triplesIndexed = new AtomicLong();
	private AtomicLong totalElapsed = new AtomicLong();
	
	private AtomicDouble triplesPerSecond = new AtomicDouble();
	
	final Client client;
	final WebTarget target;
	
	private boolean dequeuerIsActive = true;
	private final Runnable dequeuer = new Runnable() {	
		@Override
		public void run() {
			while (dequeuerIsActive) {
				try {
					final Future<InsertStatResult> future = futures.poll();
					if (future != null) {
						final InsertStatResult result = future.get();
						triplesPerSecond.getAndSet(
								((double)triplesIndexed.addAndGet(result.affectedCount) / totalElapsed.addAndGet(result.elapsed)) * 1000);
					} else {
						Thread.sleep(1000);
					}
				} catch (final Exception exception) {
					failures.incrementAndGet();
					log.error(MessageCatalog._00034_NWS_SYSTEM_INTERNAL_FAILURE, exception);
				}
			}
		}
	};
	
	private boolean cleanerIsActive = true;
	private final Runnable cleaner = new Runnable() {	
		@Override
		public void run() {
			while (cleanerIsActive) {
				try {
					if (!buffer.isEmpty()) {
						insertChunk();
					}
					
					Thread.sleep(cleanUpPeriod);
				} catch (final Exception exception) {
					failures.incrementAndGet();
					log.error(MessageCatalog._00034_NWS_SYSTEM_INTERNAL_FAILURE, exception);
				}
			}
		}
	};

	/**
	 * Builds a new connector using the given targets.
	 * 
	 * @param url the REST endpoint of the the target RDF store.
	 * @param batchSize how many statements will be grouped in a single request.
	 * @param cleanUpPeriod the sleep interval of the buffer cleanup worker.
	 */
	public AddToRDFStore(final String url, final int batchSize, final long cleanUpPeriod) {
		client = ClientBuilder.newClient();
		target = client.target(url);

		this.futures = new ConcurrentLinkedQueue<Future<InsertStatResult>>();
		this.buffer = new ArrayList<Triple>(batchSize);
		
		final int howManyWorkers = computeWorkersPoolSize();
		final RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
		workers = new ThreadPoolExecutor(
				howManyWorkers,
				howManyWorkers,
				0L, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(100),
				rejectedExecutionHandler);
		
		this.batchSize = batchSize;
		this.cleanUpPeriod = cleanUpPeriod > 0 ? cleanUpPeriod : 2000;
		workers.execute(dequeuer);
		workers.execute(cleaner);
	}
	
	/**
	 * Computes the worker pool size.
	 * 
	 * @return the worker pool size.
	 */
	private int computeWorkersPoolSize() {
		return Runtime.getRuntime().availableProcessors() + 2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void process(final Exchange exchange) throws Exception {
		final List<Triple> statements = (List<Triple>) exchange.getIn().getBody();
		if (statements != null && !statements.isEmpty()) {
			synchronized (buffer) {
				buffer.addAll(statements);
				if (buffer.size() >= batchSize) {
					insertChunk();
				}
			}
		}
	}
	
	/**
	 * Inserts a new chunk of triples.
	 */
	private void insertChunk() {
		totalJobsCount.incrementAndGet();
		futures.offer(workers.submit(new BulkInsertCallable(buffer)));
		buffer.clear();
	}
	
	/**
	 * Shutdown this processor.
	 */
	@PreDestroy
	public void shutDown() {
		dequeuerIsActive = false;
		cleanerIsActive = false;
		
		// CHECKSTYLE:OFF
		if (workers != null) { try { workers.shutdown(); } catch (Exception ignore){} }
		if (client != null) { try { client.close(); } catch (Exception ignore){} }
		// CHECKSTYLE:ON
	}
	
	/**
	 * Returns how many triples have been inserted by this processor.
	 * 
	 * @return how many triples have been inserted by this processor.
	 */
	public long getTripleIndexed() {
		return triplesIndexed.get();
	}
	
	/**
	 * Returns how many triples per second have been inserted by this processor.
	 * 
	 * @return how many triples per second have been inserted by this processor.
	 */
	public double insertThroughput() {
		return triplesPerSecond.get();
	}
	
	/**
	 * Returns the total number of insert errors detected by this processor.
	 * 
	 * @return the total number of insert errors detected by this processor.
	 */
	public double getFailures() {
		return failures.get();
	}
	
	/**
	 * Returns the total number of insert jobs created by this processor.
	 * 
	 * @return the total number of insert jobs created by this processor.
	 */
	public int getTotalJobsCount() {
		return totalJobsCount.get();
	}
	
	/**
	 * Returns the buffer / chunk / job size currently in use.
	 * 
	 * @return the buffer / chunk / job size currently in use.
	 */
	public int getBufferSize() {
		return buffer.size();
	}
}