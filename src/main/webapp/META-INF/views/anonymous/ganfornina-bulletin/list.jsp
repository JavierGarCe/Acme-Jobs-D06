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

<acme:list readonly="true">
	<acme:list-column code="anonymous.ganfornina-bulletin.list.label.company" path="company" width="10%"/>
	<acme:list-column code="anonymous.ganfornina-bulletin.list.label.description" path="description" width="60%"/>
	<acme:list-column code="anonymous.ganfornina-bulletin.list.label.salary" path="salary" width="10%"/>
	<acme:list-column code="anonymous.ganfornina-bulletin.list.label.vacancies" path="vacancies" width="10%"/>
	<acme:list-column code="anonymous.ganfornina-bulletin.list.label.moment" path="moment" width="10%"/>
</acme:list>