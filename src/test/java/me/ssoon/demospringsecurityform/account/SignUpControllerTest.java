package me.ssoon.demospringsecurityform.account;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SignUpControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  void signUpForm() throws Exception {
    mockMvc.perform(get("/sign-up"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("_csrf")));
  }

  @Test
  void processSignUp() throws Exception {
    mockMvc.perform(post("/sign-up")
          .param("username", "soohoon")
          .param("password", "123")
          .with(csrf()))
        .andDo(print())
        .andExpect(status().is3xxRedirection());
  }
}