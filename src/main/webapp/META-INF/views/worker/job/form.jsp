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
	<acme:form-textbox code="worker.job.form.label.title" path="title" />
	<acme:form-textbox code="worker.job.form.label.reference" path="reference" placeholder="EEEE-JJJJ"/>
	<acme:form-select code="worker.job.form.label.status" path="status" >
		<jstl:choose>
			<jstl:when test="${status == 'PUBLISHED' }"> <jstl:set var="publishedSelected" value="true"/> </jstl:when>
			<jstl:otherwise><jstl:set var="publishedSelected" value="false"/></jstl:otherwise>
		</jstl:choose>
		<jstl:choose>
			<jstl:when test="${status == 'DRAFT' }"> <jstl:set var="draftSelected" value="true"/> </jstl:when>
			<jstl:otherwise><jstl:set var="draftSelected" value="false"/></jstl:otherwise>
		</jstl:choose>
		<acme:form-option code="worker.job.form.label.status.published" value="PUBLISHED"  selected="${publishedSelected}" />
		<acme:form-option code="worker.job.form.label.status.draft" value="DRAFT" selected="${draftSelected}" />
	</acme:form-select>
	<acme:form-money code="worker.job.form.label.salary" path="salary" />
	<acme:form-moment code="worker.job.form.label.deadline" path="deadline" />
	<acme:form-url code="worker.job.form.label.moreInfo" path="moreInfo" />
	<acme:form-textbox code="worker.job.form.label.employer.name" path="employer.userAccount.username" />
	<acme:form-panel code="worker.job.form.label.descriptor">
		<acme:form-textarea code="worker.job.form.label.descriptor.description" path="descriptor.description" />
	</acme:form-panel>


	<button type="button" class="btn btn-primary" 
		onclick="javascript: pushReturnUrl('/worker/job/show?id=${id}'); redirect('/worker/audit-record/list-mine?id=${param.id}')">
		<acme:message code="master.menu.worker.listAuditRecords" />
	</button>
	
	<button type="button" class="btn btn-primary" 
		onclick="javascript: pushReturnUrl('/worker/job/show?id=${id}'); redirect('/worker/duty/list?id=${id}')">
		<acme:message code="worker.job.form.label.descriptorMessage" />
	</button>
	
	<jstl:if test="${canApply == true}">
	<button type="button" class="btn btn-primary" 
		onclick="javascript: pushReturnUrl('/worker/job/show?id=${id}'); redirect('/worker/application/create?jobId=${id}')">
		<acme:message code="employer.job.form.button.createApplication" />
	</button>
	</jstl:if>
	
	<acme:form-return code="worker.job.form.button.return"/>



</acme:form>