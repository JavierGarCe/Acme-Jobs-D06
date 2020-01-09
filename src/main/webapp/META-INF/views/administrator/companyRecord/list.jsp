<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<acme:list>	
	<acme:list-column code="administrator.companyRecord.list.label.name" path="name" width="20%"/>
	<acme:list-column code="administrator.companyRecord.list.label.sector" path="sector" width="20%"/>
	<acme:list-column code="administrator.companyRecord.list.label.activities" path="activities" width="25%"/>
	<acme:list-column code="administrator.companyRecord.list.label.website" path="website" width="20%"/>
	<acme:list-column code="administrator.companyRecord.list.label.phoneNumber" path="phone" width="10%"/>
	<acme:list-column code="administrator.companyRecord.list.label.stars" path="stars" width="5%"/>
</acme:list>