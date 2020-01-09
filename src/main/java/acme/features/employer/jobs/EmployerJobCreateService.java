
package acme.features.employer.jobs;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Status;
import acme.entities.jobs.Descriptor;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractCreateService;

@Service
public class EmployerJobCreateService implements AbstractCreateService<Employer, Job> {

	@Autowired
	private EmployerJobRepository repository;


	@Override
	public boolean authorise(final Request<Job> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Job> request, final Job entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors); //propiedades excluidas??
	}

	@Override
	public void unbind(final Request<Job> request, final Job entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "reference", "status", "salary", "deadline", "moreInfo", "descriptor.description");
	}

	@Override
	public Job instantiate(final Request<Job> request) {
		Job result;
		Descriptor descriptor;
		Employer employer;
		result = new Job();
		descriptor = new Descriptor();
		employer = this.repository.findEmployerById(request.getPrincipal().getActiveRoleId());
		result.setDescriptor(descriptor);
		result.setEmployer(employer);
		return result;
	}

	@Override
	public void validate(final Request<Job> request, final Job entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Calendar calendar;
		Date present;

		boolean isDuplicated = this.repository.findOneByReference(entity.getReference()) != null;
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
		if (!errors.hasErrors("status")) {
			boolean isDraft = status.equals(Status.DRAFT);
			errors.state(request, isDraft, "status", "employer.job.error.not-draft-job");
		}
	}

	@Override
	public void create(final Request<Job> request, final Job entity) {
		assert request != null;
		assert entity != null;
		entity.getDescriptor().setDescription(request.getModel().getString("descriptor.description"));
		this.repository.save(entity.getDescriptor());
		this.repository.save(entity);
	}

}
