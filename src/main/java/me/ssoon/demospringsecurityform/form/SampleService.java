package me.ssoon.demospringsecurityform.form;

import javax.annotation.security.RolesAllowed;
import me.ssoon.demospringsecurityform.common.SecurityLogger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

  @Secured("ROLE_USER")
  // @RolesAllowed("ROLE_USER")
  // @PreAuthorize("hasRole('USER')")
  public void dashboard() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    System.out.println("==============");
    System.out.println("userDetails.getUsername() = " + userDetails.getUsername());
  }

  @Async
  public void asyncService() {
    SecurityLogger.log("Async Service");
    System.out.println("Async service is called");
  }
}
