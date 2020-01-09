
package acme.features.administrator.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("select count(*) from Announcement")
	Integer numberOfAnnouncements();

	@Query("select count(*) from CompanyRecord c")
	Integer numCompanyRecord();

	@Query("select count(*) from InvestorRecord i ")
	Integer numInvestorRecord();

	@Query("select count(*) from Job")
	Integer numJobs();

	@Query("select count(*) from Application")
	Integer numApplications();

	@Query("select count(*) from Offer i where i.deadline >= current_date")
	Integer numOffers();

	@Query("select count(*) from Requests i where i.deadline >= current_date")
	Integer numRequests();

	@Query("select avg(reward.amount) from Requests r where r.deadline >= current_date")
	Double avgActiveRequests();

	@Query("select min(reward.amount) from Requests r where r.deadline >= current_date")
	Double minActiveRequests();

	@Query("select max(reward.amount) from Requests r where r.deadline >= current_date")
	Double maxActiveRequests();

	@Query("select stddev(reward.amount) from Requests r where r.deadline >= current_date")
	Double stDevActiveRequests();

	@Query("select avg(minReward.amount) from Offer o where o.deadline >= current_date")
	Double avgMinActiveOffer();

	@Query("select avg(maxReward.amount) from Offer o where o.deadline >= current_date")
	Double avgMaxActiveOffer();

	@Query("select min(minReward.amount) from Offer o where o.deadline >= current_date")
	Double minActiveOffer();

	@Query("select max(maxReward.amount) from Offer o where o.deadline >= current_date")
	Double maxActiveOffer();

	@Query("select stddev(maxReward.amount) from Offer o where o.deadline >= current_date")
	Double stDevMaxActiveOffer();

	@Query("select stddev(minReward.amount) from Offer o where o.deadline >= current_date")
	Double stDevMinActiveOffer();

	@Query("select avg(select count(j) from Job j where j.employer.id=e.id) from Employer e")
	Double averageNumberOfJobsPerEmployer();

	@Query("select avg(select count(a) from Application a where a.job.employer.id=e.id) from Employer e")
	Double averageNumberOfApplicationsPerEmployer();

	@Query("select avg(select count(a) from Application a where a.worker.id=w.id) from Worker w")
	Double averageNumberOfApplicationsPerWorker();

}
