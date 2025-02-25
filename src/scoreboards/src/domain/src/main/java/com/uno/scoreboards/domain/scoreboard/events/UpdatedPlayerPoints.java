package com.uno.scoreboards.domain.scoreboard.events;

import com.uno.shared.domain.generic.DomainEvent;

public class UpdatedPlayerPoints extends DomainEvent {
  private String playerId;
  private Integer points;

  public UpdatedPlayerPoints() {
    super(EventsEnum.UPDATED_PLAYER_POINTS.name());
  }

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

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

  public void setPoints(Integer points) {
    this.points = points;
  }
}
