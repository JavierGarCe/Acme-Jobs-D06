<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly="true">
	<acme:form-textbox code="authenticated.auditRecord.form.label.title" path="title"/>
	<acme:form-moment code="authenticated.auditRecord.form.label.moment" path="moment"/>
<acme:form-select code="authenticated.auditRecord.form.label.status" path="status">
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
		<acme:form-option code="authenticated.auditRecord.form.label.status.published" value="PUBLISHED" selected="${publishedSelected}" />
		<acme:form-option code="authenticated.auditRecord.form.label.status.draft" value="DRAFT" selected="${draftSelected}" />
	</acme:form-select>	
	<acme:form-textarea code="authenticated.auditRecord.form.label.body" path="body"/>
  	
  	<acme:form-return code="authenticated.auditRecord.form.button.return"/>
</acme:form>