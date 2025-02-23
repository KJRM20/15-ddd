package com.uno.scoreboards.application.updateplayerspoints;

import com.uno.shared.application.Request;

public class UpdatePlayersPointsRequest extends Request {
  public final String roundId;

  protected UpdatePlayersPointsRequest(String aggregateId, String roundId) {
    super(aggregateId);
    this.roundId = roundId;
  }

  public String getRoundId() {
    return roundId;
  }
}
