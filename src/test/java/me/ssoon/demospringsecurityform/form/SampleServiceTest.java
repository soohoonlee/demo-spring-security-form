package me.ssoon.demospringsecurityform.form;

import static org.junit.jupiter.api.Assertions.*;

import me.ssoon.demospringsecurityform.account.Account;
import me.ssoon.demospringsecurityform.account.AccountService;
import me.ssoon.demospringsecurityform.account.WithUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
class SampleServiceTest {

  @Autowired
  SampleService sampleService;

  @Autowired
  AccountService accountService;

  @Autowired
  AuthenticationManager authenticationManager;

  @Test
  // @WithMockUser
  void dashboard() {
    Account account = new Account();
    account.setRole("ADMIN");
    account.setUsername("soohoon");
    account.setPassword("123");
    accountService.createNew(account);

    UserDetails userDetails = accountService.loadUserByUsername("soohoon");

    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "123");
    Authentication authentication = authenticationManager.authenticate(token);

    SecurityContextHolder.getContext().setAuthentication(authentication);

    sampleService.dashboard();
  }
}