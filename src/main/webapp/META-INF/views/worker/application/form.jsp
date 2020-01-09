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

<acme:form>
	<input name="jobId" id="jobId" type="hidden" value="${param.jobId}" />
	<acme:form-textbox code="worker.application.form.label.reference" path="reference" placeholder="EEEE-JJJJ:WWWW" />
	<jstl:if test="${command != 'create' }">
		<acme:form-moment code="worker.application.form.label.moment" path="moment" readonly="true" />
	</jstl:if>
	<jstl:if test="${command != 'create' }">
		<acme:form-select code="worker.application.form.label.status" path="status">
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
			<jstl:choose>
				<jstl:when test="${status == 'ACCEPTED' }">
					<jstl:set var="acceptedSelected" value="true" />
				</jstl:when>
				<jstl:otherwise>
					<jstl:set var="acceptedSelected" value="false" />
				</jstl:otherwise>
			</jstl:choose>
			<acme:form-option code="worker.application.form.label.status.pending" value="PENDING" selected="${pendingSelected}" />
			<acme:form-option code="worker.application.form.label.status.rejected" value="REJECTED" selected="${rejectedSelected}" />
			<acme:form-option code="worker.application.form.label.status.accepted" value="ACCEPTED" selected="${acceptedSelected}" />
		</acme:form-select>
	</jstl:if>
	<acme:form-textarea code="worker.application.form.label.statement" path="statement" />
	<acme:form-textarea code="worker.application.form.label.skills" path="skills" />
	<acme:form-textarea code="worker.application.form.label.qualifications" path="qualifications" />
	<jstl:if test="${justification!=null && justification!='' }">
		<acme:form-textarea code="worker.application.form.label.justification" path="justification" />
	</jstl:if>

	<jstl:if test="${command == 'show'}">
		<button type="button" class="btn btn-primary"
			onclick="javascript: pushReturnUrl('/worker/application/show?id=${id}'); redirect('/worker/job/show?id=${idJob}')">
			<acme:message code="employer.application.form.button.job" />
		</button>
	</jstl:if>
	<acme:form-submit test="${command == 'create' }" code="employer.application.form.button.createApplication"
		action="/worker/application/create" />

	<acme:form-return code="worker.application.form.button.return" />

</acme:form>