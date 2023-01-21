package com.library.database.demo;

import com.library.database.demo.entity.User;
import com.library.database.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;

@SpringBootApplication
@ComponentScan(basePackages = "com.library.database.demo")
public class LibraryApplication {
  @Autowired
  private UserRepository userRepository;

  @PostConstruct
  public void initUsers() {
    List<User> users = Stream.of(
        new User(101, "some_username", "some_password", "username.lastname@gmail.com")
    ).collect(Collectors.toList());
    userRepository.saveAll(users);
  }

  public static void main(String[] args) {
    SpringApplication.run(LibraryApplication.class, args);
  }
}
