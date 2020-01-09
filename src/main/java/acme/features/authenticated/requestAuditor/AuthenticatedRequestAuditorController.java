
package acme.features.authenticated.requestAuditor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.requestAuditors.RequestAuditor;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Authenticated;

@Controller
@RequestMapping("/authenticated/request-auditor/")
public class AuthenticatedRequestAuditorController extends AbstractController<Authenticated, RequestAuditor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedRequestAuditorCreateService		createService;

	@Autowired
	private AuthenticatedRequestAuditorShowService			showService;

	@Autowired
	private AuthenticatedRequestAuditorCreateOtherService	createOtherService;


	// Constructors -----------------------------------------------------------

	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addCustomCommand(CustomCommand.CREATE_OTHER, BasicCommand.CREATE, this.createOtherService);
	}

}
