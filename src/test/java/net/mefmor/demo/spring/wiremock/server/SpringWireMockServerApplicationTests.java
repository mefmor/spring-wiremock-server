package net.mefmor.demo.spring.wiremock.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


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
        ResponseEntity<String> response = restTemplate.getForEntity("/endpoint", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody())
                .isEqualTo("No response could be served as there are no stub mappings in this WireMock instance.");
    }

    @Test
    void youCanSetYourOwnResponseToEndpoint() {
        String request = "{ \"request\": { \"url\": \"/endpoint\", \"method\": \"GET\" }, \"response\": { \"status\": 200, \"body\": \"Here it is!\" }}";

        restTemplate.postForLocation("/__admin/mappings/new", request);

        assertThat(restTemplate.getForEntity("/endpoint", String.class).getBody()).isEqualTo("Here it is!");
    }

    @Test
    void findResponsesThatContainsWornInBody() {
        restTemplate.postForLocation("/nevermind", "Hello Boris! How are you?");
        restTemplate.postForLocation("/nevermind", "I'm good and you?");
        restTemplate.postForLocation("/nevermind", "I'm fine too. So, what are you doing?");
        restTemplate.postForLocation("/nevermind", "I drink vodka and play the balalaika.");

        WireMockAnswer responses = restTemplate.postForObject("/__admin/requests/find",
                "{ \"bodyPatterns\": [ { \"contains\": \"and\" } ] }", WireMockAnswer.class);

        assertThat(responses.getRequests().size()).isEqualTo(2);
        assertThat(responses.getRequests().get(0).getBody()).isEqualTo("I'm good and you?");
        assertThat(responses.getRequests().get(1).getBody()).isEqualTo("I drink vodka and play the balalaika.");
    }

    @NoArgsConstructor
    @Data
    public static class WireMockAnswer {
        @JsonProperty("requests")
        private List<Request> requests;

        @NoArgsConstructor
        @Data
        static class Request {
            @JsonProperty("body")
            private String body;
        }
    }
}
