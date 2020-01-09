
package acme.features.authenticated.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.threads.Thread;
import acme.entities.threads.UserThread;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedThreadShowService implements AbstractShowService<Authenticated, Thread> {

	@Autowired
	AuthenticatedThreadRepository repository;


	@Override
	public boolean authorise(final Request<Thread> request) {
		assert request != null;

		int idThread = request.getModel().getInteger("id");
		Principal principal = request.getPrincipal();
		int authenticatedId = principal.getActiveRoleId();
		Integer count = this.repository.countNumberUserThreadByAuthenticatedIdAndThreadId(authenticatedId, idThread);

		return count == 1;
	}

	@Override
	public void unbind(final Request<Thread> request, final Thread entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		
		//Comprobramos si el usuario es el propietario del hilo para mostrarle posteriormente en el form el boton de ver usuarios o no
		int idThread = request.getModel().getInteger("id");
		Principal principal = request.getPrincipal();
		int authenticatedId = principal.getActiveRoleId();
		UserThread userThread = this.repository.findOneByThreadIdAndAuthenticatedId(idThread, authenticatedId);
		Boolean hasAccess = userThread.getCreatorThread();
		model.setAttribute("hasAccess", hasAccess);
		
		request.unbind(entity, model, "title", "moment");
	}

	@Override
	public Thread findOne(final Request<Thread> request) {

		assert request != null;

		Thread result;
		int id;
		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);
		return result;
	}

}
