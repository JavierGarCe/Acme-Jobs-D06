
package acme.features.consumer.offers;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offers.Offer;
import acme.entities.roles.Consumer;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractCreateService;

@Service
public class ConsumerOfferCreateService implements AbstractCreateService<Consumer, Offer> {

	@Autowired
	ConsumerOfferRepository repository;


	@Override
	public boolean authorise(final Request<Offer> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Offer> request, final Offer entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "moment");

	}

	@Override
	public void unbind(final Request<Offer> request, final Offer entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		if (request.isMethod(HttpMethod.GET)) {
			model.setAttribute("confirm", "false");
		} else {
			request.transfer(model, "confirm");
		}

		request.unbind(entity, model, "title", "deadline", "description", "minReward", "maxReward", "ticker");
	}

	@Override
	public Offer instantiate(final Request<Offer> request) {
		Offer result;
		result = new Offer();
		return result;
	}

	@Override
	public void validate(final Request<Offer> request, final Offer entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Calendar calendar;
		Date present;
		Boolean isConfirmed = request.getModel().getBoolean("confirm");
		errors.state(request, isConfirmed, "confirm", "consumer.requests.error.must-confirm");

		if (!errors.hasErrors("deadline")) {
			calendar = new GregorianCalendar();
			present = calendar.getTime();
			boolean isDeadlineFuture = entity.getDeadline().after(present);
			errors.state(request, isDeadlineFuture, "deadline", "consumer.offer.error.not-future-deadline");
		}
		Money minReward = entity.getMinReward();
		Money maxReward = entity.getMaxReward();
		if (!errors.hasErrors("minReward")) {
			boolean isPositive = minReward.getAmount() > 0;
			errors.state(request, isPositive, "minReward", "consumer.offer.error.not-positive-minReward");
			boolean isEUR = minReward.getCurrency().equals("EUR");
			errors.state(request, isEUR, "minReward", "consumer.offer.error.not-EUR-minReward");
		}
		if (!errors.hasErrors("maxReward")) {
			boolean isPositive = maxReward.getAmount() > 0;
			errors.state(request, isPositive, "maxReward", "consumer.offer.error.not-positive-maxReward");
			boolean isEUR = maxReward.getCurrency().equals("EUR");
			errors.state(request, isEUR, "maxReward", "consumer.offer.error.not-EUR-maxReward");

		}
		if (!errors.hasErrors("minReward") && !errors.hasErrors("maxReward")) {
			boolean isGreater = maxReward.getAmount() > minReward.getAmount();
			errors.state(request, isGreater, "maxReward", "consumer.offer.error.not-greater-maxReward");
			errors.state(request, isGreater, "minReward", "consumer.offer.error.not-greater-maxReward");
		}
		if (!errors.hasErrors("ticker")) {
			boolean isUnique = this.repository.findOneByTicker(entity.getTicker()) == null;
			errors.state(request, isUnique, "ticker", "consumer.offer.error.not-unique-ticker");
		}
	}

	@Override
	public void create(final Request<Offer> request, final Offer entity) {
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);
		this.repository.save(entity);

	}

}
