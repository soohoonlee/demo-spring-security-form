package me.ssoon.demospringsecurityform.form;

import me.ssoon.demospringsecurityform.account.Account;
import me.ssoon.demospringsecurityform.account.AccountContext;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

  public void dashboard() {
    Account account = AccountContext.getAccount();
    System.out.println("==============");
    System.out.println("account = " + account.getUsername());
  }
}
