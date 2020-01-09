<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<acme:list readonly="true">	
	<acme:list-column code="anonymous.garciaBulletin.list.label.moment" path="moment" width="15%"/>
	<acme:list-column code="anonymous.garciaBulletin.list.label.name" path="name" width="15%"/>
	<acme:list-column code="anonymous.garciaBulletin.list.label.surname" path="surname" width="15%"/>
	<acme:list-column code="anonymous.garciaBulletin.list.label.subject" path="subject" width="15%"/>
	<acme:list-column code="anonymous.garciaBulletin.list.label.text" path="text" width="40%"/>
</acme:list>