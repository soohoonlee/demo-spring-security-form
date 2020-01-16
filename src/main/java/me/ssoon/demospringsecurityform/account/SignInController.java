package me.ssoon.demospringsecurityform.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignInController {

  @GetMapping("/sign-in")
  public String signInForm() {
    return "signin";
  }

  @GetMapping("/sign-out")
  public String signOutForm() {
    return "signout";
  }
}
