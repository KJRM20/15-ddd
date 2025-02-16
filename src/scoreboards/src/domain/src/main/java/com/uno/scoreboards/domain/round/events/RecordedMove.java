package com.uno.scoreboards.domain.round.events;

import com.uno.shared.domain.generic.DomainEvent;

public class RecordedMove extends DomainEvent {
  private final String playerId;
  private final String type;

  public RecordedMove(String playerId, String type) {
    super(EventsEnum.RECORDED_MOVE.name());
    this.playerId = playerId;
    this.type = type;
  }

  public String getPlayerId() {
    return playerId;
  }

  public String getType() {
    return type;
  }
}
