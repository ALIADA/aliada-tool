// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortium
package eu.aliada.rdfizer.datasource.rdbms;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Job Stats repository.
 * Provides CRUD access to job stats entity.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public interface JobStatsRepository extends PagingAndSortingRepository<JobStats, Integer> {
	// No need of other methods at the moment
}