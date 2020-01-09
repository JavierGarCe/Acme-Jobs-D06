
package acme.features.authenticated.userThread;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.threads.UserThread;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractListService;

@Service
public class AuthenticatedUsersThreadListService implements AbstractListService<Authenticated, UserThread> {

	@Autowired
	private AuthenticatedUsersThreadRepository repository;


	@Override
	public boolean authorise(final Request<UserThread> request) {
		assert request != null;
		// Solo puedo listar los usuarios de un hilo si soy el dueño de ese hilo
		int threadId = request.getModel().getInteger("id");
		int meId = request.getPrincipal().getActiveRoleId();
		UserThread userThread = this.repository.findOneByThreadIdAndAuthenticatedId(threadId, meId);
		Boolean res = userThread.getCreatorThread();

		return res;

	}

	@Override
	public void unbind(final Request<UserThread> request, final UserThread entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		int threadId = request.getModel().getInteger("id");
		Collection<Authenticated> nonIncluded = this.repository.findUserThreadNotInThread(threadId);
		Boolean canAddUser = nonIncluded.size() > 0;
		model.setAttribute("canAddUser", canAddUser);

		request.unbind(entity, model, "authenticated.userAccount.username");

	}

	@Override
	public Collection<UserThread> findMany(final Request<UserThread> request) {
		assert request != null;

		Collection<UserThread> result;
		int threadId = request.getModel().getInteger("id");

		result = this.repository.findUserThreadsInThread(threadId);

		return result;

	}

}
