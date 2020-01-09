<%--
- list.jsp
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
<acme:list>
	<acme:list-column code="employer.duty.list.label.title" path="title" width="40%"/>
	<acme:list-column code="employer.duty.list.label.percentage" path="percentage" width="20%"/>
</acme:list>
<jstl:set var= "a" value="statusDraft[0]"/>
<jstl:set var="status" value="${requestScope[a]}"/>
<jstl:if test="${status == 'true' || status == null}">
 	<button type="button" onclick="javascript: pushReturnUrl('/employer/duty/list?jobId=${param.jobId}'); redirect('/employer/duty/create?jobId=${param.jobId}')" class="btn btn-primary">
		<acme:message code="employer.job.list.label.create.duty" />
	</button> 
</jstl:if>
<!--<acme:form-submit test="${status == 'true'}" code="employer.job.list.label.create.duty" action="/employer/duty/create?jobId=${param.jobId}" method="get"/>-->
<acme:form-return code="employer.job.list.button.return"/>
