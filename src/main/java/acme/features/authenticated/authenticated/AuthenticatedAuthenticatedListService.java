
package acme.features.authenticated.authenticated;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.threads.UserThread;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractListService;

@Service
public class AuthenticatedAuthenticatedListService implements AbstractListService<Authenticated, Authenticated> {

	@Autowired
	private AuthenticatedAuthenticatedRepository repository;


	@Override
	public boolean authorise(final Request<Authenticated> request) {
		assert request != null;
		// TODO solo puedo listar los usuarios que no estan en un hilo si soy el propietario del hilo
		int threadId = request.getModel().getInteger("threadId");
		int meId = request.getPrincipal().getActiveRoleId();
		UserThread userThread = this.repository.findOneByThreadIdAndAuthenticatedId(threadId, meId);
		Boolean res = userThread.getCreatorThread();

		Collection<Authenticated> nonIncluded = this.repository.findUserThreadNotInThread(threadId);
		Boolean canAddUser = nonIncluded.size() > 0;

		return res && canAddUser;
	}

	@Override
	public void unbind(final Request<Authenticated> request, final Authenticated entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "userAccount.username");

	}

	@Override
	public Collection<Authenticated> findMany(final Request<Authenticated> request) {
		assert request != null;

		Collection<Authenticated> result;
		int threadId = request.getModel().getInteger("threadId");
		result = this.repository.findUserThreadNotInThread(threadId);

		return result;

	}
}
