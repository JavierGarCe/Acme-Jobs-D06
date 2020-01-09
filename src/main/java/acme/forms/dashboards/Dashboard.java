
package acme.forms.dashboards;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dashboard implements Serializable {

	private static final long	serialVersionUID	= 1L;

	Integer						numberOfAnnoucements;
	Integer						numberOfCompanyRecords;
	Integer						numberOfInvestorRecords;
	Double						minActiveRequests;
	Double						maxActiveRequests;
	Double						avgActiveRequests;
	Double						stDevRActiveRequests;
	Double						minActiveOffers;
	Double						maxActiveOffers;
	Double						avgActiveOffers;
	Double						stDevMaxActiveOffers;
	Double						stDevMinActiveOffers;
	Double						avgNumberOfJobsPerEmployer;
	Double						avgNumberOfApplicationsPerEmployer;
	Double						avgNumberOfApplicationsPerWorker;
}
