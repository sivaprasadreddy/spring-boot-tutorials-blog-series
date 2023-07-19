package com.sivalabs.helloworld;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HelloWorldController.class)
class HelloWorldControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GreetingService greetingService;

    @Test
    void shouldReturnGreetingSuccessfully() throws Exception {
        given(greetingService.sayHello("Siva")).willReturn("Hello Siva");

        mockMvc.perform(get("/api/hello?name={name}", "Siva"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.greeting", is("Hello Siva")));
    }
}
