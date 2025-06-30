package com.sparta.java_02.domain.user.repository;

import com.sparta.java_02.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  Page<User> findAllByNameContaining(String name, Pageable pageable);

  List<User> findByCreatedAtAfterOrderByNameAsc(LocalDateTime dateTime);

  long countByName(String name);

  @Query("SELECT u FROM User u WHERE u.email = :email")
  Optional<User> findUserByEmail(@Param("email") String email);

  @Query("SELECT u FROM User u JOIN FETCH u.purchases")
  List<User> findAllWithPurchases();

}
