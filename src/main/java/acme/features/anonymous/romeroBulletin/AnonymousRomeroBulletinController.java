
package acme.features.anonymous.romeroBulletin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.romeroBulletins.RomeroBulletin;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Anonymous;

@Controller
@RequestMapping("/anonymous/romero-bulletin/")
public class AnonymousRomeroBulletinController extends AbstractController<Anonymous, RomeroBulletin> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnonymousRomeroBulletinListService		listService;
	@Autowired
	private AnonymousRomeroBulletinCreateService	createService;


	// Constructors -----------------------------------------------------------

	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);

	}

}
