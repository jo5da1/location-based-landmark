package com.joda.landmark.geoqueryengine.liquibase;

import java.util.concurrent.atomic.AtomicBoolean;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * The core problem ----------------- 1. App starts without DB 2. DB connects later 3. Liquibase
 * runs automatically on first DB usage
 *
 * <p>But by default: Spring Boot → runs Liquibase at startup Liquibase → needs DB connection
 * immediately
 *
 * <p>Solution --------- Lazy Liquibase trigger
 *
 * <p>A controlled workaround:
 * <li>Disable auto Liquibase
 * <li>Trigger on first DB access
 */
@Component
@Slf4j
public class LazyLiquibaseRunner {

  private final DataSource dataSource;
  private final AtomicBoolean initialized = new AtomicBoolean(false);

  public LazyLiquibaseRunner(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void runIfNeeded() {
    log.info("LazyLiquibaseRunner: runIfNeeded");

    if (initialized.compareAndSet(false, true)) {
      try {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:db/changelog/db.changelog-master.xml");
        liquibase.afterPropertiesSet();
      } catch (Exception e) {
        initialized.set(false); // allow retry
        throw new RuntimeException("Liquibase migration failed", e);
      }
    }
  }
}
