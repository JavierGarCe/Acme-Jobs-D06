
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
import acme.framework.services.AbstractCreateService;

@Service
public class SponsorCommercialBannerCreateService implements AbstractCreateService<Sponsor, CommercialBanner> {

	@Autowired
	private SponsorCommercialBannerRepository repository;


	@Override
	public boolean authorise(final Request<CommercialBanner> request) {
		assert request != null;
		return true;
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
		boolean hasCreditCard = !sponsor.getCreditCard().isEmpty();

		request.unbind(entity, model, "picture", "slogan", "targetUrl");
		model.setAttribute("canCreate", hasCreditCard);
	}

	@Override
	public CommercialBanner instantiate(final Request<CommercialBanner> request) {
		assert request != null;

		CommercialBanner result;
		result = new CommercialBanner();

		Principal principal = request.getPrincipal();
		int sponsorId = principal.getActiveRoleId();
		Sponsor sponsor = this.repository.findSponsorById(sponsorId);
		result.setSponsor(sponsor);
		result.setCreditCard(sponsor.getCreditCard());
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
	public void create(final Request<CommercialBanner> request, final CommercialBanner entity) {

		this.repository.save(entity);

	}

}
