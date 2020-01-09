
package acme.features.auditor.Job;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.jobs.Job;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorJobRepository extends AbstractRepository {

	@Query("select j from Job j where j.id = ?1")
	Job findOneJobById(int JobId);

	@Query("select distinct b.job from AuditRecord b where b.auditor.id = ?1")
	Collection<Job> findManyByAuditorId(int AuditorId);

	@Query("select j from Job j where j not in (select b.job from AuditRecord b where b.auditor.id = ?1) and j.deadline > CURRENT_TIMESTAMP and j.status=1")
	Collection<Job> findManyByAuditorIdNoMine(int AuditorId);

	@Query("select count(a) from AuditRecord a where a.job.id=?1 and a.auditor.id = ?2")
	Integer countMyAuditRecords(int jobId, int auditorId);

	@Query("select j from Job j ")
	Collection<Job> findAllJobs();

}
