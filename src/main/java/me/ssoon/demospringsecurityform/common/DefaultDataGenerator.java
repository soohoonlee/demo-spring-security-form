package me.ssoon.demospringsecurityform.common;

import me.ssoon.demospringsecurityform.account.Account;
import me.ssoon.demospringsecurityform.account.AccountService;
import me.ssoon.demospringsecurityform.book.Book;
import me.ssoon.demospringsecurityform.book.BookRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DefaultDataGenerator implements ApplicationRunner {

  private final AccountService accountService;

  private final BookRepository bookRepository;

  public DefaultDataGenerator(AccountService accountService, BookRepository bookRepository) {
    this.accountService = accountService;
    this.bookRepository = bookRepository;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    Account soohoon = createUser("soohoon");
    Account junkyu = createUser("junkyu");
    createBook("spring", soohoon);
    createBook("hibernate", junkyu);
  }

  private void createBook(String title, Account soohoon) {
    Book book = new Book();
    book.setTitle(title);
    book.setAuthor(soohoon);
    bookRepository.save(book);
  }

  private Account createUser(String username) {
    Account account = new Account();
    account.setUsername(username);
    account.setPassword("123");
    account.setRole("USER");
    return accountService.createNew(account);
  }
}
