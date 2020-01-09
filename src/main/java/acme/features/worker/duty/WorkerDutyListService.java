
package acme.features.worker.duty;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Status;
import acme.entities.jobs.Duty;
import acme.entities.jobs.Job;
import acme.entities.roles.Worker;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;

@Service
public class WorkerDutyListService implements AbstractListService<Worker, Duty> {

	@Autowired
	private WorkerDutyRepository repository;


	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;
		int jobId;
		Principal principal;

		jobId = request.getModel().getInteger("id");
		principal = request.getPrincipal();
		Job job = this.repository.findJobById(jobId);
		Date nowDate = new Date(System.currentTimeMillis());
		boolean isActive = job.getDeadline().after(nowDate) && job.getStatus().equals(Status.PUBLISHED);

		return isActive || this.repository.findNumberApplicationsByJobId(jobId, principal.getActiveRoleId()) > 0; //Tendrá acceso si el trabajo está activo o bien si ya había aplicado a ese trabajo
	}

	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "description");

	}

	@Override
	public Collection<Duty> findMany(final Request<Duty> request) {
		assert request != null;

		Collection<Duty> result;
		int id;

		id = request.getModel().getInteger("id");

		result = this.repository.findDutiesByJobId(id);

		return result;
	}

}
