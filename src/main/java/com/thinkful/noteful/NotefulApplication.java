package com.thinkful.noteful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NotefulApplication {

  public static void main(String[] args) {
    SpringApplication.run(NotefulApplication.class, args);
  }
}
