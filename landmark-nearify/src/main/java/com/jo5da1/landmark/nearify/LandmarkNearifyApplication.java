package com.jo5da1.landmark.nearify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class LandmarkNearifyApplication {

  public static void main(String[] args) {
    SpringApplication.run(LandmarkNearifyApplication.class, args);
  }
}
