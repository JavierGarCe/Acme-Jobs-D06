
package acme.features.administrator.chart;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.chart.Chart;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorChartShowService implements AbstractShowService<Administrator, Chart> {

	@Autowired
	AdministratorChartRepository repository;


	@Override
	public boolean authorise(final Request<Chart> request) {

		assert request != null;

		return true;

	}

	@Override
	public void unbind(final Request<Chart> request, final Chart entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "companiesBySector", "investorsBySector", "sectores", "jobsByStatusRatio", "applicationsByStatusRatio", "pendingApplicationsLastMonth", "acceptedApplicationsLastMonth", "rejectedApplicationsLastMonth", "dates");

	}

	@Override
	public Chart findOne(final Request<Chart> request) {
		assert request != null;

		Chart res = new Chart();
		res.setCompaniesBySector(this.repository.companiesBySector());
		res.setInvestorsBySector(this.repository.investorsBySector());

		Set<String> sectores = new HashSet<>();
		sectores.addAll(this.repository.findCompaniesSectors());
		sectores.addAll(this.repository.findInvestorSectors());
		res.setSectores(sectores);

		res.setJobsByStatusRatio(this.repository.jobsByStatusRatio());
		res.setApplicationsByStatusRatio(this.repository.applicationsByStatusRatio());

		Calendar cal = new GregorianCalendar();
		Date nowDate = new Date(System.currentTimeMillis());
		cal.setTime(nowDate);
		cal.add(Calendar.DATE, -28); //Restar 4 semanas
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String[] dates = new String[28];
		for (int i = 0; i < dates.length; i++) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
			dates[i] = formatter.format(cal.getTime());
		}
		res.setDates(dates);
		cal.setTime(nowDate);
		cal.add(Calendar.DATE, -28); //Restar 4 semanas
		res.setAcceptedApplicationsLastMonth(this.repository.findAcceptedApplicationsLastMonth(cal.getTime()));
		res.setPendingApplicationsLastMonth(this.repository.findPendingApplicationsLastMonth(cal.getTime()));
		res.setRejectedApplicationsLastMonth(this.repository.findRejectedApplicationsLastMonth(cal.getTime()));

		return res;

	}

}
