
package acme.features.authenticated.auditRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.auditRecords.AuditRecord;
import acme.entities.jobs.Job;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedAuditRecordRepository extends AbstractRepository {

	@Query("select b from AuditRecord b where b.id = ?1")
	AuditRecord findOneAuditRecordById(int id);

	@Query("select b from AuditRecord b where b.job.id = ?1 and b.status != 'DRAFT'")
	Collection<AuditRecord> findManyByJobId(int SponsorId);

	@Query("select b from AuditRecord b")
	Collection<AuditRecord> findMany();

	@Query("select j from Job j where j.id = ?1")
	Job findJobById(int idJob);
}
