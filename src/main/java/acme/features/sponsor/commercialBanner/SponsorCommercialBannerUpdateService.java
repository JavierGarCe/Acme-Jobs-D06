
package acme.features.sponsor.commercialBanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banners.CommercialBanner;
import acme.entities.customization.Customization;
import acme.entities.roles.Sponsor;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.helpers.StringHelper;
import acme.framework.services.AbstractUpdateService;

@Service
public class SponsorCommercialBannerUpdateService implements AbstractUpdateService<Sponsor, CommercialBanner> {

	@Autowired
	private SponsorCommercialBannerRepository repository;


	@Override
	public boolean authorise(final Request<CommercialBanner> request) {
		assert request != null;

		Principal principal = request.getPrincipal();
		int CommercialBannerId = request.getModel().getInteger("id");
		CommercialBanner banner = this.repository.findOneCommercialBannerById(CommercialBannerId);
		int sponsorId = principal.getActiveRoleId();
		Sponsor sponsor = this.repository.findSponsorById(sponsorId);
		boolean hasCreditCard = !sponsor.getCreditCard().isEmpty();

		return banner.getSponsor().getId() == principal.getActiveRoleId() && hasCreditCard;
	}

	@Override
	public void bind(final Request<CommercialBanner> request, final CommercialBanner entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<CommercialBanner> request, final CommercialBanner entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		Principal principal = request.getPrincipal();
		int sponsorId = principal.getActiveRoleId();
		Sponsor sponsor = this.repository.findSponsorById(sponsorId);
		boolean hasCreditCard = sponsor.getCreditCard() != null;

		request.unbind(entity, model, "picture", "slogan", "targetUrl");
		model.setAttribute("canUpdate", hasCreditCard);
	}

	@Override
	public CommercialBanner findOne(final Request<CommercialBanner> request) {
		assert request != null;

		CommercialBanner result;
		int id = request.getModel().getInteger("id");

		result = this.repository.findOneCommercialBannerById(id);

		return result;
	}

	@Override
	public void validate(final Request<CommercialBanner> request, final CommercialBanner entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Customization customization = this.repository.findCustomization();
		if (!StringHelper.isBlank(customization.getSpamword())) {
			String[] spamWords = customization.getSpamword().toLowerCase().split(",");
			Double threshold = customization.getThreshold();

			if (!errors.hasErrors("picture")) {
				String picture = entity.getPicture().toLowerCase();
				Double numberLetters = new Double(picture.length());
				Double numberSpamWordsInLetters = 0.0;
				for (String s : spamWords) {
					if (picture.contains(s.trim())) {
						numberSpamWordsInLetters += s.length();
					}
				}
				Double percentageSpam = numberSpamWordsInLetters / numberLetters * 100.0;
				Boolean notSpam = percentageSpam < threshold;
				errors.state(request, notSpam, "picture", "error.CommercialBanner.input.spam");
			}

			if (!errors.hasErrors("slogan")) {
				String slogan = entity.getSlogan().toLowerCase();
				Double numberLetters = new Double(slogan.length());
				Double numberSpamWordsInLetters = 0.0;
				for (String s : spamWords) {
					if (slogan.contains(s.trim())) {
						numberSpamWordsInLetters += s.length();
					}
				}
				Double percentageSpam = numberSpamWordsInLetters / numberLetters * 100.0;
				Boolean notSpam = percentageSpam < threshold;
				errors.state(request, notSpam, "slogan", "error.CommercialBanner.input.spam");
			}

		}

	}

	@Override
	public void update(final Request<CommercialBanner> request, final CommercialBanner entity) {
		assert request != null;
		assert entity != null;
		Principal principal = request.getPrincipal();
		int sponsorId = principal.getActiveRoleId();
		Sponsor sponsor = this.repository.findSponsorById(sponsorId);
		entity.setCreditCard(sponsor.getCreditCard());

		this.repository.save(entity);

	}

}
