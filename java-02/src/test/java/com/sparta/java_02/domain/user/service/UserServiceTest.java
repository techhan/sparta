package com.sparta.java_02.domain.user.service;

import com.sparta.java_02.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
class UserServiceTest {

    private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserService userService;

    @Test
    void saveAllUser() {
        // given
        List<User> users = getUsers(2000);

        // when
        userService.saveAllUser(users);

        // then
        List<User> savedUsers = entityManager.createQuery(
                "SELECT u FROM User u ORDER BY u.id", User.class
        ).getResultList();

        assertThat(savedUsers).hasSize(2001);
    }

    @Transactional
    public List<User> getUsers(int count) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            users.add(User.builder()
                    .name("name" + i)
                    .email("email" + i + "@email.com")
                    .passwordHash("hash" + i)
                    .build());
        }
        return users;
    }

}