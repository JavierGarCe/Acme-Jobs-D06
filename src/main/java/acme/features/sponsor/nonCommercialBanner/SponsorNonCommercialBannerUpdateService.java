
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
import acme.framework.services.AbstractUpdateService;

@Service
public class SponsorNonCommercialBannerUpdateService implements AbstractUpdateService<Sponsor, NonCommercialBanner> {

	@Autowired
	private SponsorNonCommercialBannerRepository repository;


	@Override
	public boolean authorise(final Request<NonCommercialBanner> request) {
		assert request != null;

		Principal principal = request.getPrincipal();
		int nonCommercialBannerId = request.getModel().getInteger("id");
		NonCommercialBanner banner = this.repository.findOneNonCommercialBannerById(nonCommercialBannerId);

		return banner.getSponsor().getId() == principal.getActiveRoleId();
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

		request.unbind(entity, model, "picture", "slogan", "targetUrl", "jingle");
	}

	@Override
	public NonCommercialBanner findOne(final Request<NonCommercialBanner> request) {
		assert request != null;

		NonCommercialBanner result;
		int id = request.getModel().getInteger("id");

		result = this.repository.findOneNonCommercialBannerById(id);

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
	public void update(final Request<NonCommercialBanner> request, final NonCommercialBanner entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}

}
