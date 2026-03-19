package com.joda.discover.nearby.config;

import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;

@Configuration
@Slf4j
@EnableConfigurationProperties({AwsProperties.class, DynamoDbProperties.class})
public class AwsConfig {

  @Bean
  public DynamoDbClient dynamoDbClient(AwsProperties awsProps, DynamoDbProperties dynamoProps) {

    log.info(
        "DiscoverNearBy: DYNAMODB_ENDPOINT: {}, AWS_ACCESS_KEY_ID: {} ",
        dynamoProps.endpoint(),
        awsProps.accessKeyIdOrDefault());

    DynamoDbClientBuilder builder =
        DynamoDbClient.builder()
            .region(Region.US_EAST_1)
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(
                        awsProps.accessKeyIdOrDefault(), awsProps.secretAccessKeyOrDefault())));

    if (dynamoProps.endpoint() != null && !dynamoProps.endpoint().isBlank()) {
      log.warn("DiscoverNearBy: Using custom DynamoDB endpoint: {}", dynamoProps.endpoint());
      builder.endpointOverride(URI.create(dynamoProps.endpoint()));
    } else {
      log.info("DiscoverNearBy: Using AWS default endpoint for DynamoDB");
    }

    return builder.build();
  }
}
