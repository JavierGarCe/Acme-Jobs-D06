
package acme.features.authenticated.requestAuditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.ApplicationStatus;
import acme.entities.requestAuditors.RequestAuditor;
import acme.entities.roles.Auditor;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedRequestAuditorCreateService implements AbstractCreateService<Authenticated, RequestAuditor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedRequestAuditorRepository repository;


	// AbstractCreateService<Authenticated, Auditor> interface -----------------

	@Override
	public boolean authorise(final Request<RequestAuditor> request) {
		assert request != null;
		return !request.getPrincipal().hasRole(Auditor.class);

	}

	@Override
	public void validate(final Request<RequestAuditor> request, final RequestAuditor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void bind(final Request<RequestAuditor> request, final RequestAuditor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<RequestAuditor> request, final RequestAuditor entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		boolean canShow = this.repository.findOneRequestAuditorNotFinishedByUserAccountId(request.getPrincipal().getAccountId()) != null;
		model.setAttribute("canShow", canShow);
		request.unbind(entity, model, "responsabilityStatement", "firm");
	}

	@Override
	public RequestAuditor instantiate(final Request<RequestAuditor> request) {
		assert request != null;

		RequestAuditor result;
		Principal principal;
		int userAccountId;
		Authenticated authenticated;

		principal = request.getPrincipal();
		userAccountId = principal.getAccountId();
		authenticated = this.repository.findOneAuthenticatedByUserAccountId(userAccountId);
		result = new RequestAuditor();
		result.setAuthenticated(authenticated);
		result.setStatus(ApplicationStatus.PENDING);
		result.setFinished(false);
		return result;
	}

	@Override
	public void create(final Request<RequestAuditor> request, final RequestAuditor entity) {
		assert request != null;
		assert entity != null;
		this.repository.save(entity);
	}
}
