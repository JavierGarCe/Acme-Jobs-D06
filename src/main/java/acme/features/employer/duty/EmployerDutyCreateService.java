
package acme.features.employer.duty;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Status;
import acme.entities.customization.Customization;
import acme.entities.jobs.Duty;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.helpers.StringHelper;
import acme.framework.services.AbstractCreateService;

@Service
public class EmployerDutyCreateService implements AbstractCreateService<Employer, Duty> {

	@Autowired
	private EmployerDutyRepository repository;


	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;
		int jobId = request.getModel().getInteger("jobId");
		Job job = this.repository.findOneJobById(jobId);
		int employerId = this.repository.findEmployerIdByJobId(jobId);
		Principal principal = request.getPrincipal();
		return employerId == principal.getActiveRoleId() && job.getStatus().equals(Status.DRAFT);
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
	public Duty instantiate(final Request<Duty> request) {
		assert request != null;
		Duty duty;
		duty = new Duty();
		return duty;
	}

	@Override
	public void validate(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("percentage")) {
			int jobId = request.getModel().getInteger("jobId");
			double percentage = 0;
			boolean isPositive = entity.getPercentage() > 0;
			errors.state(request, isPositive, "percentage", "employer.duty.error.not-positive-percentage");

			if (this.repository.countPercentage(jobId) != null) {
				percentage = this.repository.countPercentage(jobId);
			}
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
	public void create(final Request<Duty> request, final Duty entity) {
		assert request != null;
		assert entity != null;

		int jobId = request.getModel().getInteger("jobId");
		Job job = this.repository.findOneJobById(jobId);
		Collection<Duty> duties = job.getDescriptor().getDuties();
		duties.add(entity);
		this.repository.save(entity);
		this.repository.save(job);
	}

}
