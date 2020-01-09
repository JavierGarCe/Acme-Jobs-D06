
package acme.features.sponsor.commercialBanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banners.CommercialBanner;
import acme.entities.roles.Sponsor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class SponsorCommercialBannerShowService implements AbstractShowService<Sponsor, CommercialBanner> {

	@Autowired
	SponsorCommercialBannerRepository repository;


	@Override
	public boolean authorise(final Request<CommercialBanner> request) {
		assert request != null;
		boolean result;
		int cBannerId;
		CommercialBanner cBanner;
		Sponsor sponsor;
		Principal principal;
		cBannerId = request.getModel().getInteger("id");
		cBanner = this.repository.findOneCommercialBannerById(cBannerId);
		sponsor = cBanner.getSponsor();
		principal = request.getPrincipal();
		result = sponsor.getUserAccount().getId() == principal.getAccountId();
		return result;
	}

	@Override
	public void unbind(final Request<CommercialBanner> request, final CommercialBanner entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		Principal principal = request.getPrincipal();
		int sponsorId = principal.getActiveRoleId();
		Sponsor sponsor = this.repository.findSponsorById(sponsorId);
		boolean hasCreditCard = !sponsor.getCreditCard().isEmpty();

		request.unbind(entity, model, "picture", "slogan", "targetUrl");
		model.setAttribute("canUpdate", hasCreditCard);

	}

	@Override
	public CommercialBanner findOne(final Request<CommercialBanner> request) {
		assert request != null;

		CommercialBanner result;
		int id;
		id = request.getModel().getInteger("id");
		result = this.repository.findOneCommercialBannerById(id);

		return result;
	}

}
