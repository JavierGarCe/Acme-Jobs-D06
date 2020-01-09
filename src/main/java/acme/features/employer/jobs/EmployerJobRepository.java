
package acme.features.employer.jobs;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.auditRecords.AuditRecord;
import acme.entities.customization.Customization;
import acme.entities.jobs.Duty;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface EmployerJobRepository extends AbstractRepository {

	@Query("select j from Job j where j.id = ?1")
	Job findOneById(int id);

	@Query("select j from Job j where j.employer.id = ?1")
	Collection<Job> findManyByEmployerId(int EmployerId);

	@Query("select e from Employer e where e.id = ?1")
	Employer findEmployerById(int id);

	@Query("select sum(d.percentage) from Job j join j.descriptor.duties d where j.id = ?1")
	Integer countPercentage(int id);

	@Query("select count(a) from Application a where a.job.id = ?1")
	Integer countApplicationById(int id);

	@Query("select a from AuditRecord a where a.job.id = ?1")
	Collection<AuditRecord> fintAuditRecordsById(int id);

	@Query("select j.descriptor.duties from Job j where j.id = ?1")
	Collection<Duty> findManyDutiesByJobId(int jobId);

	@Query("select j from Job j where j.reference = ?1")
	Job findOneByReference(String reference);

	@Query("select c from Customization c")
	Customization getCustomization();
}
