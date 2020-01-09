
package acme.features.administrator.chart;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorChartRepository extends AbstractRepository {

	@Query("select count(h), h.sector from CompanyRecord h group by h.sector")
	Object[] companiesBySector();

	@Query("select count(h), h.sector from InvestorRecord h group by h.sector")
	Object[] investorsBySector();

	@Query("select 1.0*count(h)/(select count(*) from Job), h.status from Job h group by h.status")
	Object[] jobsByStatusRatio();

	@Query("select 1.0*count(h)/(select count(*) from Application), h.status from Application h group by h.status")
	Object[] applicationsByStatusRatio();

	@Query("select DATE(a.moment), count(a) from Application a where a.status=0 and a.moment > ?1 group by DAY(a.moment)")
	Object[] findPendingApplicationsLastMonth(Date date);

	@Query("select DATE(a.moment), count(a) from Application a where a.status=1 and a.moment > ?1 group by DAY(a.moment)")
	Object[] findAcceptedApplicationsLastMonth(Date date);

	@Query("select DATE(a.moment), count(a) from Application a where a.status=2 and a.moment > ?1 group by DAY(a.moment)")
	Object[] findRejectedApplicationsLastMonth(Date date);

	@Query("select c.sector from CompanyRecord c")
	Collection<String> findCompaniesSectors();

	@Query("select i.sector from InvestorRecord i")
	Collection<String> findInvestorSectors();
}
