package com.joda.landmark.geoqueryengine.messaging;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "message.queue")
public record RabbitQueueProperties(
    String landmarkRequest,
    String landmarkResponse,
    String categoryRequest,
    String categoryResponse) {}
