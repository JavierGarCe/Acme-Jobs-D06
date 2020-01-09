
package acme.features.employer.auditRecords;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Status;
import acme.entities.auditRecords.AuditRecord;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class EmployerAuditRecordShowService implements AbstractShowService<Employer, AuditRecord> {

	@Autowired
	EmployerAuditRecordRepository repository;


	@Override
	public boolean authorise(final Request<AuditRecord> request) {
		assert request != null;

		boolean result;
		int id;
		Integer jobId;
		Job job;
		Employer employer;
		Principal principal;

		id = request.getModel().getInteger("id");
		jobId = this.repository.findJobIdByAuditRecordId(id);
		assert jobId != null;
		job = this.repository.findOneJobById(jobId);
		employer = job.getEmployer();
		principal = request.getPrincipal();
		result = employer.getUserAccount().getId() == principal.getAccountId();

		AuditRecord ar;

		ar = this.repository.findOneAuditRecordById(id);
		result = result && ar.getStatus().equals(Status.PUBLISHED);

		return result;
	}

	@Override
	public void unbind(final Request<AuditRecord> request, final AuditRecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "moment", "status", "body");

	}

	@Override
	public AuditRecord findOne(final Request<AuditRecord> request) {
		assert request != null;

		int AuditRecordId;
		AuditRecord result;
		AuditRecordId = request.getModel().getInteger("id");
		result = this.repository.findOneAuditRecordById(AuditRecordId);

		return result;
	}

}
