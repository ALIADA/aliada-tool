package eu.aliada.rdfizer.pipeline.format.marc.frbr.cluster;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;

/**
 * Integration service between the conversion pipeline and the database names / works cluster.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
@Component
public class ClusterService {
	@Autowired
	private DataSource clusterDataSource;
	
	private Map<String, Cluster> cachedNameClusters = new ConcurrentLinkedHashMap.Builder<String, Cluster>().initialCapacity(1000).maximumWeightedCapacity(50000).build();
	private Map<String, Cluster> cachedTitleClusters = new ConcurrentLinkedHashMap.Builder<String, Cluster>().initialCapacity(1000).maximumWeightedCapacity(50000).build();
	private Set<Integer> processedNameClusters = new HashSet<Integer>();
	private Set<Integer> processedTitleClusters = new HashSet<Integer>();
	
	/**
	 * Returns the name {@link Cluster} associated with the given heading.
	 * NOTE: although the heading is a string, the current implementation assumes that is actually 
	 * the cluster identifier (i.e. an integer).
	 * 
	 * @param heading the cluster search criterion.
	 * @return the name {@link Cluster} associated with the given heading.
	 * @param titlesClusters the list of title clusters associated with this heading.
	 * @throws SQLException in case of data access failure.
	 */
	public Cluster getNameCluster(final String heading, final Set<Cluster> titlesClusters) throws SQLException {
		Cluster cluster = cachedNameClusters.get(heading);
		if (cluster == null) {
			cluster = loadNameCluster(heading);
			if (cluster != null) {
				cachedNameClusters.put(heading, cluster);
			} else {
				// Do not cache fake clusters!
				return new FakeCluster(heading);
			}
		}
		return cluster;
	}
	
	/**
	 * Returns the title {@link Cluster} associated with the given heading.
	 * NOTE: although the heading is a string, the current implementation assumes that is actually 
	 * the cluster identifier (i.e. an integer).
	 * 
	 * @param heading the cluster search criterion.
	 * @return the title {@link Cluster} associated with the given heading.
	 * @throws SQLException in case of data access failure.
	 */
	public Cluster getTitleCluster(final String heading) throws SQLException {
		Cluster cluster = cachedTitleClusters.get(heading);
		if (cluster == null) {
			cluster = loadTitleCluster(heading);
			if (cluster != null) {
				cachedNameClusters.put(heading, cluster);
			} else {
				// Do not cache fake clusters!
				return new FakeCluster(heading);
			}
		}
		return cluster;
	}
	
	/**
	 * Checks if the name cluster associated with the given identifier has been already processed.
	 * 
	 * @param id the cluster identifier.
	 * @return true if the name cluster associated with the given identifier has been already processed.
	 */
	public boolean nameClusterAlreadyProcessed(final int id) {
		return processedNameClusters.contains(id);
	}

	/**
	 * Marks the name cluster associated with the given identifier as processed.
	 * 
	 * @param id the cluster identifier.
	 */
	public void markNameClusterAsProcessed(int id) {
		processedNameClusters.add(id);
	}
	
	/**
	 * Checks if the title cluster associated with the given identifier has been already processed.
	 * 
	 * @param id the cluster identifier.
	 * @return true if the title cluster associated with the given identifier has been already processed.
	 */
	public boolean titleClusterAlreadyProcessed(final int id) {
		return processedTitleClusters.contains(id);
	}

	/**
	 * Marks the title cluster associated with the given identifier as processed.
	 * 
	 * @param id the cluster identifier.
	 */
	public void markTitleClusterAsProcessed(int id) {
		processedTitleClusters.add(id);
	}	
	
	/**
	 * Loads, from the database, the name {@link Cluster} associated with the given heading.
	 * 
	 * @param heading the cluster search criterion.
	 * @return the name {@link Cluster} associated with the given heading.
	 * @throws SQLException in case of data access failure.
	 */
	void loadTitlesBelongingToACluster(final Cluster nameCluster, final Connection connection) throws SQLException {
		try (final PreparedStatement statement = connection.prepareStatement("select clstr_ttl_id from clstr_nme_ttl where clstr_nme_id = ?")) {
			statement.setInt(1, nameCluster.getId());
			try( final ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					nameCluster.addParent(rs.getInt("clstr_ttl_id"));
				}
			} 
		}
	}
	
	/**
	 * Loads, from the database, the name {@link Cluster} associated with the given heading.
	 * 
	 * @param heading the cluster search criterion.
	 * @return the name {@link Cluster} associated with the given heading.
	 * @throws SQLException in case of data access failure.
	 */
	Cluster loadNameCluster(final String heading) throws SQLException {
		try (final Connection connection = clusterDataSource.getConnection()) {
			try (final PreparedStatement statement = connection.prepareStatement("select * from clstr_nme_grp where clstr_id = ?")) {
				statement.setInt(1, Integer.parseInt(heading));
				try( final ResultSet rs = statement.executeQuery()) {
					Cluster cluster = null;
					while (rs.next()) {
						if (cluster == null) {
							cluster = new Cluster(rs.getInt("clstr_id"));
						}
						
						cluster.addEntry(
								new ClusterEntry(
										rs.getString("name"), 
										"t".equals(rs.getString("pref_frm")),
										rs.getString("hdg_id"),
										null));						
					}
					loadTitlesBelongingToACluster(cluster, connection);
					return cluster;
				}
			}
		}
	}
	
	/**
	 * Loads, from the database, the title {@link Cluster} associated with the given heading.
	 * 
	 * @param heading the cluster search criterion.
	 * @return the title {@link Cluster} associated with the given heading.
	 * @throws SQLException in case of data access failure.
	 */
	//FIXME: Insert proper query
	Cluster loadTitleCluster(final String heading) throws SQLException {
		try (final Connection connection = clusterDataSource.getConnection()) {
			try (final PreparedStatement statement = connection.prepareStatement("select clstr_id,ttl_hdg_id,ttl_str_txt,viaf_id,typ_ttl from ttl_hdg where clstr_id = ? and (typ_ttl is null or typ_ttl in ('TU','TV'))")) {
				statement.setInt(1, Integer.parseInt(heading));
				try( final ResultSet rs = statement.executeQuery()) {
					Cluster cluster = null;
					while (rs.next()) {
						if (cluster == null) {
							cluster = new Cluster(rs.getInt("clstr_id"));
						}
						
						cluster.addEntry(
								new ClusterEntry(
										rs.getString("ttl_hdg_id"), 
										rs.getString("typ_ttl") == null,
										rs.getString("ttl_hdg_id"),
										rs.getString("viaf_id")));						
					}
					return cluster;
				}
			}
		}
	}
}