package io.droidme.cameltesting.commando;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static org.apache.camel.LoggingLevel.INFO;

@Component
public class CommandRoute extends RouteBuilder {

    final static String ENDPOINT_QUELLE = "{{CommandRouteQuelle}}";

    @Override
    public void configure() throws Exception {

        //@formatter:off
        from(ENDPOINT_QUELLE)
                .routeId(CommandRoute.class.getName())
                .log(INFO, "Route ${routeId} startet.")
                .log(INFO, "command received: ${body}")
                .to("stream:out");
        //@formatter:on


    }
}
