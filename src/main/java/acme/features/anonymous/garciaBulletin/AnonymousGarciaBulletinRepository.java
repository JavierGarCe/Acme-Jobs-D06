
package acme.features.anonymous.garciaBulletin;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.garciaBulletins.GarciaBulletin;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnonymousGarciaBulletinRepository extends AbstractRepository {

	@Query("select g from GarciaBulletin g")
	Collection<GarciaBulletin> findMany();
}
