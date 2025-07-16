package com.sparta.java_02.domain.user.service;

import com.sparta.java_02.common.exception.ServiceException;
import com.sparta.java_02.common.exception.ServiceExceptionCode;
import com.sparta.java_02.domain.user.dto.UserCreateRequest;
import com.sparta.java_02.domain.user.dto.UserResponse;
import com.sparta.java_02.domain.user.dto.UserSearchResponse;
import com.sparta.java_02.domain.user.dto.UserUpdateRequest;
import com.sparta.java_02.domain.user.entity.User;
import com.sparta.java_02.domain.user.mapper.UserMapper;
import com.sparta.java_02.domain.user.repository.UserQueryRepository;
import com.sparta.java_02.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserMapper userMapper;

  private final EntityManager entityManager;
  private final UserRepository userRepository;
  private final UserQueryRepository userQueryRepository;
  private final JdbcTemplate jdbcTemplate;

  @Transactional
  public Page<UserSearchResponse> searchUser() {
    return null;
  }

  @Transactional(readOnly = true)
  public UserResponse getUserById(Long userId) {
    return null;
  }

  @Transactional
  public void create(UserCreateRequest request) {
    userRepository.save(userMapper.toEntity(request));

  } // 끝나면서 DB 퀄리 날림 & 준영속


  @Transactional
  public void update(Long userId, UserUpdateRequest request) {
    User user = getUser(userId);

    user.setName(request.getName());
    user.setEmail(request.getEmail());

    userRepository.save(user);
  }

  @Transactional
  public void delete(Long userId) {
    userRepository.delete(getUser(userId));
  }

  private User getUser(Long userId) {
    return userRepository.findById(userId)
            .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_USER));
  }

  @Transactional
  public void saveAllUsers(List<User> users) {
//    int batchSize = 1000;
//
//    for(int i = 0; i < users.size(); i++) {
//      User user = users.get(i);
//      entityManager.persist(user);
//
//      // flush()
//      if((i + 1) % batchSize == 0) {
//        entityManager.flush();
//        entityManager.clear();
//      }
//    }
//
//    entityManager.flush();
//    entityManager.clear();
    userRepository.saveAll(users);
  }

  @Transactional
  public void saveAllUser(List<User> users) {
    String sql = "INSERT INTO user(name, email, password_hash) VALUES (?, ?, ?)";

    jdbcTemplate.batchUpdate(sql, users, 1000, (ps, user) -> {
      ps.setString(1, user.getName());
      ps.setString(2, user.getEmail());
      ps.setString(3, user.getPasswordHash());
    });
  }

  @Transactional
  public void bulkInsertUsers(List<User> users) {
    String sql = "INSERT INTO user (name, email, password_hash) VALUES (?, ?, ?)";

    jdbcTemplate.batchUpdate(sql, users, 1000, (ps, user) -> {
      ps.setString(1, user.getName());
      ps.setString(2, user.getEmail());
      ps.setString(3, user.getPasswordHash());
    });
  }
}