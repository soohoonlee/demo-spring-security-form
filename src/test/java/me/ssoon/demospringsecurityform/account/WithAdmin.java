package me.ssoon.demospringsecurityform.account;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithMockUser;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "soohoon", roles = "ADMIN")
public @interface WithAdmin {

}
