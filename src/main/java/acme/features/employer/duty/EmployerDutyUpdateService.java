
package acme.features.employer.duty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Status;
import acme.entities.customization.Customization;
import acme.entities.jobs.Descriptor;
import acme.entities.jobs.Duty;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.helpers.StringHelper;
import acme.framework.services.AbstractUpdateService;

@Service
public class EmployerDutyUpdateService implements AbstractUpdateService<Employer, Duty> {

	@Autowired
	EmployerDutyRepository repository;


	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;

		boolean result;
		int id;
		Integer jobId;
		Job job;
		Employer employer;
		Principal principal;

		id = request.getModel().getInteger("id");
		jobId = this.repository.findJobIdByDutyId(id);
		assert jobId != null;
		job = this.repository.findOneJobById(jobId);
		employer = job.getEmployer();
		principal = request.getPrincipal();
		result = employer.getUserAccount().getId() == principal.getAccountId() && job.getStatus().equals(Status.DRAFT);

		return result;
	}

	@Override
	public void bind(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "description", "percentage");
	}

	@Override
	public Duty findOne(final Request<Duty> request) {
		assert request != null;

		Duty result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);

		return result;
	}

	@Override
	public void validate(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		if (!errors.hasErrors("percentage")) {
			boolean isPositive = entity.getPercentage() > 0;
			errors.state(request, isPositive, "percentage", "employer.duty.error.not-positive-percentage");
			Integer id = request.getModel().getInteger("id");
			Integer jobId = this.repository.findJobIdByDutyId(id);
			double percentage = this.repository.countPercentage(jobId);
			percentage = percentage - this.repository.findOneById(id).getPercentage();
			double percMax = 100.0 - percentage;
			boolean max = percentage + entity.getPercentage() <= 100.0;
			errors.state(request, max, "percentage", "employer.duty.error.max-percentage", percMax);
		}

		Customization customization = this.repository.getCustomization();
		if (!StringHelper.isBlank(customization.getSpamword())) {
			String[] spamWords = customization.getSpamword().toLowerCase().split(", ");
			Double threshold = customization.getThreshold();

			if (!errors.hasErrors("description")) {
				String description = entity.getDescription().toLowerCase();
				Double numberLetters = new Double(description.length());
				Double numberSpamWordsInLetters = 0.0;
				for (String s : spamWords) {
					if (description.contains(s.trim())) {
						numberSpamWordsInLetters += s.length();
					}
				}
				Double percentageSpam = numberSpamWordsInLetters / numberLetters * 100.0;
				Boolean notSpam = percentageSpam < threshold;
				errors.state(request, notSpam, "description", "employer.duty.error.spam");

			}

			if (!errors.hasErrors("title")) {
				String title = entity.getTitle().toLowerCase();
				Double numberLetters = new Double(title.length());
				Double numberSpamWordsInLetters = 0.0;
				for (String s : spamWords) {
					if (title.contains(s.trim())) {
						numberSpamWordsInLetters += s.length();
					}
				}
				Double percentageSpam = numberSpamWordsInLetters / numberLetters * 100.0;
				Boolean notSpam = percentageSpam < threshold;
				errors.state(request, notSpam, "title", "employer.duty.error.spam");
			}
		}
	}

	@Override
	public void update(final Request<Duty> request, final Duty entity) {
		assert request != null;
		assert entity != null;

		int id = request.getModel().getInteger("id");
		Descriptor descriptor = this.repository.findOneDescriptionById(id);
		assert descriptor != null;
		descriptor.getDuties().remove(this.repository.findOneById(id));
		descriptor.getDuties().add(entity);
		this.repository.save(descriptor);
		this.repository.save(entity);

	}

}
