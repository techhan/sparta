package com.sparta.java_02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableAsync
@EnableRedisHttpSession
public class Java02Application {

  public static void main(String[] args) {
    SpringApplication.run(Java02Application.class, args);
  }

}
