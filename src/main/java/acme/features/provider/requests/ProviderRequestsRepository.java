
package acme.features.provider.requests;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.requests.Requests;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface ProviderRequestsRepository extends AbstractRepository {

	@Query("select r from Requests r where r.ticker = ?1")
	Requests findOnebyTicker(String ticker);

}
