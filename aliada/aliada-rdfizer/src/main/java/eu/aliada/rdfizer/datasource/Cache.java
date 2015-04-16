// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.datasource;

import static eu.aliada.shared.Strings.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.aliada.rdfizer.datasource.rdbms.JobInstance;
import eu.aliada.rdfizer.datasource.rdbms.JobInstanceRepository;
import eu.aliada.rdfizer.datasource.rdfstore.AliadaRDFStoreDAO;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;

/** 
 * A simple in-memory cache we used to avoid unnecessary datasource accesses.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
@Component
public class Cache {
	
	static final String DEFAULT_ALIADA_CLASS = "http://erlangen-crm.org/current/E18_Physical_Thing";
	final Map<String, String> crm2AliadaClasses = new HashMap<String, String>();
	{
		crm2AliadaClasses.put("E22", "http://erlangen-crm.org/current/E22_Man-Made_Object");
		crm2AliadaClasses.put("E25", "http://erlangen-crm.org/current/E25_Man-Made_Feature");
		crm2AliadaClasses.put("E78", "http://erlangen-crm.org/current/E78_Collection");	
		crm2AliadaClasses.put("E78", "http://erlangen-crm.org/current/E78_Collection");	
	}
	
	final Map<String, String> crm2AliadaEventTypes = new HashMap<String, String>();
	{
		crm2AliadaEventTypes.put("http://www.cidoc-crm.org/crm-concepts/E65", "http://erlangen-crm.org/current/E65_Creation");
		crm2AliadaEventTypes.put("http://terminology.lido-schema.org/lido00012", "http://erlangen-crm.org/current/E65_Creation");
	}
	
	final ConcurrentMap<Integer, JobInstance> activeJobConfigurations 
		= new ConcurrentLinkedHashMap
			.Builder<Integer, JobInstance>()
			.maximumWeightedCapacity(50)
			.build();
	
	final Map<String, Pattern> startPatterns = new HashMap<String, Pattern>();
	final Map<String, Pattern> endPatterns = new HashMap<String, Pattern>();
	
	@Autowired
	AliadaRDFStoreDAO rdfStore;
	
	@Autowired
	JobInstanceRepository jobInstanceRepository;
	
	/**
	 * Returns the ALIADA class corresponding to the given CIDOC-CRM class.
	 * 
	 * @param crmClass the CIDOC-CRM class.
	 * @return the ALIADA class that corresponds to the given input class.
	 */
	public String getAliadaClassFrom(final String crmClass) {
		String uri = null;
		if (crmClass != null) {
			uri = crm2AliadaClasses.get(crmClass);
			if (uri == null) {
				uri = rdfStore.crm2AliadaClass(crmClass);
			}
		}
		return uri != null ? uri : DEFAULT_ALIADA_CLASS;
	}
	
	public Pattern getStartPattern(final String regex) {
		Pattern p = startPatterns.get(regex);
		if (p == null) {
			p = Pattern.compile("<(" + regex + ")>");
			startPatterns.put(regex, p);
		}
		
		return p;
	}
	
	public Pattern getEndPattern(final String regex) {
		Pattern p = endPatterns.get(regex);
		if (p == null) {
			p = Pattern.compile("</(" + regex + ")>");
			endPatterns.put(regex, p);
		}
		
		return p;
	}	
	
	/**
	 * Returns the ALIADA event type class corresponding to the given CIDOC-CRM class.
	 * 
	 * @param crmClass the CIDOC-CRM event type class.
	 * @return the ALIADA class that corresponds to the given input class.
	 */
	public String getAliadaEventTypeClassFrom(final String crmClass) {
		String uri = crm2AliadaEventTypes.get(crmClass);
		if (uri == null) {
			uri = rdfStore.crm2AliadaClass(crmClass);
		}
		return uri;
	}

	/**
	 * Returns the job instance associated with a given identifier.
	 * 
	 * @param id the job identifier.
	 * @return the job instance associated with a given identifier (or null).
	 */
	public JobInstance getJobInstance(final Integer id) {
		JobInstance instance = activeJobConfigurations.get(id);
		if (instance == null) {
			instance = jobInstanceRepository.findOne(id);
			if (instance != null) {
				adjustPrefixes(instance);
				activeJobConfigurations.put(id, instance);
			}
		}
		return instance;
	}

	/**
	 * Removes the job instance associated with the given identifier.
	 * 
	 * @param jobId the job instance associated with the given identifier.
	 */
	public void removeJobInstance(final Integer jobId) {
		activeJobConfigurations.remove(jobId);
	}
	
	private void adjustPrefixes(final JobInstance instance) {
		String prefix = instance.getNamespace();
		if (!prefix.endsWith("/")) {
			instance.setNamespace(prefix + "/");
		}
		
		prefix = instance.getGraphName();
		if (isNotNullAndNotEmpty(prefix) && prefix.endsWith("/")) {
			instance.setGraphName(prefix.substring(0, (prefix.length() - 1) ));
		}
		
		prefix = instance.getAliadaOntologyNamespace();
		if (!prefix.endsWith("#")) {
			instance.setAliadaOntologyNamespace(prefix + "#");
		}
		
		jobInstanceRepository.save(instance);
	}
}