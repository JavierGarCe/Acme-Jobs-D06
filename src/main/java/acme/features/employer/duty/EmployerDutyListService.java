
package acme.features.employer.duty;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Status;
import acme.entities.jobs.Duty;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;

@Service
public class EmployerDutyListService implements AbstractListService<Employer, Duty> {

	@Autowired
	private EmployerDutyRepository	repository;

	private boolean					statusDraft;

	private int						tam;


	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;
		boolean result;
		int jobId;
		Job job;
		Employer employer;
		Principal principal;

		jobId = request.getModel().getInteger("jobId");
		job = this.repository.findOneJobById(jobId);
		employer = job.getEmployer();
		principal = request.getPrincipal();
		result = employer.getUserAccount().getId() == principal.getAccountId();

		return result;
	}

	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "title", "percentage");
		if (this.tam == 1) {
			model.setAttribute("statusDraft[0]", this.statusDraft);
		} else {
			model.setAttribute("statusDraft", this.statusDraft);
		}
	}

	@Override
	public Collection<Duty> findMany(final Request<Duty> request) {
		assert request != null;
		Collection<Duty> result;
		int id;
		id = request.getModel().getInteger("jobId");
		this.statusDraft = this.repository.findOneJobById(id).getStatus().equals(Status.DRAFT);
		result = this.repository.findDutiesByJobId(id);
		this.tam = result.size();
		return result;
	}

}
