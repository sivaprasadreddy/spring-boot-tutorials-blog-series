package com.sivalabs.helloworld;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
@Setter
@Getter
public class ApplicationProperties {
    private String greeting = "Hello";
    private String defaultName = "World";
}
