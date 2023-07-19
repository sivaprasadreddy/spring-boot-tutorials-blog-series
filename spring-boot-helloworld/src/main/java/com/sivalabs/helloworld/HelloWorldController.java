package com.sivalabs.helloworld;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HelloWorldController {
    private final GreetingService greetingService;

    @GetMapping("/api/hello")
    public GreetingResponse sayHello(@RequestParam(name = "name", required = false) String name) {
        log.info("Say Hello to Name: {}", name);
        String greeting = greetingService.sayHello(name);
        return new GreetingResponse(greeting);
    }
}
