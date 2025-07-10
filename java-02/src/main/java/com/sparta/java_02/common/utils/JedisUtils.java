package com.sparta.java_02.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Slf4j
@Service
@RequiredArgsConstructor
public class JedisUtils {

  private final Jedis jedis;
  private final ObjectMapper objectMapper;

  public <T> void saveObject(String key, T object) {
    try {
      String jsonString = objectMapper.writeValueAsString(object);
      jedis.set(key, jsonString);
    } catch (Exception e) {
      log.error("[RedisService] saveObject {} : {}", key, e.getMessage());
    }
  }

  public <T> void saveObject(String key, T object, int ttlSeconds) {
    try {
      String jsonString = objectMapper.writeValueAsString(object);
      jedis.setex(key, ttlSeconds, jsonString);
    } catch (Exception e) {
      log.error("[RedisService] saveObject {} : {}", key, e.getMessage());
    }
  }

  public void seykeyTtl(String key, int ttlSeconds) {
    try {
      jedis.expire(key, ttlSeconds);
    } catch (Exception e) {
      log.error("[RedisService] seykeyTtl {} : {}", key, e.getMessage());
    }
  }

  public Optional<Long> getKeyTtl(String key) {
    try {
      return Optional.of(jedis.ttl(key));
    } catch (Exception e) {
      log.error("[RedisService] getKeyTtl {} : {}", key, e.getMessage());
    }

    return Optional.empty();
  }


}
