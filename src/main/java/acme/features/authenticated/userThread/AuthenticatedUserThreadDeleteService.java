
package acme.features.authenticated.userThread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.threads.UserThread;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractDeleteService;

@Service
public class AuthenticatedUserThreadDeleteService implements AbstractDeleteService<Authenticated, UserThread> {

	@Autowired
	private AuthenticatedUsersThreadRepository repository;


	@Override
	public boolean authorise(final Request<UserThread> request) {
		assert request != null;
		// TODO solo podra borrar a un usuario de un hilo si es su hilo
		int userThreadId = request.getModel().getInteger("id");
		UserThread userThread = this.repository.findOneUserThreadById(userThreadId);
		int threadId = userThread.getThread().getId();
		int id = request.getPrincipal().getActiveRoleId();
		UserThread userThread2 = this.repository.findOneByThreadIdAndAuthenticatedId(threadId, id);
		boolean res = userThread2.getCreatorThread();
		return res;
	}

	@Override
	public void bind(final Request<UserThread> request, final UserThread entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<UserThread> request, final UserThread entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "authenticated.userAccount.username");
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

	@Override
	public void validate(final Request<UserThread> request, final UserThread entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		int threadId = request.getModel().getInteger("id");
		UserThread userThread = this.repository.findOneUserThreadById(threadId);
		int authenticatedId = userThread.getAuthenticated().getId();
		int id = request.getPrincipal().getActiveRoleId();
		boolean sameUser = id == authenticatedId;
		errors.state(request, !sameUser, "authenticated.userAccount.username", "authenticated.userThread.delete.creatorThread");

	}

	@Override
	public void delete(final Request<UserThread> request, final UserThread entity) {
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);
	}

}
