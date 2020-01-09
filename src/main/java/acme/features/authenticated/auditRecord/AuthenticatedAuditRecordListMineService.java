
package acme.features.authenticated.auditRecord;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Status;
import acme.entities.auditRecords.AuditRecord;
import acme.entities.jobs.Job;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractListService;

@Service
public class AuthenticatedAuditRecordListMineService implements AbstractListService<Authenticated, AuditRecord> {

	@Autowired
	AuthenticatedAuditRecordRepository repository;


	@Override
	public boolean authorise(final Request<AuditRecord> request) {
		assert request != null;
		int idJob = request.getModel().getInteger("id");
		Job job = this.repository.findJobById(idJob);
		Calendar calendar = new GregorianCalendar();
		return job.getDeadline().after(calendar.getTime()) && job.getStatus().equals(Status.PUBLISHED);
	}

	@Override
	public void unbind(final Request<AuditRecord> request, final AuditRecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "moment", "status");
	}

	@Override
	public Collection<AuditRecord> findMany(final Request<AuditRecord> request) {

		assert request != null;
		int jobId;
		Collection<AuditRecord> result;
		jobId = request.getModel().getInteger("id");
		result = this.repository.findManyByJobId(jobId);

		return result;
	}

}
