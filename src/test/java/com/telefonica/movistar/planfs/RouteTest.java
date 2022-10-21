package com.telefonica.movistar.planfs;

import static com.telefonica.movistar.planfs.constant.Examples.*;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import lombok.extern.log4j.Log4j2;


@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Log4j2
public class RouteTest extends CamelTestSupport {

	@Autowired
	private CamelContext context;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setUp() throws Exception {

		context.getRouteDefinition("com.telefonica.movistar.planfs.request.PipelinePairConsultaPlanFS_response").adviceWith(context, new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {

				weaveById("callCreateinteractionfs").replace()
					.to("mock:callCreateinteractionfs")
					.setBody(constant(R2_SUCCESSFUL_RESPONSE));
			}
		});
	}

	@Test
	public void happyPathTest() throws Exception {

		log.debug("TEST:::: Sending example request to the endpoint.");
		HttpHeaders headers = new HttpHeaders();     
		HttpEntity<String> request = new HttpEntity<>(REQUEST_EXAMPLE, headers);

		ResponseEntity<String> result = restTemplate.postForEntity("/app/plan/purchased/phone/5534718454", request, String.class);
		log.info("TEST:::: Response received. \n" + result.getBody());
		assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
	}
}
