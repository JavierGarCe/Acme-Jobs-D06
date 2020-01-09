<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly="true">
		<acme:form-integer code="administrator.dashboard.form.label.numberOfAnnouncements" path="numberOfAnnoucements"/>
		<acme:form-integer code="administrator.dashboard.form.label.numberOfCompanyRecords" path="numberOfCompanyRecords"/>
		<acme:form-integer code="administrator.dashboard.form.label.numberOfInvestorRecords" path="numberOfInvestorRecords"/>
		<acme:form-double code="administrator.dashboard.form.label.minActiveRequests" path="minActiveRequests"/>
		<acme:form-double code="administrator.dashboard.form.label.maxActiveRequests" path="maxActiveRequests"/>
		<acme:form-double code="administrator.dashboard.form.label.avgActiveRequests" path="avgActiveRequests"/>
		<acme:form-double code="administrator.dashboard.form.label.stDevRActiveRequests" path="stDevRActiveRequests"/>
		<acme:form-double code="administrator.dashboard.form.label.minActiveOffers" path="minActiveOffers"/>
		<acme:form-double code="administrator.dashboard.form.label.maxActiveOffers" path="maxActiveOffers"/>
		<acme:form-double code="administrator.dashboard.form.label.avgActiveOffers" path="avgActiveOffers"/>
		<acme:form-double code="administrator.dashboard.form.label.stDevMaxActiveOffers" path="stDevMaxActiveOffers"/>
		<acme:form-double code="administrator.dashboard.form.label.stDevMinActiveOffers" path="stDevMinActiveOffers"/>
		<acme:form-double code="administrator.dashboard.form.label.avgNumberOfJobsPerEmployer" path="avgNumberOfJobsPerEmployer"/>
		<acme:form-double code="administrator.dashboard.form.label.avgNumberOfApplicationsPerEmployer" path="avgNumberOfApplicationsPerEmployer"/>
		<acme:form-double code="administrator.dashboard.form.label.avgNumberOfApplicationsPerWorker" path="avgNumberOfApplicationsPerWorker"/>
		
	<acme:form-return code="administrator.dashboard.form.button.return"/>
</acme:form>