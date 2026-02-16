package com.jo5da1.landmark.nearby.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

@Configuration
public class RabbitConfig {

  @Bean
  public Queue landmarkRequestQueue() {
    return new Queue("landmark.request.queue", true);
  }

  @Bean
  public Queue landmarkResponseQueue() {
    return new Queue("landmark.response.queue", true);
  }

  @Bean
  public JacksonJsonMessageConverter jacksonJsonMessageConverter(ObjectMapper objectMapper) {
    return new JacksonJsonMessageConverter((JsonMapper) objectMapper);
  }
}
