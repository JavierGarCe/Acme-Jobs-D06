
package acme.features.auditor.AuditRecord;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Status;
import acme.entities.auditRecords.AuditRecord;
import acme.entities.jobs.Job;
import acme.entities.roles.Auditor;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class AuditorAuditRecordCreateService implements AbstractCreateService<Auditor, AuditRecord> {

	@Autowired
	AuditorAuditRecordRepository repository;


	@Override
	public boolean authorise(final Request<AuditRecord> request) {
		assert request != null;
		int jobId = request.getModel().getInteger("jobId");
		Job job = this.repository.findJobById(jobId);
		Date nowDate = new Date(System.currentTimeMillis());
		boolean result = job.getDeadline().after(nowDate) && job.getStatus().equals(Status.PUBLISHED);

		return result;
	}

	@Override
	public void bind(final Request<AuditRecord> request, final AuditRecord entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "moment");

	}

	@Override
	public void unbind(final Request<AuditRecord> request, final AuditRecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "body", "status");

	}

	@Override
	public AuditRecord instantiate(final Request<AuditRecord> request) {
		AuditRecord res;
		Job job;
		Auditor auditor;
		res = new AuditRecord();
		int jobId = request.getModel().getInteger("jobId");
		job = this.repository.findJobById(jobId);
		auditor = this.repository.findAuditorById(request.getPrincipal().getActiveRoleId());
		res.setJob(job);
		res.setAuditor(auditor);
		return res;
	}

	@Override
	public void validate(final Request<AuditRecord> request, final AuditRecord entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void create(final Request<AuditRecord> request, final AuditRecord entity) {
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);

		this.repository.save(entity);

	}

}
