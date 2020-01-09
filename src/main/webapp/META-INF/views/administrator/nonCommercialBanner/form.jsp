<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="administrator.banner.nonCommercial.form.label.picture" path="picture"/>
	<acme:form-textbox code="administrator.banner.nonCommercial.form.label.slogan" path="slogan"/>
	<acme:form-url code="administrator.banner.nonCommercial.form.label.targetUrl" path="targetUrl"/>
	<acme:form-textbox code="administrator.banner.nonCommercial.form.label.jingle" path="jingle"/>
	<acme:form-submit test="${command == 'show' }" code="administrator.banner.nonCommercial.form.button.update"
	action="/administrator/non-commercial-banner/update"/>
	<acme:form-submit test="${command == 'show' }" code="administrator.banner.nonCommercial.form.button.delete"
	action="/administrator/non-commercial-banner/delete"/>
	<acme:form-submit test="${command == 'create' }" code="administrator.banner.nonCommercial.form.button.create"
	action="/administrator/non-commercial-banner/create"/>
	<acme:form-submit test="${command == 'update' }" code="administrator.banner.nonCommercial.form.button.update"
	action="/administrator/non-commercial-banner/update"/>
	<acme:form-submit test="${command == 'delete' }" code="administrator.banner.nonCommercial.form.button.delete"
	action="/administrator/non-commercial-banner/delete"/>
  	<acme:form-return code="administrator.banner.nonCommercial.form.button.return"/>
</acme:form>