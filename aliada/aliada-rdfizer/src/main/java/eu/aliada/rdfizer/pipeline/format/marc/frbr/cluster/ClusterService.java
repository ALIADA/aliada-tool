package eu.aliada.rdfizer.pipeline.format.marc.frbr.cluster;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;

import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.shared.log.Log;

@Component
public class ClusterService {
	private final Log logger = new Log(ClusterService.class);
	
	@Autowired
	private DataSource clusterDataSource;
	
	private Map<String, Cluster> cache = new ConcurrentLinkedHashMap.Builder<String, Cluster>().initialCapacity(1000).maximumWeightedCapacity(50000).build();
	private Set<Integer> processedClusters = new HashSet<Integer>();
	
	public Cluster getCluster(final String heading) {
		Cluster cluster = cache.get(heading);
		if (cluster == null) {
			cluster = loadCluster(heading);
			if (cluster != null) {
				
				cache.put(heading, cluster);
			}
		}
		return cluster;
	}
	
	Cluster loadCluster(final String heading) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try {
			connection = clusterDataSource.getConnection();
			statement = connection.prepareStatement(
					"select * "
					+ "from clstr_grp "
					+ "where clstr_id = (select distinct clstr_id "
					+ "from clstr_grp "
					+ "where name = ?)");
			statement.setString(1, heading);
			rs = statement.executeQuery();
			Cluster cluster = null;
			while (rs.next()) {
				if (cluster == null) {
					cluster = new Cluster(rs.getInt("clstr_id"));
				}
				
				cluster.addEntry(rs.getString("hdg_id"), rs.getString("name"));
			}
			return cluster;
		} catch (final Exception exception) {
			logger.error(MessageCatalog._00031_DATA_ACCESS_FAILURE, exception);
			return null;
		} finally {
			if (rs != null) try { rs.close(); } catch (Exception ignore) { }
			if (statement != null) try { statement.close(); } catch (Exception ignore) { }
			if (connection != null) try { connection.close(); } catch (Exception ignore) { }
		}
	}

	public boolean alreadyProcessed(final int id) {
		return processedClusters.contains(id);
	}

	public void markAsProcessed(int id) {
		processedClusters.add(id);
	}
}
