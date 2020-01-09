
package acme.features.auditor.AuditRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.auditRecords.AuditRecord;
import acme.entities.roles.Auditor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;

@Service
public class AuditorAuditRecordListMineService implements AbstractListService<Auditor, AuditRecord> {

	@Autowired
	AuditorAuditRecordRepository repository;


	@Override
	public boolean authorise(final Request<AuditRecord> request) {
		assert request != null;

		return true;
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
		Principal principal = request.getPrincipal();

		result = this.repository.findManyByJobIdActives(jobId, principal.getActiveRoleId());

		return result;
	}

}
