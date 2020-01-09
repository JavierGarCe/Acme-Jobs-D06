
package acme.entities.requestAuditors;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import acme.datatypes.ApplicationStatus;
import acme.framework.entities.Authenticated;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "status"), @Index(columnList = "finished")
})
public class RequestAuditor extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	private String				firm;

	@NotBlank
	private String				responsabilityStatement;

	@NotNull
	private ApplicationStatus	status;

	@NotNull
	private Boolean				finished;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Authenticated		authenticated;
}
