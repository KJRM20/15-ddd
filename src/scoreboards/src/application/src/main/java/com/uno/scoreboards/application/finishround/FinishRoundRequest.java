package com.uno.scoreboards.application.finishround;

import com.uno.shared.application.Request;

public class FinishRoundRequest extends Request {
  private final String winnerId;
  private final Integer extraPoints;

  protected FinishRoundRequest(String aggregateId, String winnerId, Integer extraPoints) {
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
