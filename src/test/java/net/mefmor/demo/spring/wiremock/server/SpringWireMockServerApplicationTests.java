package net.mefmor.demo.spring.wiremock.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SpringWireMockServerApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void resetDefaultParameters() {
        restTemplate.postForLocation("/__admin/mappings/reset", "");
    }

    @Test
    void byDefaultThereIsNoResponse() {
        assertThat(restTemplate.getForEntity("/endpoint", String.class).getBody(),
                equalTo("No response could be served as there are no stub mappings in this WireMock instance."));
    }

    @Test
    void youCanSetYourOwnResponseToEndpoint() {
        String request = "{ \"request\": { \"url\": \"/endpoint\", \"method\": \"GET\" }, \"response\": { \"status\": 200, \"body\": \"Here it is!\" }}";

        restTemplate.postForLocation("/__admin/mappings/new", request);

        assertThat(restTemplate.getForEntity("/endpoint", String.class).getBody(),
                equalTo("Here it is!"));
    }
}
