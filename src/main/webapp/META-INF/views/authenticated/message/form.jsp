

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form >
<input id ="threadId" name="threadId" type="hidden" value="${param.threadId}"/>
	<acme:form-textbox code="authenticated.message.form.label.title" path="title" />
	<acme:form-textarea code="authenticated.message.form.label.body" path="body" />
	<acme:form-textarea code="authenticated.message.form.label.tags" path="tags" />
	<jstl:if test="${command == 'show' }">
	<acme:form-textarea  code="authenticated.message.form.label.user" path="authenticated.userAccount.username" />
	<acme:form-moment code="authenticated.message.form.label.moment" path="moment" />

	</jstl:if>
	<jstl:if test="${command == 'create' }">
		<acme:form-checkbox code="authenticated.message.form.checkbox" path="confirm"/>
</jstl:if>
	
	

		<acme:form-submit
		test = "${command == 'create' }"
			code = "authenticated.Message.form.button.create"
			action = "/authenticated/message/create" />


	
	<acme:form-return code="authenticated.message.form.button.return"/>


</acme:form>