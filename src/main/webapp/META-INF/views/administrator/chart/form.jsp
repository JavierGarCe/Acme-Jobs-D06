<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<div>

	<canvas id="canvas"></canvas>

</div>

<div style="clear: left">
	<canvas id="canvas2"></canvas>
</div>

<div style="clear: left">
	<canvas id="canvas3"></canvas>
</div>


<script type="text/javascript">

	$(document).ready(function(){

		var data = {

			labels : [
				<jstl:forEach var ="i" items="${sectores}" varStatus="status">
					"<jstl:out value='${i}'/>",
				</jstl:forEach>
				
				

				],

			datasets : [

				{

					backgroundColor:"rgba(0, 0, 1, 1)",

					label:"<acme:message code='companiesbysector'/>",

					data :[
						<jstl:forEach var ="i" items="${sectores}">
							<jstl:set var="printValue" value="0"/>
							<jstl:forEach var ="j" items="${companiesBySector}">
								<jstl:if test="${i==j[1]}">
									<jstl:set var="printValue" value="${j[0]}"/>
								</jstl:if>
							</jstl:forEach>
							<jstl:out value="${printValue}"/>,
						</jstl:forEach>
						

				 	]

				},
				
				{

					backgroundColor:"rgba(255, 0, 0, 0.8)",

					label:"<acme:message code='investorsbysector'/>",

					data :[
						<jstl:forEach var ="i" items="${sectores}">
						<jstl:set var="printValue" value="0"/>
						<jstl:forEach var ="j" items="${investorsBySector}">
							<jstl:if test="${i==j[1]}">
								<jstl:set var="printValue" value="${j[0]}"/>
							</jstl:if>
						</jstl:forEach>
						<jstl:out value="${printValue}"/>,
					</jstl:forEach>

				 	]

				}

				

			]

		};
		
		var data2 = {

				labels : [
					<jstl:forEach var ="i" items="${jobsByStatusRatio}" varStatus="status">
					"<jstl:out value='${i[1]}'/>",
					</jstl:forEach>
					
					<jstl:forEach var ="i" items="${applicationsByStatusRatio}" varStatus="status">
					<jstl:choose>
					<jstl:when test="${status.last}">
					"<jstl:out value='${i[1]}'/>"
					</jstl:when>
					<jstl:when test="${!status.last}">
					"<jstl:out value='${i[1]}'/>",
					</jstl:when>
					</jstl:choose>
					</jstl:forEach>
					
					

					],

				datasets : [

					{

						backgroundColor:"rgba(0, 0, 1, 1)",

						label:"<acme:message code='jobsByStatus'/>",

						data :[
							<jstl:forEach var ="i" items="${jobsByStatusRatio}" varStatus="status">
							<jstl:out value='${i[0]}'/>,
							</jstl:forEach>
							<jstl:forEach var ="i" items="${applicationsByStatusRatio}" varStatus="status">
							<jstl:out value='0'/>,
							</jstl:forEach>
							
							

					 	]

					},
					
					{

						backgroundColor:"rgba(255, 0, 0, 0.8)",

						label:"<acme:message code='applicationsByStatus'/>",

						data :[
							<jstl:forEach var ="i" items="${jobsByStatusRatio}" varStatus="status">
							<jstl:out value='0'/>,
							</jstl:forEach>
							<jstl:forEach var ="i" items="${applicationsByStatusRatio}" varStatus="status">
							<jstl:out value='${i[0]}'/>,
							</jstl:forEach>

					 	]

					}

					

				]

			};
		
			var data3 = {

					labels : [
						<jstl:forEach var ="i" items="${dates}">
						"<jstl:out value='${i}'/>",
						</jstl:forEach>		
						

						],

					datasets : [

						{

							borderColor:"rgba(255, 252, 59, 1)",
							
							fill: false,

							label:"<acme:message code='pendingApplicationsPerDay'/>",

							data :[
								<jstl:forEach var ="i" items="${dates}">
								<jstl:set var="printValue" value="0"/>
								<jstl:forEach var = "j" items="${pendingApplicationsLastMonth}">
									<jstl:if test="${i == j[0]}">
										<jstl:set var="printValue" value="${j[1]}"/>
									</jstl:if>
								</jstl:forEach>
								<jstl:out value="${printValue}"/>,
							</jstl:forEach>
												
								

						 	]

						},
						
						{

							borderColor:"rgba(12, 250, 32, 1)",
							
							fill: false,

							label:"<acme:message code='acceptedApplicationsPerDay'/>",

							data :[
								<jstl:forEach var ="i" items="${dates}">
								<jstl:set var="printValue" value="0"/>
								<jstl:forEach var = "j" items="${acceptedApplicationsLastMonth}">
									<jstl:if test="${i == j[0]}">
										<jstl:set var="printValue" value="${j[1]}"/>
									</jstl:if>
								</jstl:forEach>
								<jstl:out value="${printValue}"/>,
							</jstl:forEach>
												
								

						 	]

						},
						
						{

							borderColor:"rgba(12, 242, 250, 1)",
							
							fill: false,

							label:"<acme:message code='rejectedApplicationsPerDay'/>",

							data :[
								<jstl:forEach var ="i" items="${dates}">
									<jstl:set var="printValue" value="0"/>
									<jstl:forEach var = "j" items="${rejectedApplicationsLastMonth}">
										<jstl:if test="${i == j[0]}">
											<jstl:set var="printValue" value="${j[1]}"/>
										</jstl:if>
									</jstl:forEach>
									<jstl:out value="${printValue}"/>,
								</jstl:forEach>
												
								

						 	]

						}
						
									

					]

				};

		var options = {

			scales : {

				yAxes : [

					{

						ticks : {

							suggestedMin : 0.0,

							suggestedMax : 1.0

						}

					}

				]

			},

			legend : {

				display: true

			}

		};

	

		var canvas, context;

		canvas=document.getElementById("canvas");

		context=canvas.getContext("2d");

		new Chart( context, {

			type : "bar",

			data : data,

			options : options

		});
		var canvas2, context2;
		
		canvas2=document.getElementById("canvas2");
		
		context2=canvas2.getContext("2d");
		
		new Chart( context2, {

			type : "bar",

			data : data2,

			options : options

		});
		
		var canvas3, context3;
		
		canvas3=document.getElementById("canvas3");
		
		context3=canvas3.getContext("2d");
		
		new Chart( context3, {

			type : "line",

			data : data3,

			options : options

		});

	});
	

	

</script>