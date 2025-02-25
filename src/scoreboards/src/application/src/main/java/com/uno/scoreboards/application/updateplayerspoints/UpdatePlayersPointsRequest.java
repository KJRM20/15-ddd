package com.uno.scoreboards.application.updateplayerspoints;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.uno.shared.application.Request;

public class UpdatePlayersPointsRequest extends Request {

  @JsonCreator
  public UpdatePlayersPointsRequest( @JsonProperty("aggregateId")String aggregateId) {
    super(aggregateId);
  }
}
