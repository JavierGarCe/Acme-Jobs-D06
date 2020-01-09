
package acme.features.anonymous.romeroBulletin;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.romeroBulletins.RomeroBulletin;
import acme.framework.repositories.AbstractRepository;

public interface AnonymousRomeroBulletinRepository extends AbstractRepository {

	@Query("select r from RomeroBulletin r")
	Collection<RomeroBulletin> findMany();

}
