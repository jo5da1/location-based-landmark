package com.joda.discover.nearby.service;

import com.joda.discover.nearby.persistence.City;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class CityService {

  private final DynamoDbClient dynamoDbClient;

  private static final String tableName = "discover_city";

  public void save(String city) {

    City dbCity = get(city);
    List<City> cities = getAll();

    String id = "1";
    if (dbCity != null) {
      id = dbCity.id();
    } else if (cities.size() > 0) {
      id = String.valueOf(cities.size() + 1);
    }

    log.info("DiscoverNearBy: Saving City: id: {}, name: {}", id, city);

    Map<String, AttributeValue> item =
        Map.of(
            "id", AttributeValue.builder().s(id).build(),
            "name", AttributeValue.builder().s(city).build());

    PutItemRequest request = PutItemRequest.builder().tableName(tableName).item(item).build();

    dynamoDbClient.putItem(request);
  }

  public City get1(String name) {

    Map<String, AttributeValue> key = Map.of("name", AttributeValue.builder().s(name).build());

    GetItemRequest request = GetItemRequest.builder().tableName(tableName).key(key).build();

    GetItemResponse response = dynamoDbClient.getItem(request);

    if (!response.hasItem()) {
      return null;
    }

    return new City(response.item().get("id").s(), response.item().get("name").s());
  }

  public City get(String name) {

    ScanRequest request =
        ScanRequest.builder()
            .tableName(tableName)
            .filterExpression("#n = :name")
            .expressionAttributeNames(Map.of("#n", "name"))
            .expressionAttributeValues(Map.of(":name", AttributeValue.builder().s(name).build()))
            .build();

    ScanResponse response = dynamoDbClient.scan(request);

    if (response.count() == 0) {
      return null;
    }

    var item = response.items().get(0);

    return new City(item.get("id").s(), item.get("name").s());
  }

  public List<City> getAll() {

    ScanRequest request = ScanRequest.builder().tableName(tableName).build();

    ScanResponse response = dynamoDbClient.scan(request);

    return response.items().stream()
        .map(item -> new City(item.get("id").s(), item.get("name").s()))
        .toList();
  }
}
