// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortiums
package eu.aliada.ckancreation;

import eu.aliada.ckancreation.impl.DatasetDescFile;
import eu.aliada.ckancreation.model.DumpFileInfo;
import eu.aliada.ckancreation.model.JobConfiguration;
import eu.aliada.ckancreation.model.Subset;
import eu.aliada.shared.log.Log;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

/**
 * Test {@link DatasetDescFile} class functions
 * 
 * @author Idoia Murua
 * @since 2.0
 */
public class DatasetDescFileTest {
	/** For creating random variable values. */
	static final Random RANDOMIZER = new Random();
	/** For logging. */
	private static final Log LOGGER = new Log(DatasetDescFileTest.class);

    /**
     * Test the encodeParams method.
     * 
     * @since 2.0
     */
    @Test
    public void testdDatasetDescFile() {
		final JobConfiguration jobConf = newJobConfiguration();
		final ArrayList<DumpFileInfo> datasetDumpFileInfoList = newDatasetDumpFileInfoList();
		final String ckanDatasetUrl = "http://datahub.io/es/";
		final int numTriples = 1000;
		final String dataFolderName = "src/test/resources/";
		final String dataFolderURL = "http://aliada.com/";
		final DatasetDescFile datasetDescFile = new DatasetDescFile(jobConf, ckanDatasetUrl, 
				datasetDumpFileInfoList, numTriples, dataFolderName, dataFolderURL);
		final boolean result = datasetDescFile.createFile();
        if (result) {
        	LOGGER.info("OK");
        } else {
        	LOGGER.info("NOK");
        }
    }
    
	/**
	 * Returns a random identifier (as integer).
	 * 
	 * @return a random identifier (as integer).
     * @since 2.0
	 */
	public static Integer randomIdentifier() {
		return RANDOMIZER.nextInt();
	}

	/**
	 * Creates a dummy job configuration. 
	 * 
	 * @return a dummy job configuration.
     * @since 2.0
	 */
	public static ArrayList<DumpFileInfo> newDatasetDumpFileInfoList() {
		final ArrayList<DumpFileInfo> datasetDumpFileInfoList = new ArrayList<DumpFileInfo>();  
		final DumpFileInfo dumpFileInfo = new DumpFileInfo();
		final String dumpFileFormat= "application/x-gzip";
		final String dumpFilePath= "/home/virtuoso/var/lib/virtuoso/vsp/aliadascanbitorg/datadumps";
		final String dumpFileUrl= "http://aliada.scanbit.org/datadumps/biblioteca_artium_org20150112000001.nt.gz";
		dumpFileInfo.setDumpFileFormat(dumpFileFormat);
		dumpFileInfo.setDumpFilePath(dumpFilePath);
		dumpFileInfo.setDumpFileUrl(dumpFileUrl);
		datasetDumpFileInfoList.add(dumpFileInfo);
		return datasetDumpFileInfoList;
	}

	/**
	 * Creates a dummy job configuration. 
	 * 
	 * @return a dummy job configuration.
     * @since 2.0
	 */
	public static JobConfiguration newJobConfiguration() {
		final JobConfiguration job = new JobConfiguration();
		job.setId(randomIdentifier());
		job.setStoreIp("aliada.scanbit.net");
		job.setStoreSqlPort(1111);
		job.setSqlLogin("sql_login");
		job.setSqlPassword("sql_password");
		job.setUriDocPart("doc");
		job.setDomainName("data.artium.org");
		job.setOntologyUri("http://aliada-project.eu/2014/aliada-ontology#");
		job.setUriConceptPart("collections");
		job.setVirtualHost("*ini*");
		job.setIsqlCommandPath("isql");
		job.setDatasetAuthor("Aliada Consortium");
		job.setDatasetDesc("Dataset containing ARTIUM bibliogarphy and museum data as Linked Data.");
		job.setDatasetLongDesc("Dataset containing ARTIUM bibliogarphy as Linked Data.");
		job.setDatasetSourceURL("http://www.artium.org");
		job.setLicenseCKANId("cc-zero");
		job.setLicenseURL("http://creativecommons.org/publicdomain/zero/1.0/");
		job.setNumTriples(1000);
		job.setPublicSparqlEndpointUri("http://aliada.scanbit.org:8891/sparql");
		job.setVirtHttpServRoot("/home/virtuoso/var/lib/virtuoso/vsp");
		job.setOrgName("ARTIUM");
		final Subset subset = new Subset();
		subset.setDescription("Library bibliography");
		subset.setGraph("http://aliada_graph1");
		subset.setLinksGraph("http://aliada_graph1.links");
		subset.setUriConceptPart("library/bib");
		subset.setGraphNumTriples(900);
		subset.setLinksGraphNumTriples(100);
		job.setSubset(subset);
		return job;
	}

}
