/*
 * CustomCommand.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.components;

import acme.framework.components.Command;

public enum CustomCommand implements Command {

	LIST_TOP, LIST_DATE, SHOW_CHART, LIST_ALL, LIST_MINE, LIST_ACTIVE, LIST_OTHERS, LIST_NON_APPLIED, ACCEPT, REJECT, CREATE_OTHER, LIST_USERS, LIST_NON_INCLUDED
}
