package com.telefonica.movistar.planfs.routes;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_XML_VALUE;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jsonvalidator.JsonValidationException;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

import com.telefonica.movistar.planfs.configurations.JsonXml;
import com.telefonica.movistar.planfs.configurations.Utility;


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
	        .handled(true)
	        .process(new Processor() {
                
                @Override
                public void process(Exchange exchange) throws Exception {
                    // TODO Auto-generated method stub
                    Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                    System.out.println(exception.getClass());
                    System.out.println(exception.getMessage());
                    System.out.println(exception.getCause());
                }
            })
        	.setHeader("Message", constant("Business Error"))
        	.setHeader("Code", constant("4050"))
        	.setHeader("Detail", constant("Error en: ',$fault/ctx:errorCode, ' - ', $fault/ctx:reason/text(), ' - ', /ctx:details/text()"))
			.to("freemarker:classpath:faultJSONRs.ftl?contentCache=true");
	        
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
				.to("direct:PipelinePairConsultaPlanFS_request")
				.to("direct:PipelinePairConsultaPlanFS_response")
				.setHeader(Exchange.CONTENT_TYPE, simple("application/json"));
		
		from("direct:PipelinePairConsultaPlanFS_request").routeId("com.telefonica.movistar.planfs.request.PipelinePairConsultaPlanFS_request")
			.noStreamCaching().noMessageHistory().noTracing()
			.choice()
		        .when(simple("${header.phoneNumber} == null || ${header.phoneNumber} == ':phoneNumber'"))
		        	.to("direct:errorValidation")
			.end()
			.doTry()
				.to("json-validator:classpath:RequestPayloadSchema.json?contentCache=true")
			.doCatch(JsonValidationException.class)
				.to("direct:errorValidation")
			.end();
		
		from("direct:PipelinePairConsultaPlanFS_response").routeId("com.telefonica.movistar.planfs.request.PipelinePairConsultaPlanFS_response")
			.noStreamCaching().noMessageHistory().noTracing()
	         .convertBodyTo(String.class)
			 .setHeader("descIncEureka", jsonpath("$.peticion.descIncEureka"))
	         .setHeader("primaryTelephoneNumber", simple("${headers.phoneNumber}"))
	         .setHeader("contactTypePartyAccountContact", jsonpath("$.peticion.idContacto"))
	         .setHeader("descriptionBusinessInteraction").method(Utility.class, "decodeBase64(${headers.descIncEureka})") 
	         .to("freemarker:classpath:CreateInteractionRequest.ftl?contentCache=true")
	         .setHeader(ACCEPT, constant(TEXT_XML_VALUE))
	         .setHeader(CONTENT_TYPE, constant(TEXT_XML_VALUE))
	         .enrich().simple("{{service.createinteractionfs_bs_business.url}}").id("callCreateinteractionfs")
	         .to("xslt:classpath:RemoveNS.xsl?contentCache=true")
	         .bean(JsonXml.class,"xmltoJSON(${body})");
		
		from("direct:errorValidation").routeId("com.telefonica.movistar.planfs.request.errorValidation")
			.noStreamCaching().noMessageHistory().noTracing()
        	.setHeader("Message", constant("Business Error"))
        	.setHeader("Code", constant("1052"))
        	.setHeader("Detail", constant("Los argumentos suministrados son incorrectos"))
			.to("freemarker:classpath:faultJSONRs.ftl?contentCache=true");

	}
}