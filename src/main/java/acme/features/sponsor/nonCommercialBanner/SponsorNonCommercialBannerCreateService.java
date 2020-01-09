
package acme.features.sponsor.nonCommercialBanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banners.NonCommercialBanner;
import acme.entities.customization.Customization;
import acme.entities.roles.Sponsor;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.helpers.StringHelper;
import acme.framework.services.AbstractCreateService;

@Service
public class SponsorNonCommercialBannerCreateService implements AbstractCreateService<Sponsor, NonCommercialBanner> {

	@Autowired
	private SponsorNonCommercialBannerRepository repository;


	@Override
	public boolean authorise(final Request<NonCommercialBanner> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<NonCommercialBanner> request, final NonCommercialBanner entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<NonCommercialBanner> request, final NonCommercialBanner entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "picture", "targetUrl", "slogan", "jingle");
	}

	@Override
	public NonCommercialBanner instantiate(final Request<NonCommercialBanner> request) {
		NonCommercialBanner result;
		result = new NonCommercialBanner();

		Principal principal = request.getPrincipal();
		int sponsorId = principal.getActiveRoleId();
		Sponsor sponsor = this.repository.findSponsorById(sponsorId);
		result.setSponsor(sponsor);

		return result;
	}

	@Override
	public void validate(final Request<NonCommercialBanner> request, final NonCommercialBanner entity, final Errors errors) {
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
				errors.state(request, notSpam, "picture", "error.nonCommercialBanner.input.spam");
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
				errors.state(request, notSpam, "slogan", "error.nonCommercialBanner.input.spam");
			}

			if (!errors.hasErrors("jingle") && !entity.getJingle().isEmpty()) {
				String jingle = entity.getJingle().toLowerCase();
				Double numberLetters = new Double(jingle.length());
				Double numberSpamWordsInLetters = 0.0;
				for (String s : spamWords) {
					if (jingle.contains(s.trim())) {
						numberSpamWordsInLetters += s.length();
					}
				}
				Double percentageSpam = numberSpamWordsInLetters / numberLetters * 100.0;
				Boolean notSpam = percentageSpam < threshold;
				errors.state(request, notSpam, "jingle", "error.nonCommercialBanner.input.spam");
			}

		}
	}

	@Override
	public void create(final Request<NonCommercialBanner> request, final NonCommercialBanner entity) {
		this.repository.save(entity);
	}

}
