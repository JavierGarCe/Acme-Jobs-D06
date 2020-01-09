
package acme.entities.ganforninaBulletins;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GanforninaBulletin extends DomainEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	private String				company;

	@NotBlank
	private String				description;

	@NotNull
	@Min(value = 1)
	private Double				salary;

	@NotNull
	@Min(value = 1)
	private Integer				vacancies;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				moment;

}
