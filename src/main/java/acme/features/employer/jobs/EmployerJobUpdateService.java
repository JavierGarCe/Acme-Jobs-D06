
package acme.features.employer.jobs;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Status;
import acme.entities.customization.Customization;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.datatypes.Money;
import acme.framework.entities.Principal;
import acme.framework.helpers.StringHelper;
import acme.framework.services.AbstractUpdateService;

@Service
public class EmployerJobUpdateService implements AbstractUpdateService<Employer, Job> {

	@Autowired
	EmployerJobRepository repository;


	@Override
	public boolean authorise(final Request<Job> request) {
		assert request != null;

		boolean result;
		int jobId;
		Job job;
		Employer employer;
		Principal principal;

		jobId = request.getModel().getInteger("id");
		job = this.repository.findOneById(jobId);
		employer = job.getEmployer();
		principal = request.getPrincipal();
		result = employer.getUserAccount().getId() == principal.getAccountId() && job.getStatus().equals(Status.DRAFT);

		return result;
	}

	@Override
	public void bind(final Request<Job> request, final Job entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Job> request, final Job entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "reference", "status", "salary", "deadline", "moreInfo", "descriptor.description");
	}

	@Override
	public Job findOne(final Request<Job> request) {
		assert request != null;

		Job result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);

		return result;
	}

	@Override
	public void validate(final Request<Job> request, final Job entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Calendar calendar;
		Date present;
		Job jobByReference = this.repository.findOneByReference(entity.getReference());
		boolean isDuplicated = jobByReference != null && entity.getId() != jobByReference.getId();
		errors.state(request, !isDuplicated, "reference", "employer.reference.error.duplicated");

		if (!errors.hasErrors("deadline")) {
			calendar = new GregorianCalendar();
			present = calendar.getTime();
			boolean isDeadlineFuture = entity.getDeadline().after(present);
			errors.state(request, isDeadlineFuture, "deadline", "employer.job.error.not-future-deadline");
		}
		Money salary = entity.getSalary();
		if (!errors.hasErrors("salary")) {
			boolean isEUR = salary.getCurrency().equals("EUR");
			errors.state(request, isEUR, "salary", "employer.job.error.not-EUR-currency");
			boolean isPositive = salary.getAmount() != 0;
			errors.state(request, isPositive, "salary", "employer.job.error.not-positive-salary");
		}
		Status status = entity.getStatus();
		if (!errors.hasErrors("status") && status.equals(Status.PUBLISHED)) {
			double percentage = 0.;
			if (this.repository.countPercentage(entity.getId()) != null) {
				percentage = this.repository.countPercentage(entity.getId());
			}
			boolean fullWorkload = percentage == 100.0;
			errors.state(request, fullWorkload, "status", "employer.job.error.non-fullWorkload-job");

			Customization customization = this.repository.getCustomization();
			if (!StringHelper.isBlank(customization.getSpamword())) {
				String[] spamWords = customization.getSpamword().toLowerCase().split(",");
				Double threshold = customization.getThreshold();

				if (!errors.hasErrors("descriptor.description")) {
					String description = entity.getDescriptor().getDescription().toLowerCase();
					Double numberLetters = new Double(description.length());
					Double numberSpamWordsInLetters = 0.0;
					for (String s : spamWords) {
						if (description.contains(s.trim())) {
							numberSpamWordsInLetters += s.length();
						}
					}
					Double percentageSpam = numberSpamWordsInLetters / numberLetters * 100.0;
					Boolean notSpam = percentageSpam < threshold;
					errors.state(request, notSpam, "descriptor.description", "employer.job.error.spam");

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
					errors.state(request, notSpam, "title", "employer.job.error.spam");

				}
			}
		}

	}

	@Override
	public void update(final Request<Job> request, final Job entity) {
		assert request != null;
		assert entity != null;
		entity.getDescriptor().setDescription(request.getModel().getString("descriptor.description"));
		this.repository.save(entity.getDescriptor());
		this.repository.save(entity);

	}

}
