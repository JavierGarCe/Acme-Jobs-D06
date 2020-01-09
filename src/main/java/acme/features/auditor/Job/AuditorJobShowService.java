
package acme.features.auditor.Job;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Status;
import acme.entities.jobs.Job;
import acme.entities.roles.Auditor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class AuditorJobShowService implements AbstractShowService<Auditor, Job> {

	@Autowired
	AuditorJobRepository repository;


	@Override
	public boolean authorise(final Request<Job> request) {
		assert request != null;
		int jobId = request.getModel().getInteger("id");
		Job job = this.repository.findOneJobById(jobId);
		Status status;
		Date deadline;
		Boolean res = true;
		Principal principal = request.getPrincipal();

		status = job.getStatus();
		deadline = job.getDeadline();

		Calendar cal = new GregorianCalendar();

		if (!status.equals(Status.PUBLISHED)) {
			res = false;
		}

		if (deadline.before(cal.getTime())) {
			res = false;
		}

		return res || this.repository.countMyAuditRecords(jobId, principal.getActiveRoleId()) > 0;
	}

	@Override
	public void unbind(final Request<Job> request, final Job entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		int jobId = request.getModel().getInteger("id");
		Job job = this.repository.findOneJobById(jobId);
		Date deadline;
		deadline = job.getDeadline();
		Calendar cal = new GregorianCalendar();
		Boolean canAudit = deadline.after(cal.getTime());

		model.setAttribute("canAudit", canAudit);

		request.unbind(entity, model, "title", "salary", "deadline", "reference", "status", "descriptor.description", "moreInfo");

	}

	@Override
	public Job findOne(final Request<Job> request) {
		assert request != null;

		int jobId;
		Job result;
		jobId = request.getModel().getInteger("id");
		result = this.repository.findOneJobById(jobId);
		result.getDescriptor().getDuties().size();
		return result;
	}

}
