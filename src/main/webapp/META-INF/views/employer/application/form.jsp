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

<acme:form >
	<acme:form-textbox code="employer.application.form.label.reference" path="reference" placeholder="EEEE-JJJJ:WWWW" readonly="true"/>
	<acme:form-moment code="employer.application.form.label.moment" path="moment" readonly="true"/>
	<acme:form-select code="employer.application.form.label.status" path="status">
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
		<acme:form-option code="employer.application.form.label.status.pending" value="PENDING" selected="${pendingSelected}" />
		<acme:form-option code="employer.application.form.label.status.rejected" value="REJECTED" selected="${rejectedSelected}" />
		<acme:form-option code="employer.application.form.label.status.accepted" value="ACCEPTED" selected="${acceptedSelected}" />
	</acme:form-select>
	<acme:form-textarea code="employer.application.form.label.statement" path="statement" readonly="true"/>
	<acme:form-textarea code="employer.application.form.label.skills" path="skills" readonly="true"/>
	<acme:form-textarea code="employer.application.form.label.qualifications" path="qualifications" readonly="true"/>
	<jstl:if test="${status == 'PENDING'||status=='REJECTED'&&justification==''}">
	<acme:form-textarea code="employer.application.form.label.justification" path="justification"/>
	</jstl:if>
	<jstl:if test="${status == 'ACCEPTED'&&justification!=''||status == 'REJECTED'&&justification!=''}">
	<acme:form-textarea code="employer.application.form.label.justification" path="justification" readonly="true"/>
	</jstl:if>
	
	<jstl:if test="${command == 'show'}">
	<button type="button" class="btn btn-primary" 
		onclick="javascript: pushReturnUrl('/employer/application/show?id=${id}'); redirect('/employer/job/show?id=${idJob}')">
		<acme:message code="employer.application.form.button.job" />
	</button>
	</jstl:if>
	<acme:form-submit test="${status == 'PENDING'||status=='REJECTED'&&justification==''}" code="employer.application.form.label.update" action="/employer/application/update"/>

	<acme:form-return code="employer.application.form.button.return" />

</acme:form>