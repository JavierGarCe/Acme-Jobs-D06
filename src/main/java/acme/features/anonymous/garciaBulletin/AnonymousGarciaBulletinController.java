
package acme.features.anonymous.garciaBulletin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.garciaBulletins.GarciaBulletin;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Anonymous;

@Controller
@RequestMapping("/anonymous/garcia-bulletin/")
public class AnonymousGarciaBulletinController extends AbstractController<Anonymous, GarciaBulletin> {

	// Internal state -------------------------------------------------------

	@Autowired
	private AnonymousGarciaBulletinListService		listService;

	@Autowired
	private AnonymousGarciaBulletinCreateService	createService;


	// Constructors ---------------------------------------------------------

	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);

	}
}
