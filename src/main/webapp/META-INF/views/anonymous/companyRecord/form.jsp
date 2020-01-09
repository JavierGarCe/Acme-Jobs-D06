<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly="true">
	<acme:form-textbox code="anonymous.companyRecord.form.label.name" path="name"/>
	<acme:form-textbox code="anonymous.companyRecord.form.label.sector" path="sector"/>
	<acme:form-textbox code="anonymous.companyRecord.form.label.ceo" path="ceo"/>
	<acme:form-textarea code="anonymous.companyRecord.form.label.activities" path="activities"/>
	<acme:form-url code="anonymous.companyRecord.form.label.website" path="website"/>
	<acme:form-textbox code="anonymous.companyRecord.form.label.phoneNumber" path="phone"/>
	<acme:form-checkbox code="anonymous.companyRecord.form.label.inc" path="incorporated"/>
	<acme:form-textbox code="anonymous.companyRecord.form.label.stars" path="stars"/>
	<acme:form-textbox code="anonymous.companyRecord.form.label.email" path="email"/>

	
	<acme:form-return code="anonymous.companyRecord.form.button.return"/>
</acme:form>
