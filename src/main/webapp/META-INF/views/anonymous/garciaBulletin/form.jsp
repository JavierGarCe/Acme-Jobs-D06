<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="anonymous.garciaBulletin.form.label.name" path="name"/>
	<acme:form-textbox code="anonymous.garciaBulletin.form.label.surname" path="surname"/>
	<acme:form-textbox code="anonymous.garciaBulletin.form.label.subject" path="subject"/>
	<acme:form-textarea code="anonymous.garciaBulletin.form.label.text" path="text"/>
	
	<acme:form-submit code="anonymous.garciaBulletin.form.button.create" action="/anonymous/garcia-bulletin/create"/>
	<acme:form-return code="anonymous.garciaBulletin.form.button.return"/>
</acme:form>
