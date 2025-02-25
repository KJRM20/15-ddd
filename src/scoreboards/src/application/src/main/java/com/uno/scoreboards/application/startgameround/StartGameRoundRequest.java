package com.uno.scoreboards.application.startgameround;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.uno.shared.application.Request;

public class StartGameRoundRequest extends Request {
  @JsonCreator
  public StartGameRoundRequest( @JsonProperty("aggregateId")String aggregateId) {
    super(aggregateId);
  }
}
