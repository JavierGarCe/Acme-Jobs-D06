<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<acme:list>	
	<acme:list-column code="anonymous.companyRecord.list.label.name" path="name" width="5%"/>
	<acme:list-column code="anonymous.companyRecord.list.label.sector" path="sector" width="10%"/>
	<acme:list-column code="anonymous.companyRecord.list.label.activities" path="activities" width="30%"/>
</acme:list>