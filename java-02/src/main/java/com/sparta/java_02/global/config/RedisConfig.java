package com.sparta.java_02.global.config;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class RedisConfig {

  @Value("${spring.data.redis.host}")
  private String redisHost;

  @Value("${spring.data.redis.port}")
  private int redisPort;

  @Value("${spring.data.redis.password:}")
  private String redisPassword;

  @Bean
  public Jedis jedis() {
    Jedis jedis = new Jedis(redisHost, redisPort);

    if (!ObjectUtils.isEmpty(redisPassword)) {
      jedis.auth(redisPassword);
    }

    return jedis;
  }

}
