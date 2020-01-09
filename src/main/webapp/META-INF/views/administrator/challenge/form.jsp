<%--
- form.jsp
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="administrator.Challenge.form.label.title" path="title"/>
	<acme:form-moment code="administrator.Challenge.form.label.deadline" path="deadline"/>
	<acme:form-textbox code="administrator.Challenge.form.label.description" path="description"/>
	<acme:form-panel code="administrator.Challenge.form.panel.goldLevel">
	<acme:form-textbox code="administrator.Challenge.form.label.goldGoal" path="goldGoal"/>
	<acme:form-textbox code="administrator.Challenge.form.label.goldReward" path="goldReward"/>
	</acme:form-panel>
	<acme:form-panel code="administrator.Challenge.form.panel.silverLevel">
		<acme:form-textbox code="administrator.Challenge.form.label.silverGoal" path="silverGoal"/>
	<acme:form-textbox code="administrator.Challenge.form.label.silverReward" path="silverReward"/>
	</acme:form-panel>
	<acme:form-panel code="administrator.Challenge.form.panel.bronzeLevel">
		<acme:form-textbox code="administrator.Challenge.form.label.bronzeGoal" path="bronzeGoal"/>
	<acme:form-textbox code="administrator.Challenge.form.label.bronzeReward" path="bronzeReward"/>
	</acme:form-panel>
	
	<acme:form-submit test="${command == 'show' }"
			code = "administrator.Challenge.form.button.update"
			action = "/administrator/challenge/update" />
	<acme:form-submit test="${command == 'show' }"
			code = "administrator.Challenge.form.button.delete"
			action = "/administrator/challenge/delete" />
	<acme:form-submit test="${command == 'create' }"
			code = "administrator.Challenge.form.button.create"
			action = "/administrator/challenge/create" />
	<acme:form-submit test="${command == 'update' }"
			code = "administrator.Challenge.form.button.update"
			action = "/administrator/challenge/update" />
	<acme:form-submit test="${command == 'delete' }"
			code = "administrator.Challenge.form.button.delete"
			action = "/administrator/challenge/delete" />
	
	<acme:form-return code="administrator.Challenge.form.button.return"/>
	

</acme:form>
