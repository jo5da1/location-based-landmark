package com.joda.landmark.nearby.messaging;

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
    return new Queue("message.queue.landmark-request", true);
  }

  @Bean
  public Queue landmarkResponseQueue() {
    return new Queue("message.queue.landmark-response", true);
  }

  @Bean
  public Queue categoryRequestQueue() {
    return new Queue("message.queue.category-request", true);
  }

  @Bean
  public Queue categoryResponseQueue() {
    return new Queue("message.queue.category-response", true);
  }

  @Bean
  public JacksonJsonMessageConverter jacksonJsonMessageConverter(ObjectMapper objectMapper) {
    return new JacksonJsonMessageConverter((JsonMapper) objectMapper);
  }
}
