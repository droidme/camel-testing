package io.droidme.cameltesting.adhoc;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.apache.camel.LoggingLevel.INFO;

@CamelSpringBootTest
@EnableAutoConfiguration
@SpringBootTest(
        properties = { "camel.springboot.name=Adhoc" }
)
public class AdhocRouteTest {

    @Autowired
    ProducerTemplate template;

    @EndpointInject("mock:endpunkt")
    MockEndpoint mock;

    @Configuration
    static class TestConfig {
        @Bean
        RoutesBuilder route () {
            return new RouteBuilder() {
                @Override
                public void configure() throws Exception {

                    getContext().setTracing(true);

                    from("direct:start")
                            .routeId("AdHocRoute")
                            .log(INFO, "Start Route ${routeId}")
                            .setHeader("MsgType", simple("CDM:WP_TX"))
                            .setBody(simple("Dies ist eine CDM von Olympic vom Type WP_TX"))
                            .choice()
                                .when(simple("${header.MsgType} == 'CDM:WP_TX'"))
                                    .setProperty("originalMessageBody", body())
                                    .to("direct:b")
                                    .setBody(simple("${header.originalMessageBody}"))
                            .end()
                            .log(INFO, "to endpunkt")
                            .to("mock:endpunkt");

                    from("direct:b")
                            .log(INFO, "in direct:b before delay")
                            .setBody(simple("change the body inside sub-route"))
                            .delay(5000)
                            .log(INFO, "in direct:b after delay")
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
