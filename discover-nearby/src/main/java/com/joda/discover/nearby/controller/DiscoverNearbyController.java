package com.joda.discover.nearby.controller;

import com.joda.discover.nearby.persistence.City;
import com.joda.discover.nearby.service.CityService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discover-nearby")
@RequiredArgsConstructor
@Slf4j
public class DiscoverNearbyController {

  private final CityService cityService;

  @GetMapping("")
  public String welcome1() {
    log.info("DiscoverNearBy: ENDPOINT:[/discover-nearby][welcome-1]");

    return "Hello Welcome(1) to Discover Nearby !!";
  }

  @GetMapping("/")
  public String welcome() {

    log.info("DiscoverNearBy: ENDPOINT:[/discover-nearby/][welcome]");

    return "Hello Welcome to Discover Nearby !!";
  }

  @PostMapping(
      value = "/city/save",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public String saveCity(@RequestBody String city) {

    log.info("DiscoverNearBy: ENDPOINT:[/discover-nearby/city/save:[{}]]", city);

    cityService.save(city);

    return "City stored in DynamoDB";
  }

  @GetMapping("/city/get/{city}")
  public City getCity(@PathVariable String city) {

    log.info("DiscoverNearBy: ENDPOINT:[/discover-nearby/city/get/[{}]]", city);

    return cityService.get(city);
  }

  @GetMapping(value = "/city/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<City> getCities() {

    log.info("DiscoverNearBy: ENDPOINT:[/discover-nearby/city/getAll]");

    return cityService.getAll();
  }
}
