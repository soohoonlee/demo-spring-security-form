package me.ssoon.demospringsecurityform.config;

import me.ssoon.demospringsecurityform.account.AccountService;
import me.ssoon.demospringsecurityform.common.LoggingFilter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final AccountService accountService;

  public SecurityConfig(AccountService accountService) {
    this.accountService = accountService;
  }

  public SecurityExpressionHandler<FilterInvocation> expressionHandler() {
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

    DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
    handler.setRoleHierarchy(roleHierarchy);

    return handler;
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.addFilterBefore(new LoggingFilter(), WebAsyncManagerIntegrationFilter.class);

    http.authorizeRequests()
          .mvcMatchers("/", "/info", "/account/**", "/sign-up").permitAll()
          .mvcMatchers("/admin").hasRole("ADMIN")
          .mvcMatchers("/user").hasRole("USER")
          .anyRequest().authenticated()
          .expressionHandler(expressionHandler())
        .and()
          .formLogin()
            .loginPage("/sign-in")
            .usernameParameter("my-username")
            .passwordParameter("my-password")
            .permitAll()
        .and()
          .exceptionHandling()
            // .accessDeniedPage("/access-denied")
            .accessDeniedHandler((request, response, accessDeniedException) -> {
              UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                  .getAuthentication().getPrincipal();
              String username = principal.getUsername();
              System.out.println(username + " is denied to access " + request.getRequestURI());
              response.sendRedirect("/access-denied");
            })
        .and()
          .rememberMe()
            .userDetailsService(accountService)
            .rememberMeParameter("my-remember-me")
            .key("remember-me-sample")
        .and()
          .httpBasic()
        .and()
          .logout()
            .logoutUrl("/sign-out")
            .logoutSuccessUrl("/")
        .and()
          .sessionManagement()
            .sessionFixation()
              .changeSessionId()
            .maximumSessions(1)
              .maxSessionsPreventsLogin(false);

    SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
