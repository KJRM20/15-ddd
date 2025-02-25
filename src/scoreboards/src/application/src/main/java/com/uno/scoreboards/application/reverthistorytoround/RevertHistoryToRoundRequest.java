package com.uno.scoreboards.application.reverthistorytoround;

import com.uno.shared.application.Request;

public class RevertHistoryToRoundRequest extends Request {
  private final String roundId;

  public RevertHistoryToRoundRequest(String aggregateId, String roundId) {
    super(aggregateId);
    this.roundId = roundId;
  }

  public String getRoundId() {
    return roundId;
  }
}
