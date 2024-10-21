package io.droidme.cameltesting.adhoc;

import org.apache.camel.*;
import org.apache.camel.builder.BuilderSupport;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

@SpringBootTest
@CamelSpringBootTest
@MockEndpoints("stream:out")
public class AdhocRouteTest {

    @Autowired
    ProducerTemplate template;

    @EndpointInject("mock:stream:out")
    MockEndpoint mock;

    @Configuration
    static class TestConfig {
        @Bean
        RoutesBuilder route () {
            return new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("direct:start")
                            .routeId("AdHocRoute")
                            .log(LoggingLevel.INFO, "Start Route ${routeId}")
                            .to("stream:out");
                }
            };
        }
    }

    @Test
    void AdHocRouteTest() throws InterruptedException {

        // expectations
        mock.setExpectedMessageCount(1);
        template.sendBody("direct:start", "Hello Adhoc");
        mock.assertIsSatisfied();

    }



}
