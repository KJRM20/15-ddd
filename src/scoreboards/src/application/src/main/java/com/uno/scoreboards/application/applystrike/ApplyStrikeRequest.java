package com.uno.scoreboards.application.applystrike;

import com.uno.shared.application.Request;

public class ApplyStrikeRequest extends Request {
  private final String playerId;
  private final String details;

  public ApplyStrikeRequest(String aggregateId, String playerId, String details) {
    super(aggregateId);
    this.playerId = playerId;
    this.details = details;
  }

  public String getPlayerId() {
    return playerId;
  }

  public String getDetails() {
    return details;
  }
}
