package com.example.runorn_dadata_demo;

import com.zaxxer.hikari.HikariDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import javax.sql.DataSource;

@Configuration
public class TestContainerConfig {

  @Bean
  public PostgreSQLContainer<?> postgreSQLContainer() {
    PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:13")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test");
    container.start();
    return container;
  }

  @Bean
  public DataSource dataSource(PostgreSQLContainer<?> postgreSQLContainer) {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(postgreSQLContainer.getJdbcUrl());
    dataSource.setUsername(postgreSQLContainer.getUsername());
    dataSource.setPassword(postgreSQLContainer.getPassword());
    return dataSource;
  }
}