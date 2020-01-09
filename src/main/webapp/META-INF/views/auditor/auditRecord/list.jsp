<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="auditor.auditRecord.list.label.title" path="title" width="40%"/>
	<acme:list-column code="auditor.auditRecord.list.label.moment" path="moment" width="20%"/>
	<acme:list-column code="auditor.auditRecord.list.label.status" path="status" width="40%"/>
	<acme:form-return code="auditor.auditRecord.form.button.return"/>
</acme:list>
<acme:form-return code="auditor.auditRecord.form.button.return"/>
	