package org.theeric.auth.test;

import org.junit.jupiter.api.Disabled;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Disabled
@Testcontainers
public class AbstractIntegrationTest {

    @Container
    private static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:5.7");

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);

        registry.add("spring.flyway.url", mysql::getJdbcUrl);
        registry.add("spring.flyway.username", mysql::getUsername);
        registry.add("spring.flyway.password", mysql::getPassword);
        registry.add("spring.flyway.schemas", mysql::getDatabaseName);
    }

}
