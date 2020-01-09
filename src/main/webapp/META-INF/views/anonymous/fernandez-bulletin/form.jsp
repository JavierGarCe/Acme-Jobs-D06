<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="anonymous.fernandezbulletin.form.label.author" path="author"/>
	<acme:form-textbox code="anonymous.fernandezbulletin.form.label.text" path="text"/>
	<acme:form-textbox code="anonymous.fernandezbulletin.form.label.country" path="country"/>
	
	<acme:form-submit code="anonymous.fernandezbulletin.form.button.create" action="/anonymous/fernandez-bulletin/create"/>
  	<acme:form-return code="anonymous.fernandezbulletin.form.button.return"/>
</acme:form>