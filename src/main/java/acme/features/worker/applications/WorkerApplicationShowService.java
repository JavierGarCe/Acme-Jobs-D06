
package acme.features.worker.applications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.applications.Application;
import acme.entities.roles.Worker;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class WorkerApplicationShowService implements AbstractShowService<Worker, Application> {

	@Autowired
	private WorkerApplicationRepository repository;


	@Override
	public boolean authorise(final Request<Application> request) {
		assert request != null;

		boolean result;
		int applicationId;
		Application application;
		Worker worker;
		Principal principal;

		applicationId = request.getModel().getInteger("id");
		application = this.repository.findOneById(applicationId);
		worker = application.getWorker();
		principal = request.getPrincipal();
		result = worker.getUserAccount().getId() == principal.getAccountId();

		return result;
	}

	@Override
	public void unbind(final Request<Application> request, final Application entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "reference", "moment", "status", "statement", "reference", "skills", "qualifications", "justification");
		model.setAttribute("idJob", this.repository.findId(entity.getId()));

	}

	@Override
	public Application findOne(final Request<Application> request) {
		assert request != null;

		Application res;
		int id;

		id = request.getModel().getInteger("id");
		res = this.repository.findOneById(id);

		return res;
	}
}
