package com.uno.scoreboards.domain.scoreboard.events;

import com.uno.shared.domain.generic.DomainEvent;

public class ReachedPlayerTargetScore extends DomainEvent {
  private final String playerId;
  private final Integer targetScore;

  public ReachedPlayerTargetScore(String playerId, Integer targetScore) {
    super(EventsEnum.REACHED_PLAYER_TARGET_SCORE.name());
    this.playerId = playerId;
    this.targetScore = targetScore;
  }

  public String getPlayerId() {
    return playerId;
  }

  public Integer getTargetScore() {
    return targetScore;
  }
}
