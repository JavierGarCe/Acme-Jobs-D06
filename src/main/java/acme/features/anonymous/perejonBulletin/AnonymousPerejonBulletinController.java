
package acme.features.anonymous.perejonBulletin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.perejonBulletins.perejonBulletin;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Anonymous;

@Controller
@RequestMapping("/anonymous/perejonBulletin/")
public class AnonymousPerejonBulletinController extends AbstractController<Anonymous, perejonBulletin> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnonymousPerejonBulletinListService		listService;
	@Autowired
	private AnonymousPerejonBulletinCreateService	createService;


	// Constructors -----------------------------------------------------------

	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);

	}

}
