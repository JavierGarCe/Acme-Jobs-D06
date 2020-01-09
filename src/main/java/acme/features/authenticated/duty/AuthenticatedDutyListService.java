
package acme.features.authenticated.duty;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Status;
import acme.entities.jobs.Duty;
import acme.entities.jobs.Job;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractListService;

@Service
public class AuthenticatedDutyListService implements AbstractListService<Authenticated, Duty> {

	@Autowired
	private AuthenticatedDutyRepository repository;


	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;
		boolean res;
		int jobId = request.getModel().getInteger("id");
		Calendar calendar = new GregorianCalendar();
		Job job = this.repository.findJobById(jobId);
		res = job.getDeadline().after(calendar.getTime()) && job.getStatus().equals(Status.PUBLISHED);
		return res;
	}

	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "description");

	}

	@Override
	public Collection<Duty> findMany(final Request<Duty> request) {
		assert request != null;

		Collection<Duty> result;
		int id;

		id = request.getModel().getInteger("id");

		result = this.repository.findDutiesByJobId(id);

		return result;
	}

}
