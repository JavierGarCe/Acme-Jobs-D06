
package acme.features.authenticated.investorRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.investorRecord.InvestorRecord;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedInvestorRecordRepository extends AbstractRepository {

	@Query("select h from InvestorRecord h where h.id = ?1")
	InvestorRecord findOneById(int id);

	@Query("select h from InvestorRecord h")
	Collection<InvestorRecord> findManyAll();

}
