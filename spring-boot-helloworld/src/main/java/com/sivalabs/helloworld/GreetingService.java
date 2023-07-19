package com.sivalabs.helloworld;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GreetingService {
    private final ApplicationProperties properties;

    public String sayHello(String name) {
        String s = name == null ? properties.getDefaultName() : name;
        return String.format("%s %s", properties.getGreeting(), s);
    }
}
