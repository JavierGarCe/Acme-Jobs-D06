<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly="true">
	<acme:form-textbox code="authenticated.offer.form.labbel.title" path="title"/>
	<acme:form-textbox code="authenticated.offer.form.labbel.moment" path="moment"/>
	<acme:form-textbox code="authenticated.offer.form.labbel.deadline" path="deadline"/>
	<acme:form-textarea code="authenticated.offer.form.label.description" path="description"/>
	<acme:form-textbox code="authenticated.offer.form.labbel.ticker" path="ticker"/>
	<acme:form-panel code="authenticated.offer.form.panel.reward">
	<acme:form-textbox code="authenticated.offer.form.labbel.minReward" path="minReward"/>
	<acme:form-textbox code="authenticated.offer.form.labbel.maxReward" path="maxReward"/>
	</acme:form-panel>
	
	<acme:form-return code="authenticated.offer.form.button.return"/>
</acme:form>
