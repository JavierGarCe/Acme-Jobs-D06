
package acme.features.auditor.duty;

import java.util.Calendar;
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
import acme.framework.services.AbstractShowService;

@Service
public class AuditorDutyShowService implements AbstractShowService<Auditor, Duty> {

	@Autowired
	private AuditorDutyRepository repository;


	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;
		boolean res;
		Principal principal = request.getPrincipal();
		int dutyId = request.getModel().getInteger("id");
		Calendar calendar = new GregorianCalendar();
		Integer jobId = this.repository.findJobIdByDutyId(dutyId);
		assert jobId != null;
		Job job = this.repository.findOneJobById(jobId);
		res = job.getDeadline().after(calendar.getTime()) && job.getStatus().equals(Status.PUBLISHED);
		return res || this.repository.countMyAuditRecords(jobId, principal.getActiveRoleId()) > 0;

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

}
