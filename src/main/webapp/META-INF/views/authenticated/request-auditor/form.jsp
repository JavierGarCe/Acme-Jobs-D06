<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<jstl:if test="${canShow}">
<script type="text/javascript">
	redirect('/authenticated/request-auditor/show');
</script>
</jstl:if>
<acme:form readonly="${command == 'show'}">
	<jstl:if test="${command == 'show'}">
		<acme:form-select code="employer.application.form.label.status" path="status" readonly="true">
		<jstl:choose>
			<jstl:when test="${status == 'PENDING' }">
				<jstl:set var="pendingSelected" value="true" />
			</jstl:when>
			<jstl:otherwise>
				<jstl:set var="pendingSelected" value="false" />
			</jstl:otherwise>
		</jstl:choose>
		<jstl:choose>
			<jstl:when test="${status == 'REJECTED' }">
				<jstl:set var="rejectedSelected" value="true" />
			</jstl:when>
			<jstl:otherwise>
				<jstl:set var="rejectedSelected" value="false" />
			</jstl:otherwise>
		</jstl:choose>
		<acme:form-option code="authenticated.request.auditor.form.label.status.pending" value="PENDING" selected="${pendingSelected}" />
		<acme:form-option code="authenticated.request.auditor.form.label.status.rejected" value="REJECTED" selected="${rejectedSelected}" />
	</acme:form-select>
	
	</jstl:if>
	<acme:form-textarea code="authenticated.auditor.form.label.firm" path="firm"/>
	<acme:form-textarea code="authenticated.auditor.form.label.responsabilityStatement" path="responsabilityStatement"/>
	
	<jstl:if test="${command == 'show' && status == 'REJECTED'}">
	<button type="button" onclick="javascript: redirect('/authenticated/request-auditor/create_other')" class="btn btn-primary">
		<acme:message code="authenticated.auditor.form.button.create.other"/>
	</button>
	</jstl:if>
	<acme:form-submit test="${command == 'create_other'}" code="authenticated.auditor.form.button.create" action="/authenticated/request-auditor/create_other"/>
	<acme:form-submit test="${command == 'create'}" code="authenticated.auditor.form.button.create" action="/authenticated/request-auditor/create"/>
	<acme:form-return code="authenticated.auditor.form.button.return"/>
</acme:form>
