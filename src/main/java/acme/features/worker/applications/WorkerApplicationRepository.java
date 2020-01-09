
package acme.features.worker.applications;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.applications.Application;
import acme.entities.jobs.Job;
import acme.entities.roles.Worker;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface WorkerApplicationRepository extends AbstractRepository {

	@Query("select a from Application a where a.id = ?1")
	Application findOneById(int id);

	@Query("select a from Application a where a.worker.id = ?1")
	Collection<Application> findManyByWorkerId(int WorkerId);

	@Query("select j.id from Application a join a.job j where a.id= ?1")
	int findId(int id);

	@Query("select j from Job j where j.id = ?1")
	Job findJobById(int id);

	@Query("select w from Worker w where w.id = ?1")
	Worker findWorkerById(int id);

	@Query("select a from Application a where a.reference = ?1")
	Application findOneByReference(String reference);

	@Query("select count(a) from Application a where a.worker.id = ?1 and job.id = ?2")
	Integer findNumberApplicationsByWorkerIdAndJobId(int workerId, int jobId);

}
