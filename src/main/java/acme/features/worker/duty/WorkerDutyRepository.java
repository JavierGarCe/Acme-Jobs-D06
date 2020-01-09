
package acme.features.worker.duty;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.jobs.Duty;
import acme.entities.jobs.Job;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface WorkerDutyRepository extends AbstractRepository {

	@Query("select d from Duty d where d.id = ?1")
	Duty findOneById(int id);

	@Query("select d.duties from Job j join j.descriptor d where j.id = ?1")
	Collection<Duty> findDutiesByJobId(int id);

	@Query("select j.id from Job j join j.descriptor.duties d where d.id= ?1")
	Integer findJobIdByDutyId(int id);

	@Query("select count(a) from Application a where a.job.id = ?1 and a.worker.id=?2")
	int findNumberApplicationsByJobId(int id1, int id2);

	@Query("select j from Job j where j.id = ?1")
	Job findJobById(int id);

}
