package com.joda.landmark.geoqueryengine.liquibase;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * background pre-warm approach Instead of triggering Liquibase on first repository call, Trigger
 * Liquibase asynchronously after startup.
 *
 * <p>What this(background pre-warm approach) gives --------------- 1. App starts instantly (DB can
 * be down) 2. Liquibase runs automatically when DB becomes available 3. No delay on first user
 * request 4. Clean separation of concerns
 */
@Component
@Slf4j
public class LiquibaseWarmup {

  private final LazyLiquibaseRunner runner;

  public LiquibaseWarmup(LazyLiquibaseRunner runner) {
    this.runner = runner;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void warmUp() {
    log.info("---> LiquibaseWarmup");
    new Thread(
            () -> {
              while (true) {
                log.info("---> LiquibaseWarmup(retry)");
                try {
                  runner.runIfNeeded();
                  break; // success
                } catch (Exception e) {
                  try {
                    Thread.sleep(5000); // retry every 5s
                  } catch (InterruptedException ignored) {
                  }
                }
              }
            })
        .start();
  }
}
