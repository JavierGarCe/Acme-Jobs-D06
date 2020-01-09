
package acme.features.employer.duty;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.customization.Customization;
import acme.entities.jobs.Descriptor;
import acme.entities.jobs.Duty;
import acme.entities.jobs.Job;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface EmployerDutyRepository extends AbstractRepository {

	@Query("select d from Duty d where d.id = ?1")
	Duty findOneById(int id);

	@Query("select d.duties from Job j join j.descriptor d where j.id = ?1")
	Collection<Duty> findDutiesByJobId(int id);

	@Query("select j.id from Job j join j.descriptor.duties d where d.id= ?1")
	Integer findJobIdByDutyId(int id);

	@Query("select j from Job j where j.id = ?1")
	Job findOneJobById(int id);

	@Query("select j.employer.id from Job j where j.id = ?1")
	Integer findEmployerIdByJobId(int id);

	@Query("select sum(d.percentage) from Job j join j.descriptor.duties d where j.id = ?1")
	Double countPercentage(int id);

	@Query("select d from Descriptor d join d.duties du where du.id = ?1")
	Descriptor findOneDescriptionById(int id);

	@Query("select c from Customization c")
	Customization getCustomization();
}
