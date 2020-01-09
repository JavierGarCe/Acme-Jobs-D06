
package acme.features.employer.auditRecords;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.auditRecords.AuditRecord;
import acme.entities.jobs.Job;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface EmployerAuditRecordRepository extends AbstractRepository {

	@Query("select b from AuditRecord b where b.id = ?1")
	AuditRecord findOneAuditRecordById(int id);

	@Query("select b from AuditRecord b where b.job.id = ?1 and b.status=1")
	Collection<AuditRecord> findManyByJobId(int SponsorId);
	@Query("select b from AuditRecord b")
	Collection<AuditRecord> findMany();

	@Query("select j from Job j where j.id = ?1")
	Job findOneJobById(int id);

	@Query("select a.job.id from AuditRecord a where a.id= ?1")
	Integer findJobIdByAuditRecordId(int id);

}
