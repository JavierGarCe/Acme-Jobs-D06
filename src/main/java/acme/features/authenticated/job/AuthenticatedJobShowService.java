
package acme.features.authenticated.job;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Status;
import acme.entities.jobs.Job;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedJobShowService implements AbstractShowService<Authenticated, Job> {

	@Autowired
	private AuthenticatedJobRepository repository;


	@Override
	public boolean authorise(final Request<Job> request) {
		assert request != null;
		int jobId;
		Job job;
		Status status;
		Date deadline;
		Boolean res = true;

		jobId = request.getModel().getInteger("id");

		job = this.repository.findOneById(jobId);

		status = job.getStatus();
		deadline = job.getDeadline();

		Calendar cal = new GregorianCalendar();

		if (!status.equals(Status.PUBLISHED)) {
			res = false;
		}

		if (deadline.before(cal.getTime())) {
			res = false;
		}

		return res;
	}

	@Override
	public void unbind(final Request<Job> request, final Job entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "moreInfo", "salary", "deadline", "reference", "status", "descriptor", "descriptor.description", "employer.userAccount.username");

	}

	@Override
	public Job findOne(final Request<Job> request) {
		assert request != null;

		Job res;
		int id;

		id = request.getModel().getInteger("id");
		res = this.repository.findOneById(id);

		res.getDescriptor().getDuties().size(); //desenrrollar

		return res;
	}

}
