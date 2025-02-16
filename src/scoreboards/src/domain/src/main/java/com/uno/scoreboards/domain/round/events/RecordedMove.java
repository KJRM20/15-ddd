package com.uno.scoreboards.domain.round.events;

import com.uno.shared.domain.generic.DomainEvent;

public class RecordedMove extends DomainEvent {
  private final String playerId;
  private final String type;
  private final Integer points; // revisar

  public RecordedMove(String playerId, String type, Integer points) {
    super(EventsEnum.RECORDED_MOVE.name());
    this.playerId = playerId;
    this.type = type;
    this.points = points;
  }

  public String getPlayerId() {
    return playerId;
  }

  public String getType() {
    return type;
  }

  public Integer getPoints() {
    return points;
  }
}
