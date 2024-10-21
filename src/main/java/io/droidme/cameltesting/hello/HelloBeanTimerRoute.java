package io.droidme.cameltesting.hello;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
//@Component
public class HelloBeanTimerRoute extends RouteBuilder {

    @Override
    public void configure() {

        //@formatter:off

        from("timer:hello?period={{timer.period}}")
            .routeId(HelloBeanTimerRoute.class.getName())
            .transform()
                .method("myHelloBean", "saySomething")
            .filter(simple("${body} contains 'foo'"))
                .to("log:foo")
            .end()
            .to("stream:out");

        //@formatter:on
    }

}
