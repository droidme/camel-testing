package io.droidme.cameltesting.hello;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.apache.camel.builder.AdviceWith.adviceWith;

@SpringBootTest
@CamelSpringBootTest
@MockEndpoints("stream:out")
public class HelloBeanTimerRouteTest {

    private final CamelContext context;
    private final ProducerTemplate template;

    @EndpointInject("mock:stream:out")
    MockEndpoint mock;

    @Autowired
    public HelloBeanTimerRouteTest(CamelContext context, ProducerTemplate template) {
        this.context = context;
        this.template = template;
    }

    @BeforeEach
    void setup() throws Exception {
        adviceWith(context, HelloBeanTimerRoute.class.getName(), route -> {
            route.replaceFromWith("direct:start");
        });

    }

    @Test
    void test() throws InterruptedException {

        // setting expectations
        mock.expectedMessageCount(1);
        mock.expectedBodiesReceived("Hello World");

        // invoking consumer
        template.sendBody("direct:start", null);

        // asserting mock is satisfied
        mock.assertIsSatisfied();
    }


}
