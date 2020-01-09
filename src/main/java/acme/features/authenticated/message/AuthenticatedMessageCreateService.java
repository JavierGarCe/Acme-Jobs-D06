
package acme.features.authenticated.message;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.customization.Customization;
import acme.entities.messages.Message;
import acme.entities.threads.Thread;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.helpers.StringHelper;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedMessageCreateService implements AbstractCreateService<Authenticated, Message> {

	@Autowired
	AuthenticatedMessageRepository repository;


	@Override
	public boolean authorise(final Request<Message> request) {
		assert request != null;
		int idThread = request.getModel().getInteger("threadId");
		Principal principal = request.getPrincipal();
		int authenticatedId = principal.getActiveRoleId();
		Integer count = this.repository.countNumberUserThreadByAuthenticatedIdAndThreadId(authenticatedId, idThread);

		return count == 1;
	}

	@Override
	public void bind(final Request<Message> request, final Message entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "moment", "authenticated.userAccount.username");

	}

	@Override
	public void unbind(final Request<Message> request, final Message entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		if (request.isMethod(HttpMethod.GET)) {
			model.setAttribute("confirm", "false");
		} else {
			request.transfer(model, "confirm");
		}
		request.unbind(entity, model, "title", "tags", "body");

	}

	@Override
	public Message instantiate(final Request<Message> request) {
		Message result;
		result = new Message();
		Principal principal = request.getPrincipal();
		Authenticated aut = this.repository.findAuthenticated(principal.getAccountId());
		result.setAuthenticated(aut);
		return result;

	}

	@Override
	public void validate(final Request<Message> request, final Message entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		Boolean isConfirmed = request.getModel().getBoolean("confirm");
		errors.state(request, isConfirmed, "confirm", "authenticated.requests.error.must-confirm");

		Customization customization = this.repository.getCustomization();
		if (!StringHelper.isBlank(customization.getSpamword())) {
			String[] spamwords = customization.getSpamword().toLowerCase().split(",");
			if (!errors.hasErrors("title")) {
				String title = entity.getTitle().toLowerCase();
				int i = title.length();
				int b = 0;
				for (String spamword : spamwords) {
					if (title.contains(spamword.trim())) {
						b += spamword.length();
					}
				}

				boolean isSpam = b * 100 / i >= customization.getThreshold();
				errors.state(request, !isSpam, "title", "authenticated.message.error.spam");
			}

			if (!errors.hasErrors("body")) {

				String body = entity.getBody().toLowerCase();
				int i = body.length();
				int b = 0;
				for (String spamword : spamwords) {
					if (body.contains(spamword.trim())) {
						b += spamword.length();
					}

				}
				boolean isSpam = b * 100 / i >= customization.getThreshold();
				errors.state(request, !isSpam, "body", "authenticated.message.error.spam");
			}
			if (!errors.hasErrors("tags") && !entity.getTags().isEmpty()) {

				String tags = entity.getTags().toLowerCase();
				int i = tags.length();
				int b = 0;
				for (String spamword : spamwords) {
					if (tags.contains(spamword.trim())) {
						b += spamword.length();
					}
				}

				boolean isSpam = b * 100 / i >= customization.getThreshold();
				errors.state(request, !isSpam, "tags", "authenticated.message.error.spam");

			}

		}

	}

	@Override
	public void create(final Request<Message> request, final Message entity) {
		assert request != null;
		assert entity != null;
		Date moment;
		Thread thread;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);
		int threadId = request.getModel().getInteger("threadId");
		thread = this.repository.findThreadById(threadId);
		thread.getMessages().add(entity);

		this.repository.save(entity);
		this.repository.save(thread);

	}

}
