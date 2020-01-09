
package acme.features.anonymous.fernandezBulletin;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.fernandezBulletin.FernandezBulletin;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnonymousFernandezBulletinRepository extends AbstractRepository {

	@Query("select f from FernandezBulletin f")
	Collection<FernandezBulletin> findMany();
}
