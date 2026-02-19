package com.joda.landmark.geoqueryengine.messaging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;

import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

class LandmarkResultPublisherTest {

  private RabbitTemplate rabbitTemplate;
  private LandmarkResponsePublisher publisher;
  private final String queueName = "landmark.response.queue";

  @BeforeEach
  void setUp() {
    rabbitTemplate = mock(RabbitTemplate.class);
    publisher = new LandmarkResponsePublisher(rabbitTemplate, queueName);
  }

  @Test
  void testSendToLandmarkResponseQueue() {
    // Arrange
    LandmarksResponse response = new LandmarksResponse("requestid", 1, null);

    // Act
    publisher.sendToLandmarkResponseQueue(response);

    // Assert
    ArgumentCaptor<LandmarksResponse> captor = ArgumentCaptor.forClass(LandmarksResponse.class);
    verify(rabbitTemplate, times(1)).convertAndSend(eq(queueName), captor.capture());

    LandmarksResponse sentMessage = captor.getValue();
    assertEquals(response, sentMessage);
  }

  @Test
  void testSendToLandmarkResponseQueue_exceptionHandled() {
    // Arrange
    LandmarksResponse response = new LandmarksResponse("requestId", 1, null);
    doThrow(new RuntimeException("MQ error"))
        .when(rabbitTemplate)
        .convertAndSend(anyString(), (Object) any());

    // Act
    publisher.sendToLandmarkResponseQueue(response);

    // Assert
    // Verify that convertAndSend was called
    verify(rabbitTemplate, times(1)).convertAndSend(eq(queueName), eq(response));
    // No exception should propagate, handled inside publisher
  }
}
