package com.jo5da1.landmark.nearby.rabbit;

import com.jo5da1.landmark.nearby.api.dto.NearByLandmarksResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LandmarkResultListener {

  private final String landmarkResponseQueue;

  public LandmarkResultListener(@Value("${landmark.response.queue}") String landmarkResponseQueue) {
    this.landmarkResponseQueue = landmarkResponseQueue;
  }

  @RabbitListener(queues = "${landmark.response.queue}")
  public void listenLandmarkResponseQueue(NearByLandmarksResponse message) {
    log.info("received landmark response on queue [{}]: {}", landmarkResponseQueue, message);
  }
}
