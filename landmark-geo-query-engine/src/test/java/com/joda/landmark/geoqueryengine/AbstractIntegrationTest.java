package com.joda.landmark.geoqueryengine;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.rabbitmq.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public abstract class AbstractIntegrationTest {

  static final DockerImageName POSTGIS_IMAGE =
      DockerImageName.parse("postgis/postgis:16-3.4-alpine").asCompatibleSubstituteFor("postgres");

  @Container
  static PostgreSQLContainer postgres =
      new PostgreSQLContainer(POSTGIS_IMAGE)
          .withDatabaseName("testDb")
          .withUsername("test")
          .withPassword("test");

  @Container
  static RabbitMQContainer rabbitmq =
      new RabbitMQContainer("rabbitmq:4.2.3-management-alpine")
          .withAdminUser("guest")
          .withAdminPassword("guest");

  @DynamicPropertySource
  static void configure(DynamicPropertyRegistry registry) {

    // Postgres
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);

    // RabbitMQ
    registry.add("spring.rabbitmq.host", rabbitmq::getHost);
    registry.add("spring.rabbitmq.port", rabbitmq::getAmqpPort);
    registry.add("spring.rabbitmq.username", rabbitmq::getAdminUsername);
    registry.add("spring.rabbitmq.password", rabbitmq::getAdminPassword);
  }
}
