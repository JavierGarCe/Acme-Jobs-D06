
package acme.features.employer.applications;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.ApplicationStatus;
import acme.entities.applications.Application;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;

@Service
public class EmployerApplicationUpdateService implements AbstractUpdateService<Employer, Application> {

	@Autowired
	private EmployerApplicationRepository repository;


	@Override
	public boolean authorise(final Request<Application> request) {
		assert request != null;
		boolean result;
		int applicationId;
		Application application;
		Job job;
		Employer employer;
		Principal principal;

		applicationId = request.getModel().getInteger("id");
		application = this.repository.findOneById(applicationId);
		job = application.getJob();
		employer = job.getEmployer();
		principal = request.getPrincipal();
		result = employer.getUserAccount().getId() == principal.getAccountId();

		return result && application.getStatus() == ApplicationStatus.PENDING;
	}

	@Override
	public void bind(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "reference", "moment", "statement", "skills", "qualifications", "worker", "job");

	}

	@Override
	public void unbind(final Request<Application> request, final Application entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "justification");

	}

	@Override
	public Application findOne(final Request<Application> request) {
		assert request != null;

		Application result;
		int id;

		id = request.getModel().getInteger("id");

		result = this.repository.findOneById(id);

		return result;
	}

	@Override
	public void validate(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		if (!errors.hasErrors("justification")) {
			Boolean isFilled;
			if (entity.getStatus().equals(ApplicationStatus.REJECTED)) {
				isFilled = !entity.getJustification().equals("");
			} else {
				isFilled = true;
			}
			errors.state(request, isFilled, "justification", "employer.justification.error.notblank");
		}
		if (!errors.hasErrors("status")) {
			Boolean isNotPending = !entity.getStatus().equals(ApplicationStatus.PENDING);
			errors.state(request, isNotPending, "status", "employer.status.error.notupdated");
		}

	}

	@Override
	public void update(final Request<Application> request, final Application entity) {
		assert request != null;
		assert entity != null;
		if (request.getModel().getAttribute("status").equals(ApplicationStatus.REJECTED)) {
			entity.setStatus(ApplicationStatus.REJECTED);
		} else if (request.getModel().getAttribute("status").equals(ApplicationStatus.ACCEPTED)) {
			entity.setStatus(ApplicationStatus.ACCEPTED);
		}
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);
		this.repository.save(entity);

	}

}
