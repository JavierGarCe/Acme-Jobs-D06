
package acme.features.authenticated.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messages.Message;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedMessageShowService implements AbstractShowService<Authenticated, Message> {

	@Autowired
	AuthenticatedMessageRepository repository;


	@Override
	public boolean authorise(final Request<Message> request) {
		assert request != null;
		Integer threadId = this.repository.findThreadIdByMessageId(request.getModel().getInteger("id"));
		Principal principal = request.getPrincipal();
		int authenticatedId = principal.getActiveRoleId();
		Integer count = this.repository.countNumberUserThreadByAuthenticatedIdAndThreadId(authenticatedId, threadId);

		return count == 1;
	}

	@Override
	public void unbind(final Request<Message> request, final Message entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "moment", "tags", "body", "authenticated.userAccount.username");

	}

	@Override
	public Message findOne(final Request<Message> request) {

		assert request != null;

		Message result;
		int id;
		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);
		return result;
	}

}
