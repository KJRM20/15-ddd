package com.uno.scoreboards.domain.scoreboard.events;

import com.uno.shared.domain.generic.DomainEvent;

public class UpdatedPlayerPoints extends DomainEvent {
  private final String playerId;
  private final Integer points;

  public UpdatedPlayerPoints(String playerId, Integer points) {
    super(EventsEnum.UPDATED_PLAYER_POINTS.name());
    this.playerId = playerId;
    this.points = points;
  }

  public String getPlayerId() {
    return playerId;
  }

  public Integer getPoints() {
    return points;
  }
}
