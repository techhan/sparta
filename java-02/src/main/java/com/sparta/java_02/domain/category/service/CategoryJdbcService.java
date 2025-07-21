package com.sparta.java_02.domain.category.service;

import com.sparta.java_02.domain.category.entity.Category;
import com.sparta.java_02.domain.category.repository.CategoryJdbcRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryJdbcService {

  private final DataSource dataSource;
  private final CategoryJdbcRepository categoryJdbcRepository;

  public void updateCategory(Long categoryId, String name) throws SQLException {
    Connection connection = dataSource.getConnection();

    try {
      connection.setAutoCommit(false);

      Category category = categoryJdbcRepository.findById(connection, categoryId);
      if (Objects.nonNull(category)) {
        categoryJdbcRepository.update(connection, categoryId, name);
      }
      categoryJdbcRepository.update(connection, categoryId, name);

      connection.commit();
    } catch (Exception e) {
      connection.rollback();
      throw new RuntimeException(e);
    } finally {
      connection.setAutoCommit(true);
      connection.close();
    }
  }

}
