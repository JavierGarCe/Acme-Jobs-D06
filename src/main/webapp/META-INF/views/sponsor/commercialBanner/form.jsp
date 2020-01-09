<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly="false">
	<acme:form-textbox code="sponsor.banner.commercial.form.label.picture" path="picture"/>
	<acme:form-textbox code="sponsor.banner.commercial.form.label.slogan" path="slogan"/>
	<acme:form-url code="sponsor.banner.commercial.form.label.targetUrl" path="targetUrl"/>
	
	<div class="collapse" id="alertCreate">
 	 <div class="alert alert-danger">
 		<acme:message code="error.commercialBanner.notCreditRegistred.create"/>
 		<a href="/acme-jobs/authenticated/sponsor/update" class="alert-link"><acme:message code="sponsor.banner.commercial.form.data"/></a>
	</div>
	</div>
	<div class="collapse" id="alertUpdate">
 	 <div class="alert alert-danger">
 		<acme:message code="error.commercialBanner.notCreditRegistred.update"/>
 		<a href="/acme-jobs/authenticated/sponsor/update" class="alert-link"><acme:message code="sponsor.banner.commercial.form.data"/></a>
	</div>
	</div>
	
  	<acme:form-submit test="${canCreate == 'true'}" code="sponsor.banner.commercial.form.button.create" action="/sponsor/commercial-banner/create"/>
	<acme:form-submit test="${command == 'create' && canCreate == null}" code="sponsor.banner.commercial.form.button.create" action="/sponsor/commercial-banner/create"/>

	<jstl:if test="${canCreate == 'false'}">
 	<button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#alertCreate" aria-expanded="false" aria-controls="collapseExample">
  	<acme:message code="sponsor.banner.commercial.form.button.create"/>
  	</button>
  	</jstl:if>
  	
  	<acme:form-submit test="${canUpdate == 'true'}" code="sponsor.banner.commercial.form.button.update" action="/sponsor/commercial-banner/update"/>
	<acme:form-submit test="${command == 'update' && canUpdate == null}" code="sponsor.banner.commercial.form.button.update" action="/sponsor/commercial-banner/update"/>

	<jstl:if test="${canUpdate == 'false'}">
 	<button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#alertUpdate" aria-expanded="false" aria-controls="collapseExample">
  	<acme:message code="sponsor.banner.commercial.form.button.update"/>
  	</button>
  	</jstl:if>
  	<acme:form-submit test="${command == 'show'}" code="sponsor.banner.commercial.form.button.delete" action="/sponsor/commercial-banner/delete"/> 
  	<acme:form-return code="sponsor.banner.commercial.form.button.return"/>
</acme:form>