/*
 * AuthenticatedSponsorCreateService.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.sponsor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Sponsor;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedSponsorCreateService implements AbstractCreateService<Authenticated, Sponsor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedSponsorRepository repository;

	// AbstractCreateService<Authenticated, Sponsor> ---------------------------


	@Override
	public boolean authorise(final Request<Sponsor> request) {
		assert request != null;
		return !request.getPrincipal().hasRole(Sponsor.class);

	}

	@Override
	public void validate(final Request<Sponsor> request, final Sponsor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		String creditCard;
		Boolean valida = true;
		Boolean numerico = true;
		if (!errors.hasErrors("creditCard") && !entity.getCreditCard().isEmpty()) {
			creditCard = entity.getCreditCard();
			numerico = AuthenticatedSponsorCreateService.isNumeric(creditCard);

		}
		if (!errors.hasErrors("creditCard") && !entity.getCreditCard().isEmpty() && numerico) {
			creditCard = entity.getCreditCard();
			valida = AuthenticatedSponsorCreateService.Check(creditCard.trim());
		}

		errors.state(request, numerico, "creditCard", "authenticated.message.error.creditCardNumero");
		errors.state(request, valida, "creditCard", "authenticated.message.error.creditCard");
	}

	@Override
	public void bind(final Request<Sponsor> request, final Sponsor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Sponsor> request, final Sponsor entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "organisationName", "creditCard");
	}

	@Override
	public Sponsor instantiate(final Request<Sponsor> request) {
		assert request != null;

		Sponsor result;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = request.getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		result = new Sponsor();
		result.setUserAccount(userAccount);

		return result;
	}

	@Override
	public void create(final Request<Sponsor> request, final Sponsor entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

	@Override
	public void onSuccess(final Request<Sponsor> request, final Response<Sponsor> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}

	public static boolean Check(final String ccNumber)

	{

		int sum = 0;
		boolean alternate = false;
		for (int i = ccNumber.length() - 1; i >= 0; i--) {
			int n = Integer.parseInt(ccNumber.substring(i, i + 1));
			if (alternate) {
				n *= 2;
				if (n > 9) {
					n = n % 10 + 1;
				}
			}
			sum += n;
			alternate = !alternate;
		}
		return sum % 10 == 0;
	}
	public static boolean isNumeric(final String cadena) {

		boolean resultado;

		try {
			Long.parseLong(cadena);
			resultado = true;
		} catch (NumberFormatException excepcion) {
			resultado = false;
		}

		return resultado;
	}

}
