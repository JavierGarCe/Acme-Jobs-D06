
package acme.entities.companyRecords;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "stars")
})
public class CompanyRecord extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	private String				name;

	@NotBlank
	private String				sector;

	@NotBlank
	private String				ceo;

	@NotBlank
	private String				activities;

	@NotBlank
	@URL
	private String				website;

	@Pattern(regexp = "^([+][0-9]{1,3}\\s)?([(][0-9]{1,4}[)]\\s)?[0-9]{6,10}$")
	@NotBlank
	private String				phone;

	@NotNull
	private Boolean				incorporated;

	@Range(min = 0, max = 5)
	private Integer				stars;

	@NotBlank
	@Email
	private String				email;

}
