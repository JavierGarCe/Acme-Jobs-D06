
package acme.entities.jobs;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Descriptor extends DomainEntity {

	private static final long		serialVersionUID	= 1L;

	@NotBlank
	private String					description;

	@OneToMany
	private Collection<@Valid Duty>	duties;
}
