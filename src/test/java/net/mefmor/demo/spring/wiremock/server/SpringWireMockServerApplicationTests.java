package net.mefmor.demo.spring.wiremock.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class SpringWireMockServerApplicationTests {

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @BeforeEach
    void resetDefaultParameters() {
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8080"));
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
