package com.uno.scoreboards.application.checkgamestatus;

import com.uno.shared.application.Request;

public class CheckGameStatusRequest extends Request {
  private final Integer targetScore;

  public CheckGameStatusRequest(String aggregateId, Integer targetScore) {
    super(aggregateId);
    this.targetScore = targetScore;
  }

  public Integer getTargetScore() {
    return targetScore;
  }
}
