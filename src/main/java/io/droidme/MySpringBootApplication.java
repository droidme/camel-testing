package io.droidme;

import io.droidme.cameltesting.hello.HelloBeanTimerRoute;
import org.apache.camel.CamelContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class MySpringBootApplication {

    /**
     * A main method to start this application.
     */
    public static void main(String... args) {
        SpringApplication.run(MySpringBootApplication.class, args);
    }

}
