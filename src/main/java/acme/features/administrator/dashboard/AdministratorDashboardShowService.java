
package acme.features.administrator.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.dashboards.Dashboard;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorDashboardShowService implements AbstractShowService<Administrator, Dashboard> {

	@Autowired
	private AdministratorDashboardRepository repository;


	@Override
	public boolean authorise(final Request<Dashboard> request) {
		assert request != null;
		return true;
	}

	@Override
	public void unbind(final Request<Dashboard> request, final Dashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "numberOfAnnoucements", "numberOfCompanyRecords", "numberOfInvestorRecords", "minActiveRequests", "maxActiveRequests", "avgActiveRequests", "stDevRActiveRequests", "minActiveOffers", "maxActiveOffers",
			"avgActiveOffers", "stDevMaxActiveOffers", "stDevMinActiveOffers", "avgNumberOfJobsPerEmployer", "avgNumberOfApplicationsPerEmployer", "avgNumberOfApplicationsPerWorker");

	}

	@Override
	public Dashboard findOne(final Request<Dashboard> request) {
		assert request != null;

		Dashboard result;

		Double minActiveRequests = .0;
		Double maxActiveRequests = .0;
		Double avgActiveRequests = .0;
		Double stDevRActiveRequests = .0;
		Double minActiveOffers = .0;
		Double maxActiveOffers = .0;
		Double avgMaxActiveOffers = .0;
		Double avgMinActiveOffers = .0;
		Double avgActiveOffers = .0;
		Double stDevMaxActiveOffers = .0;
		Double stDevMinActiveOffers = .0;
		Double averageNumberOfJobsPerEmployer = .0;
		Double averageNumberOfApplicationsPerEmployer = .0;
		Double averageNumberOfApplicationsPerWorker = .0;

		Integer numberOfAnnoucements = this.repository.numberOfAnnouncements();
		Integer numberOfCompanyRecords = this.repository.numCompanyRecord();
		Integer numberOfInvestorRecords = this.repository.numInvestorRecord();

		if (this.repository.numOffers() != 0) {
			minActiveOffers = this.repository.minActiveOffer();
			maxActiveOffers = this.repository.maxActiveOffer();
			avgMaxActiveOffers = this.repository.avgMaxActiveOffer();
			avgMinActiveOffers = this.repository.avgMinActiveOffer();
			avgActiveOffers = (avgMaxActiveOffers + avgMinActiveOffers) / 2.0;
			stDevMaxActiveOffers = this.repository.stDevMaxActiveOffer();
			stDevMinActiveOffers = this.repository.stDevMinActiveOffer();
		}

		if (this.repository.numRequests() != 0) {
			minActiveRequests = this.repository.minActiveRequests();
			maxActiveRequests = this.repository.maxActiveRequests();
			avgActiveRequests = this.repository.avgActiveRequests();
			stDevRActiveRequests = this.repository.stDevActiveRequests();
		}

		if (this.repository.numJobs() != 0) {
			averageNumberOfJobsPerEmployer = this.repository.averageNumberOfJobsPerEmployer();
		}

		if (this.repository.numApplications() != 0) {
			averageNumberOfApplicationsPerEmployer = this.repository.averageNumberOfApplicationsPerEmployer();
			averageNumberOfApplicationsPerWorker = this.repository.averageNumberOfApplicationsPerWorker();
		}
		result = new Dashboard();

		result.setNumberOfAnnoucements(numberOfAnnoucements);
		result.setNumberOfCompanyRecords(numberOfCompanyRecords);
		result.setNumberOfInvestorRecords(numberOfInvestorRecords);
		result.setMinActiveRequests(minActiveRequests);
		result.setMaxActiveRequests(maxActiveRequests);
		result.setAvgActiveRequests(avgActiveRequests);
		result.setStDevRActiveRequests(stDevRActiveRequests);
		result.setMinActiveOffers(minActiveOffers);
		result.setMaxActiveOffers(maxActiveOffers);
		result.setAvgActiveOffers(avgActiveOffers);
		result.setStDevMinActiveOffers(stDevMinActiveOffers);
		result.setStDevMaxActiveOffers(stDevMaxActiveOffers);
		result.setAvgNumberOfJobsPerEmployer(averageNumberOfJobsPerEmployer);
		result.setAvgNumberOfApplicationsPerEmployer(averageNumberOfApplicationsPerEmployer);
		result.setAvgNumberOfApplicationsPerWorker(averageNumberOfApplicationsPerWorker);

		return result;
	}
}
