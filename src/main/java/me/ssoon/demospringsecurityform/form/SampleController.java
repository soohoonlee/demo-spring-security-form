package me.ssoon.demospringsecurityform.form;

import java.security.Principal;
import java.util.concurrent.Callable;
import me.ssoon.demospringsecurityform.account.Account;
import me.ssoon.demospringsecurityform.book.BookRepository;
import me.ssoon.demospringsecurityform.common.CurrentUser;
import me.ssoon.demospringsecurityform.common.SecurityLogger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

  private final SampleService sampleService;

  private final BookRepository bookRepository;

  public SampleController(SampleService sampleService, BookRepository bookRepository) {
    this.sampleService = sampleService;
    this.bookRepository = bookRepository;
  }

  @GetMapping("/")
  public String index(Model model, @CurrentUser Account account) {
    if (account == null) {
      model.addAttribute("message", "Hello Spring Security");
    } else {
      model.addAttribute("message", "Hello, " + account.getUsername());
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
    model.addAttribute("books", bookRepository.findCurrentUserBooks());
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

  @GetMapping("/async-service")
  @ResponseBody
  public String asyncService() {
    SecurityLogger.log("MVC, before async service");
    sampleService.asyncService();
    SecurityLogger.log("MVC, after async service");
    return "Async Service";
  }
}
