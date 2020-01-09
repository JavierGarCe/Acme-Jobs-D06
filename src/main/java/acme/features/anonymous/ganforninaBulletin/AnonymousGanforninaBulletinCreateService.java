
package acme.features.anonymous.ganforninaBulletin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.ganforninaBulletins.GanforninaBulletin;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractCreateService;

@Service
public class AnonymousGanforninaBulletinCreateService implements AbstractCreateService<Anonymous, GanforninaBulletin> {

	@Autowired
	AnonymousGanforninaBulletinRepository repository;


	@Override
	public boolean authorise(final Request<GanforninaBulletin> request) {
		assert request != null;

		return true;
	}

	@Override
	public GanforninaBulletin instantiate(final Request<GanforninaBulletin> request) {
		assert request != null;

		GanforninaBulletin result;
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);

		result = new GanforninaBulletin();
		result.setCompany("DistribGal S.A.");
		result.setDescription("Comercial para oficina");
		result.setSalary(1200.50);
		result.setVacancies(100);
		result.setMoment(moment);

		return result;
	}

	@Override
	public void unbind(final Request<GanforninaBulletin> request, final GanforninaBulletin entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "company", "description", "salary", "vacancies");

	}

	@Override
	public void bind(final Request<GanforninaBulletin> request, final GanforninaBulletin entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);

	}

	@Override
	public void validate(final Request<GanforninaBulletin> request, final GanforninaBulletin entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void create(final Request<GanforninaBulletin> request, final GanforninaBulletin entity) {
		assert request != null;
		assert entity != null;

		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);
		this.repository.save(entity);
	}

}
