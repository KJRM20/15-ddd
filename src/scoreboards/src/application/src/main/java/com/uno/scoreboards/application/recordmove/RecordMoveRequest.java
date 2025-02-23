package com.uno.scoreboards.application.recordmove;

import com.uno.shared.application.Request;

public class RecordMoveRequest extends Request {
  private final String playerId;
  private final String cardType;
  private final Integer number;

  protected RecordMoveRequest(String aggregateId, String playerId, String cardType, Integer number) {
    super(aggregateId);
    this.playerId = playerId;
    this.cardType = cardType;
    this.number = number;
  }

  public String getPlayerId() {
    return playerId;
  }

  public String getCardType() {
    return cardType;
  }

  public Integer getNumber() {
    return number;
  }
}
