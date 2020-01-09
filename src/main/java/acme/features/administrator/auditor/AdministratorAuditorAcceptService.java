
package acme.features.administrator.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.ApplicationStatus;
import acme.entities.requestAuditors.RequestAuditor;
import acme.entities.roles.Auditor;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractCreateService;

@Service
public class AdministratorAuditorAcceptService implements AbstractCreateService<Administrator, Auditor> {

	@Autowired
	private AdministratorAuditorRepository repository;


	@Override
	public boolean authorise(final Request<Auditor> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Auditor> request, final Auditor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);

	}

	@Override
	public void unbind(final Request<Auditor> request, final Auditor entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "firm", "responsabilityStatement");

	}

	@Override
	public void validate(final Request<Auditor> request, final Auditor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void create(final Request<Auditor> request, final Auditor entity) {
		this.repository.save(entity);
		int idRequest = request.getModel().getInteger("id");
		RequestAuditor requestAud = this.repository.findRequestAuditorById(idRequest);
		requestAud.setStatus(ApplicationStatus.ACCEPTED);
		this.repository.save(requestAud);

	}

	@Override
	public Auditor instantiate(final Request<Auditor> request) {
		Auditor result;
		RequestAuditor requestAud;

		int idRequest = request.getModel().getInteger("id");
		requestAud = this.repository.findRequestAuditorById(idRequest);
		result = new Auditor();
		result.setFirm(requestAud.getFirm());
		result.setResponsabilityStatement(requestAud.getResponsabilityStatement());
		result.setUserAccount(requestAud.getAuthenticated().getUserAccount());

		return result;
	}
}
