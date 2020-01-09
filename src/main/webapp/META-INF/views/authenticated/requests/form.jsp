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

<acme:form readonly="true">
	<acme:form-textbox code="authenticated.requests.form.label.title" path="title" />
	<acme:form-textarea code="authenticated.requests.form.label.moreInfo" path="moreInfo" />
	<acme:form-money code="authenticated.requests.form.label.reward" path="reward"/>
	<acme:form-textbox code="authenticated.requests.form.label.ticker" path="ticker" />
	<acme:form-textbox code="authenticated.requests.form.label.deadline" path="deadline" />
	
  	<acme:form-return code="authenticated.requests.form.button.return"/>
</acme:form>