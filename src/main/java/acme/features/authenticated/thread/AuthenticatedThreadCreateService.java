
package acme.features.authenticated.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.threads.Thread;
import acme.entities.threads.UserThread;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedThreadCreateService implements AbstractCreateService<Authenticated, Thread> {

	@Autowired

	private AuthenticatedThreadRepository repository;


	@Override
	public boolean authorise(final Request<Thread> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Thread> request, final Thread entity, final Errors errors) {
		// TODO Auto-generated method stub
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "moment");

	}

	@Override
	public void unbind(final Request<Thread> request, final Thread entity, final Model model) {
		// TODO Auto-generated method stub
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title");

	}

	@Override
	public Thread instantiate(final Request<Thread> request) {
		Authenticated authenticated;
		List<Authenticated> lista = new ArrayList<>();

		authenticated = this.repository.findAuthenticatedById(request.getPrincipal().getActiveRoleId());
		lista.add(authenticated);
		Thread thread;
		thread = new Thread();

		return thread;
	}

	@Override
	public void validate(final Request<Thread> request, final Thread entity, final Errors errors) {
		// TODO Auto-generated method stub
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void create(final Request<Thread> request, final Thread entity) {
		// TODO Auto-generated method stub
		assert request != null;
		assert entity != null;
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);

		UserThread userThread = new UserThread();
		Authenticated me = this.repository.findAuthenticatedById(request.getPrincipal().getActiveRoleId());
		userThread.setAuthenticated(me);
		userThread.setThread(entity);
		userThread.setCreatorThread(true);

		this.repository.save(entity);
		this.repository.save(userThread);
	}

}
