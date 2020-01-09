
package acme.features.sponsor.nonCommercialBanner;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.banners.NonCommercialBanner;
import acme.entities.customization.Customization;
import acme.entities.roles.Sponsor;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface SponsorNonCommercialBannerRepository extends AbstractRepository {

	@Query("select b from NonCommercialBanner b where b.id = ?1")
	NonCommercialBanner findOneNonCommercialBannerById(int id);

	@Query("select b from NonCommercialBanner b where b.sponsor.id = ?1")
	Collection<NonCommercialBanner> findManyBySponsorId(int SponsorId);

	@Query("select s from Sponsor s where s.id = ?1")
	Sponsor findSponsorById(int id);

	@Query("select c from Customization c")
	Customization findCustomization();
}
