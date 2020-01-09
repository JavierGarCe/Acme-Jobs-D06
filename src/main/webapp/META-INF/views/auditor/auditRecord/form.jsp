<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly="${hasAccess}">
<input name="jobId" id="jobId" type="hidden" value="${param.jobId}"/>
	<acme:form-textbox code="auditor.auditRecord.form.label.title" path="title"/>
	<jstl:if test="${command != 'create'}">
	<acme:form-moment code="consumer.offer.form.labbel.moment" path="moment" readonly="true"/>
	</jstl:if>
<acme:form-select code="auditor.auditRecord.form.label.status" path="status">
		<jstl:choose>
			<jstl:when test="${status == 'PUBLISHED' }">
				<jstl:set var="publishedSelected" value="true" />
			</jstl:when>
			<jstl:otherwise>
				<jstl:set var="publishedSelected" value="false" />
			</jstl:otherwise>
		</jstl:choose>
		<jstl:choose>
			<jstl:when test="${status == 'DRAFT' }">
				<jstl:set var="draftSelected" value="true" />
			</jstl:when>
			<jstl:otherwise>
				<jstl:set var="draftSelected" value="false" />
			</jstl:otherwise>
		</jstl:choose>
		<acme:form-option code="auditor.auditRecord.form.label.status.published" value="PUBLISHED" selected="${publishedSelected}" />
		<acme:form-option code="auditor.auditRecord.form.label.status.draft" value="DRAFT" selected="${draftSelected}" />
	</acme:form-select>	
		<acme:form-textarea code="auditor.auditRecord.form.label.body" path="body"/>
  	
  	<acme:form-return code="auditor.auditRecord.form.button.return"/>
  	<acme:form-submit test="${command == 'show' and hasAccess == false and status== 'DRAFT'}" code="auditor.auditRecord.form.button.update"
	action="/auditor/auditRecord/update"/>
	<acme:form-submit test="${command == 'create' }" code="auditor.auditRecord.form.button.create"
	action="/auditor/auditRecord/create"/>
	<acme:form-submit test="${command == 'update' }" code="auditor.auditRecord.form.button.update"
	action="/auditor/auditRecord/update"/>
</acme:form>