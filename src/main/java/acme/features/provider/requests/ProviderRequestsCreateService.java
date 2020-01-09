
package acme.features.provider.requests;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.requests.Requests;
import acme.entities.roles.Provider;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class ProviderRequestsCreateService implements AbstractCreateService<Provider, Requests> {

	@Autowired
	ProviderRequestsRepository repository;


	@Override
	public boolean authorise(final Request<Requests> request) {
		assert request != null;

		return true;
	}

	@Override
	public Requests instantiate(final Request<Requests> request) {
		assert request != null;

		Requests result = new Requests();

		return result;
	}

	@Override
	public void unbind(final Request<Requests> request, final Requests entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "moreInfo", "reward", "ticker", "deadline");

		if (request.isMethod(HttpMethod.GET)) {
			model.setAttribute("confirm", "false");
		} else {
			request.transfer(model, "confirm");
		}
	}

	@Override
	public void bind(final Request<Requests> request, final Requests entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);

	}

	@Override
	public void validate(final Request<Requests> request, final Requests entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Boolean isConfirmed, isDuplicated, currencyValid, deadlineValid, amountPositive;
		Calendar calendar;

		isConfirmed = request.getModel().getBoolean("confirm");
		errors.state(request, isConfirmed, "confirm", "provider.requests.error.must-confirm");
		isDuplicated = this.repository.findOnebyTicker(entity.getTicker()) != null;
		errors.state(request, !isDuplicated, "ticker", "provider.ticker.error.duplicated");

		if (!errors.hasErrors("reward")) {
			currencyValid = entity.getReward().getCurrency().equals("EUR");
			errors.state(request, currencyValid, "reward", "provider.reward.error.currency");

			amountPositive = entity.getReward().getAmount() > 0;
			errors.state(request, amountPositive, "reward", "provider.reward.error.amount");

		}
		if (!errors.hasErrors("deadline")) {
			calendar = new GregorianCalendar();
			deadlineValid = entity.getDeadline().after(calendar.getTime());
			errors.state(request, deadlineValid, "deadline", "provider.deadline.error.future");
		}

	}

	@Override
	public void create(final Request<Requests> request, final Requests entity) {
		assert request != null;
		assert entity != null;

		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);
		this.repository.save(entity);
	}

}
