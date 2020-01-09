
package acme.features.authenticated.job;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.jobs.Job;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedJobRepository extends AbstractRepository {

	@Query("select j from Job j where j.id = ?1")
	Job findOneById(int id);

	@Query("select j from Job j where j.deadline > CURRENT_TIMESTAMP and j.status=1") //PUBLISHED es 1
	Collection<Job> findManyActive();

}
