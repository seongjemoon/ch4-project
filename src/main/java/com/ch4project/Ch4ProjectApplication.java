package com.ch4project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.awspring.cloud.autoconfigure.config.parameterstore.ParameterStoreAutoConfiguration;

@SpringBootApplication(exclude = {
        ParameterStoreAutoConfiguration.class
})
public class Ch4ProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ch4ProjectApplication.class, args);
    }

}