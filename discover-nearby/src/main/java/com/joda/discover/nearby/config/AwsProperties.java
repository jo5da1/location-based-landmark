package com.joda.discover.nearby.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws")
@Slf4j
public record AwsProperties(String accessKeyId, String secretAccessKey) {
  public String accessKeyIdOrDefault() {
    log.info("DiscoverNearBy: accessKeyId : {}", accessKeyId);
    // return (accessKeyId == null || accessKeyId.isBlank()) ? "test" : accessKeyId;
    return "test";
  }

  public String secretAccessKeyOrDefault() {
    log.info("DiscoverNearBy: secretAccessKey : {}", secretAccessKey);
    // return (secretAccessKey == null || secretAccessKey.isBlank()) ? "test" : secretAccessKey;
    return "test";
  }
}
