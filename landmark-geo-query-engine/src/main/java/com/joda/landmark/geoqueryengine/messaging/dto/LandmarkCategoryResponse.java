package com.joda.landmark.geoqueryengine.messaging.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LandmarkCategoryResponse {
  String requestId;
  List<LandmarkCategory> categories;
}
