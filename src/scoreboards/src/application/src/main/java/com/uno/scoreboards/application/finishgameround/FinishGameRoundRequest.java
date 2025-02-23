package com.uno.scoreboards.application.finishgameround;

import com.uno.shared.application.Request;

public class FinishGameRoundRequest extends Request {
  private final String winnerId;
  private final Integer extraPoints;

  public FinishGameRoundRequest(String aggregateId, String winnerId, Integer extraPoints) {
    super(aggregateId);
    this.winnerId = winnerId;
    this.extraPoints = extraPoints;
  }

  public String getWinnerId() {
    return winnerId;
  }

  public Integer getExtraPoints() {
    return extraPoints;
  }
}
