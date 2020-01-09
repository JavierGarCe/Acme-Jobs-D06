<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="provider.requests.form.label.title" path="title" />
	<acme:form-textarea code="provider.requests.form.label.moreInfo" path="moreInfo" />
	<acme:form-money code="provider.requests.form.label.reward" path="reward"/>
	<acme:form-textbox code="provider.requests.form.label.ticker" path="ticker" placeholder="RAAAA-99999"/>
	<acme:form-moment code="provider.requests.form.label.deadline" path="deadline" />
	<acme:form-checkbox code="provider.requests.form.label.confirm" path="confirm"/>
	
  	<acme:form-submit code="provider.requests.form.button.create" action="/provider/requests/create"/>
  	<acme:form-return code="provider.requests.form.button.return"/>
</acme:form>