
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="authenticated.message.list.label.title" path="title" />
	<acme:list-column code="authenticated.message.list.label.body" path="body" />
	<acme:list-column code="authenticated.message.list.label.tags" path="tags" />
</acme:list>
	
	<button type="button" onclick="javascript: pushReturnUrl('/authenticated/message/list?id=${param.id}'); redirect('/authenticated/message/create?threadId=${param.id}')" class="btn btn-primary">
		<acme:message code="authenticated.message.form.button.post" />
	</button>
<acme:form-return code="authenticated.message.form.button.return"/>