
package acme.features.auditor.duty;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Status;
import acme.entities.jobs.Duty;
import acme.entities.jobs.Job;
import acme.entities.roles.Auditor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;

@Service
public class AuditorDutyListService implements AbstractListService<Auditor, Duty> {

	@Autowired
	private AuditorDutyRepository repository;


	@Override
	public boolean authorise(final Request<Duty> request) {
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
