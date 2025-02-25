package com.uno.scoreboards.domain.round.events;

import com.uno.shared.domain.generic.DomainEvent;

public class RecordedMove extends DomainEvent {
  private String playerId;
  private String type;
  private Integer number;

  public RecordedMove() {
    super(EventsEnum.RECORDED_MOVE.name());
  }

  public RecordedMove(String playerId, String type, Integer number) {
    super(EventsEnum.RECORDED_MOVE.name());
    this.playerId = playerId;
    this.type = type;
    this.number = number;
  }

  public String getPlayerId() {
    return playerId;
  }

  public String getType() {
    return type;
  }

  public Integer getNumber() {
    return number;
  }

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }
}
