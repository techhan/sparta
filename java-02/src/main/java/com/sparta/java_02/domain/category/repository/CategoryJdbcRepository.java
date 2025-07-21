package com.sparta.java_02.domain.category.repository;

import com.sparta.java_02.domain.category.entity.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.internal.jdbc.JdbcUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryJdbcRepository {

  private final DataSource dataSource;

  public Category save(Category category) throws SQLException {
    String query = "INSERT INTO category (name) VALUES (?)";

    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = dataSource.getConnection();
      preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, category.getName());
      preparedStatement.executeUpdate();

      return category;
    } catch (SQLException error) {
      log.error(error.getMessage(), error);
      throw error;
    } finally {
      close(connection, preparedStatement, null);
    }
  }

  public Category findById(Connection connection, Long categoryId) throws SQLException {
    String query = "SELECT * FROM category WHERE id = ?";

    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;

    try {
      preparedStatement = connection.prepareStatement(query);
      preparedStatement.setLong(1, categoryId);
      resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        return Category.builder()
            .name(resultSet.getString("name"))
            .build();
      } else {
        throw new NoSuchElementException("Category with id " + categoryId + " does not exist");
      }

    } catch (SQLException error) {
      log.error(error.getMessage(), error);
      throw error;
    } finally {
      close(connection, preparedStatement, resultSet);
    }
  }

  public void update(Connection connection, Long categoryId, String name) throws SQLException {
    String query = "UPDATE category SET name = ? WHERE id = ?";
    PreparedStatement preparedStatement = null;
    try {
      preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, name);
      preparedStatement.setLong(2, categoryId);
      preparedStatement.executeUpdate();


    } catch (SQLException error) {
      log.error(error.getMessage(), error);
      throw error;
    } finally {
      close(null, preparedStatement, null);
    }
  }

  private void close(Connection connection, PreparedStatement preparedStatement,
      ResultSet resultSet) {
    JdbcUtils.closeResultSet(resultSet);
    JdbcUtils.closeStatement(preparedStatement);
    JdbcUtils.closeConnection(connection);
  }

  private Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

}
