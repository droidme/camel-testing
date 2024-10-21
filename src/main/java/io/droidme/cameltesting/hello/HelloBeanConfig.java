package io.droidme.cameltesting.hello;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "hello.bean")
public class HelloBeanConfig {

    private String say;

    public String getSay() {
        return say;
    }

    public void setSay(String say) {
        this.say = say;
    }
}
