
package acme.features.worker.job;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Status;
import acme.entities.jobs.Job;
import acme.entities.roles.Worker;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class WorkerJobShowService implements AbstractShowService<Worker, Job> {

	@Autowired
	private WorkerJobRepository repository;


	@Override
	public boolean authorise(final Request<Job> request) {
		assert request != null;

		int jobId;
		Principal principal;

		jobId = request.getModel().getInteger("id");
		Job job = this.repository.findJobById(jobId);
		principal = request.getPrincipal();
		Date nowDate = new Date(System.currentTimeMillis());
		boolean isActive = job.getDeadline().after(nowDate) && job.getStatus().equals(Status.PUBLISHED);
		return isActive || this.repository.findNumberApplicationsByJobId(jobId, principal.getActiveRoleId()) > 0;
	}

	@Override
	public void unbind(final Request<Job> request, final Job entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		int workerId = request.getPrincipal().getActiveRoleId();
		int jobId = request.getModel().getInteger("id");
		Integer numberApp = this.repository.findNumberApplicationsByJobId(jobId, workerId);
		Boolean canApply = numberApp == 0;
		model.setAttribute("canApply", canApply);

		request.unbind(entity, model, "title", "moreInfo", "salary", "deadline", "reference", "status", "descriptor", "descriptor.description", "employer.userAccount.username");

	}

	@Override
	public Job findOne(final Request<Job> request) {
		assert request != null;

		Job res;
		int id;

		id = request.getModel().getInteger("id");
		res = this.repository.findJobById(id);

		res.getDescriptor().getDuties().size(); //desenrrollar

		return res;
	}
}
