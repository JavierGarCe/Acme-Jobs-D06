
package acme.features.employer.jobs;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractListService;

@Service
public class EmployerJobListService implements AbstractListService<Employer, Job> {

	@Autowired
	private EmployerJobRepository repository;


	@Override
	public boolean authorise(final Request<Job> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Job> request, final Job entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "salary", "deadline");
	}

	@Override
	public Collection<Job> findMany(final Request<Job> request) {
		assert request != null;

		Collection<Job> result;
		int id = request.getPrincipal().getActiveRoleId();
		result = this.repository.findManyByEmployerId(id);

		return result;
	}

}
