package com.sparta.java_02;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

@SpringBootTest
public class RedisDataTest {

  private static final Logger log = LoggerFactory.getLogger(redisTest.class);

  @Autowired
  private Jedis jedis;

  @Test
  void redisStringExample() {
    // api : /api/users/34
    jedis.set("users:55:session", "{'id':34, 'name':'홍길동'}");
    jedis.expire("users:55:session", 3600);
    String response = jedis.get("users:55:session");
    log.info("/api/users/55 요청에 따른 캐싱 된 응답 값 : {}", response);

    Long ttl = jedis.ttl("users:55:session");
    log.info("ttl : {}", ttl);

    jedis.set("article:101:views", "0");
    jedis.incr("article:101:views");
    jedis.incrBy("article:101:views", 10);

    jedis.decr("article:101:views");

    log.info("article:101:views : {}", jedis.get("article:101:views"));
  }

  @Test
  void redisListExample() {

    jedis.del("queue:tasks");

    jedis.lpush("queue:tasks", "task1", "task2", "task3", "task4", "task5");
    Long queueSize = jedis.llen("queue:tasks");
    log.info("queueSize : {}", queueSize);

    String L_task = jedis.lpop("queue:tasks");
    log.info("L_Task : {}", L_task);

    String R_task = String.valueOf(jedis.rpop("queue:tasks"));
    log.info("R_task : {}", R_task);
  }

  void redisSetExample() {
    jedis.sadd("set2", "task1", "task2", "task4");
    jedis.sadd("set2", "task1", "teak2", "task3");

    Set<String> sinterSet = jedis.sinter("set1", "set2");
    log.info("sinterSet : {}", sinterSet);

    Set<String> sunionSet = jedis.sinter("set1", "set2");
    log.info("sunion : {}", sunionSet);
  }

  @Test
  void test() {
    jedis.hset("user:123", "name", "John Doe");
    jedis.hset("user:123", "email", "john.doe@example.com");
    jedis.hset("user:123", "age", "30");
    jedis.hset("user:123", "city", "Seoul");

    String name = jedis.hget("user:123", "name");

    Map<String, String> hget = jedis.hgetAll("user:123");

    log.info("java map : {}, redis : {}", hget.get("name"));

  }

  @Test
  void redisSortedSetExample() {
    jedis.zadd("user123:friendly", 100, "friend1");
    jedis.zadd("user123:friendly", 200, "friend2");
    jedis.zadd("user123:friendly", 300, "friend3");
    jedis.zadd("user123:friendly", 400, "friend4");

    List<String> friends = jedis.zrange("user:123:friendly", 0, 3); // 내림차순
    List<String> zrevrangeFriends = jedis.zrevrange("user:123:friendly", 0, 3); // 오름차순
    log.info("friends : {}, {}", friends, zrevrangeFriends);

    Double score = jedis.zscore("user:123:friendly", "friend1");
    log.info("score : {}", score);

    jedis.zincrby("user:123:friendly", 120, "friend1"); // 데이터 베이스에 기준 값 업데이트 시점

    List<String> zrevragedFriends1 = jedis.zrange("user:123:friendly", 0, 3); // 내림차순
    List<String> zrangeFriends1 = jedis.zrevrange("user:123:friendly", 0, 3); // 오름차순
    log.info("friends : {}, {}", zrevragedFriends1, zrangeFriends1);
  }

}
