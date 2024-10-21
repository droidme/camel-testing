package io.droidme.cameltesting.hello;

import org.springframework.stereotype.Component;

/**
 * A bean that returns a message when you call the {@link #saySomething()} method.
 * <p/>
 * Uses <tt>@Component("myHelloBean")</tt> to register this bean with the name <tt>myBean</tt>
 * that we use in the Camel route to lookup this bean.
 */
@Component("myHelloBean")
public class HelloBean {

    private final HelloBeanConfig config;

    public HelloBean(HelloBeanConfig config) {
        this.config = config;
    }

    public String saySomething() {
        return config.getSay();
    }

}
