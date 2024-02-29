package demo.auth.server.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class IndexControllerTest {
    @Autowired
    MockMvc mockMvc;

    @DisplayName("returnFailIsSuccess")
    @Test
    void index() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @DisplayName("securityApplySuccess")
    @Test
    void test() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/login/oauth2/code/naver"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("success!"));
    }
}