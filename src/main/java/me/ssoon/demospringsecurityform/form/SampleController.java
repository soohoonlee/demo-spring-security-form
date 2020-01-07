package me.ssoon.demospringsecurityform.form;

import java.security.Principal;
import java.util.concurrent.Callable;
import me.ssoon.demospringsecurityform.common.SecurityLogger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

  private final SampleService sampleService;

  public SampleController(SampleService sampleService) {
    this.sampleService = sampleService;
  }

  @GetMapping("/")
  public String index(Model model, Principal principal) {
    if (principal == null) {
      model.addAttribute("message", "Hello Spring Security");
    } else {
      model.addAttribute("message", "Hello, " + principal.getName());
    }
    return "index";
  }

  @GetMapping("/info")
  public String info(Model model) {
    model.addAttribute("message", "Info");
    return "info";
  }

  @GetMapping("/dashboard")
  public String dashboard(Model model, Principal principal) {
    model.addAttribute("message", "Hello " + principal.getName());
    sampleService.dashboard();
    return "dashboard";
  }

  @GetMapping("/admin")
  public String admin(Model model, Principal principal) {
    model.addAttribute("message", "Hello Admin, " + principal.getName());
    return "admin";
  }

  @GetMapping("/user")
  public String user(Model model, Principal principal) {
    model.addAttribute("message", "Hello User, " + principal.getName());
    return "user";
  }

  @GetMapping("/async-handler")
  @ResponseBody
  public Callable<String> asyncHandler() {
    SecurityLogger.log("MVC");
    return () -> {
      SecurityLogger.log("Callable");
      return "Async Handler";
    };
  }
}
