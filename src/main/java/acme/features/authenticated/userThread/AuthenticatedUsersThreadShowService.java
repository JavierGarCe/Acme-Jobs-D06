
package acme.features.authenticated.userThread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.threads.UserThread;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedUsersThreadShowService implements AbstractShowService<Authenticated, UserThread> {

	@Autowired
	private AuthenticatedUsersThreadRepository repository;


	@Override
	public boolean authorise(final Request<UserThread> request) {
		assert request != null;
		// Solo puedes ver un usuario de un hilo si tu eres el creador de dicho hilo
		int userThreadId = request.getModel().getInteger("id");
		UserThread userThread = this.repository.findOneUserThreadById(userThreadId);
		int threadId = userThread.getThread().getId();
		int meId = request.getPrincipal().getActiveRoleId();
		UserThread userThread2 = this.repository.findOneByThreadIdAndAuthenticatedId(threadId, meId);
		Boolean res = userThread2.getCreatorThread();

		return res;
	}

	@Override
	public void unbind(final Request<UserThread> request, final UserThread entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "authenticated.userAccount.username", "creatorThread");
	}

	@Override
	public UserThread findOne(final Request<UserThread> request) {
		assert request != null;

		UserThread result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneUserThreadById(id);

		return result;
	}
}
