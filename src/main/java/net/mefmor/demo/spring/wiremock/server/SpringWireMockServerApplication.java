package net.mefmor.demo.spring.wiremock.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.contract.wiremock.WireMockConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(WireMockConfiguration.class) // you can use @AutoConfigureWireMock instead, but without unit-tests
public class SpringWireMockServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWireMockServerApplication.class, args);
    }

}
