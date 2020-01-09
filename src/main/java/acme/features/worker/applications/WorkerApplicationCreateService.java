
package acme.features.worker.applications;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.ApplicationStatus;
import acme.datatypes.Status;
import acme.entities.applications.Application;
import acme.entities.jobs.Job;
import acme.entities.roles.Worker;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;

@Service
public class WorkerApplicationCreateService implements AbstractCreateService<Worker, Application> {

	@Autowired
	private WorkerApplicationRepository repository;


	@Override
	public boolean authorise(final Request<Application> request) {
		assert request != null;
		int jobId = request.getModel().getInteger("jobId");
		Job job = this.repository.findJobById(jobId);
		Date nowDate = new Date(System.currentTimeMillis());
		Integer numberApplications = this.repository.findNumberApplicationsByWorkerIdAndJobId(request.getPrincipal().getActiveRoleId(), jobId);
		assert numberApplications != null;
		boolean result = job.getDeadline().after(nowDate) && job.getStatus().equals(Status.PUBLISHED) && numberApplications == 0;

		return result;
	}

	@Override
	public void bind(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "moment", "status");
	}

	@Override
	public void unbind(final Request<Application> request, final Application entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "reference", "moment", "statement", "skills", "qualifications");
	}

	@Override
	public Application instantiate(final Request<Application> request) {
		assert request != null;

		Application result;
		result = new Application();
		result.setStatus(ApplicationStatus.PENDING); //Al crear una application estará PENDIENTE por defecto

		Principal principal = request.getPrincipal();
		int workerId = principal.getActiveRoleId();
		Worker worker = this.repository.findWorkerById(workerId);
		result.setWorker(worker);

		int jobId = request.getModel().getInteger("jobId");
		Job job = this.repository.findJobById(jobId);
		result.setJob(job);

		return result;
	}

	@Override
	public void validate(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean isDuplicated = this.repository.findOneByReference(entity.getReference()) != null;
		errors.state(request, !isDuplicated, "reference", "worker.reference.error.duplicated");
	}

	@Override
	public void create(final Request<Application> request, final Application entity) {
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);

		this.repository.save(entity);

	}

}
