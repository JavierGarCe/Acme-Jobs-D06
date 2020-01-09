
package acme.features.anonymous.perejonBulletin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.perejonBulletins.perejonBulletin;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractCreateService;

@Service
public class AnonymousPerejonBulletinCreateService implements AbstractCreateService<Anonymous, perejonBulletin> {

	@Autowired
	AnonymousPerejonBulletinRepository repository;


	@Override
	public boolean authorise(final Request<perejonBulletin> request) {
		assert request != null;

		return true;
	}

	@Override
	public perejonBulletin instantiate(final Request<perejonBulletin> request) {
		assert request != null;

		perejonBulletin result;
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);

		result = new perejonBulletin();
		result.setNombre("John Doe");
		result.setAspiraciones("Lorem ipsum");
		result.setMoment(moment);

		return result;
	}

	@Override
	public void unbind(final Request<perejonBulletin> request, final perejonBulletin entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "nombre", "aspiraciones");

	}

	@Override
	public void bind(final Request<perejonBulletin> request, final perejonBulletin entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);

	}

	@Override
	public void validate(final Request<perejonBulletin> request, final perejonBulletin entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void create(final Request<perejonBulletin> request, final perejonBulletin entity) {
		assert request != null;
		assert entity != null;

		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);
		this.repository.save(entity);
	}

}
