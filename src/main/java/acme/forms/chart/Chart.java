
package acme.forms.chart;

import java.io.Serializable;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Chart implements Serializable {

	private static final long	serialVersionUID	= 1L;
	Object[]					companiesBySector;
	Object[]					investorsBySector;
	Object[]					jobsByStatusRatio;
	Object[]					applicationsByStatusRatio;
	Set<String>					sectores;
	Object[]					pendingApplicationsLastMonth;
	Object[]					acceptedApplicationsLastMonth;
	Object[]					rejectedApplicationsLastMonth;
	String[]					dates;
}
