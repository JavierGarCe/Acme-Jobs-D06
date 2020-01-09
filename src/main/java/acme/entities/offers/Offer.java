
package acme.entities.offers;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import acme.framework.datatypes.Money;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "deadline"), @Index(columnList = "ticker")
})
public class Offer extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	private String				title;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				moment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				deadline;

	@NotBlank
	private String				description;

	@NotNull
	private Money				minReward;

	@NotNull
	private Money				maxReward;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^O[a-zA-Z]{4}-[0-9]{5}$")
	private String				ticker;


	@Transient
	public String getRewardRange() {
		StringBuilder result;

		result = new StringBuilder();
		result.append(this.minReward.getAmount());
		result.append(this.minReward.getCurrency());
		result.append(" - ");
		result.append(this.maxReward.getAmount());
		result.append(this.maxReward.getCurrency());

		return result.toString();
	}

}
