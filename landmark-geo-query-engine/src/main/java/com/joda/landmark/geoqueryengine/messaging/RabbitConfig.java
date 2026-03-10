package com.joda.landmark.geoqueryengine.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

@Configuration
@EnableConfigurationProperties(RabbitQueueProperties.class)
public class RabbitConfig {

  @Bean
  public Queue landmarkRequest(RabbitQueueProperties props) {
    return new Queue(props.landmarkRequest(), true);
  }

  @Bean
  public Queue landmarkResponseQueue(RabbitQueueProperties props) {
    return new Queue(props.landmarkResponse(), true);
  }

  @Bean
  public Queue categoryRequestQueue(RabbitQueueProperties props) {
    return new Queue(props.categoryRequest(), true);
  }

  @Bean
  public Queue categoryResponseQueue(RabbitQueueProperties props) {
    return new Queue(props.categoryResponse(), true);
  }

  @Bean
  public JacksonJsonMessageConverter jacksonJsonMessageConverter(ObjectMapper objectMapper) {
    return new JacksonJsonMessageConverter((JsonMapper) objectMapper);
  }

  @Bean
  public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
    SimpleMessageListenerContainer container =
        new SimpleMessageListenerContainer(connectionFactory);
    container.setDefaultRequeueRejected(false);
    return container;
  }
}
