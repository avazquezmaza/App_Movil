package com.telefonica.movistar.planfs.routes;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;


@Component
public class RequestRouteBuilder extends RouteBuilder {	

	private static final String API_VERSION = "1.0.0";
    private static final String API_TITLE = "Redis connector REST API";
    private static final String API_COMPONENT = "servlet";
    private static final String DOC_CONTEXT_PATH = "/api-docs";
    private static final String PROP_TITLE = "api.title";
    private static final String PROP_VERSION = "api.version";
    private static final String CONTEXT_ROUTE_ID = "doc-api";
	
	@Override
	public void configure() throws Exception {
		
        onException(Exception.class)
	        .handled(true);

		
		restConfiguration()
            .enableCORS(true)
			.corsHeaderProperty("Access-Control-Allow-Methods", "POST")
			.contextPath("/app")
            .apiContextPath(DOC_CONTEXT_PATH)
            .apiProperty(PROP_TITLE, API_TITLE)
            .apiProperty(PROP_VERSION, API_VERSION)
            .apiContextRouteId(CONTEXT_ROUTE_ID)
            .component(API_COMPONENT)
            .bindingMode(RestBindingMode.off)
            .dataFormatProperty("prettyPrint","true");

		rest("/plan/purchased")
			.post("/phone/{phoneNumber}")
			.description("REST Service ConsultaPlanFS")
				.consumes(APPLICATION_JSON_VALUE)
				.type(String.class)
	            .description("POST Endpoint 2 Send a Message to ConsultaPlanFS Interface")
	            	.param().name("body").required(true).type(RestParamType.body).endParam()
				.produces(APPLICATION_JSON_VALUE)
				.outType(String.class)
				.responseMessage()
					.code(OK.value())
				.endResponseMessage()
				.to("seda:dispatchRequest");

		/* Routes Configuration */
		from("seda:dispatchRequest").routeId("com.telefonica.movistar.planfs.request.dispatchRequest")
				.noStreamCaching().noMessageHistory().noTracing()
				.choice()
			        .when(simple("${header.phoneNumber} == null || ${header.phoneNumber} == ':phoneNumber'"))
			        	.setHeader("Message", constant("Business Error"))
			        	.setHeader("Code", constant("1052"))
			        	.setHeader("Detail", constant("Los argumentos suministrados son incorrectos"))
						.log(LoggingLevel.INFO, "2222  [${headers}]")
						.to("freemarker:classpath:faultJSONRs.ftl?contentCache=true")
						.log(LoggingLevel.INFO, " [SalidaPayload [${body}]")
					.otherwise()
						.setBody(constant("{\n"
								+ "  \"Response\": {\n"
								+ "    \"Message\": \"Salida\""
								+ "  }\n"
								+ "}"))
					.end()
					.setHeader(Exchange.CONTENT_TYPE, simple("application/json"));

	}
}