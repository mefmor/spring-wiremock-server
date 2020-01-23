package net.mefmor.demo.spring.wiremock.server;

import com.github.tomakehurst.wiremock.standalone.WireMockServerRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class WireMockServerComponent implements CommandLineRunner {

    @Override
    public void run(String... args) {
        new WireMockServerRunner().run(args);
    }
}
