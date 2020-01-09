
package acme.features.authenticated.requestAuditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.requestAuditors.RequestAuditor;
import acme.entities.roles.Auditor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedRequestAuditorShowService implements AbstractShowService<Authenticated, RequestAuditor> {

	@Autowired
	AuthenticatedRequestAuditorRepository repository;


	@Override
	public boolean authorise(final Request<RequestAuditor> request) {
		assert request != null;
		return !request.getPrincipal().hasRole(Auditor.class);

	}

	@Override
	public void unbind(final Request<RequestAuditor> request, final RequestAuditor entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "responsabilityStatement", "firm", "status");

	}

	@Override
	public RequestAuditor findOne(final Request<RequestAuditor> request) {

		assert request != null;

		RequestAuditor result;
		int id;
		id = request.getPrincipal().getAccountId();
		result = this.repository.findOneRequestAuditorNotFinishedByUserAccountId(id);
		return result;
	}

}
