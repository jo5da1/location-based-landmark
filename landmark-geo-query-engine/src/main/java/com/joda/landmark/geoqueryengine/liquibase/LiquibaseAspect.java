package com.joda.landmark.geoqueryengine.liquibase;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/** Aspect is good. This triggers Liquibase before any repository call. */
@Aspect
@Component
@Slf4j
public class LiquibaseAspect {

  private final LazyLiquibaseRunner runner;

  public LiquibaseAspect(LazyLiquibaseRunner runner) {
    this.runner = runner;
  }

  @Before("execution(* com.joda.landmark.geoqueryengine.persistence.*Repository.*(..))")
  public void beforeRepositoryCall() {
    log.info("LiquibaseAspect: beforeRepositoryCall");

    runner.runIfNeeded();
  }
}
