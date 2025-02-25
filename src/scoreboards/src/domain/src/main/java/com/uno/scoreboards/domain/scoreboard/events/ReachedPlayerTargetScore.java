package com.uno.scoreboards.domain.scoreboard.events;

import com.uno.shared.domain.generic.DomainEvent;

public class ReachedPlayerTargetScore extends DomainEvent {
  private String playerId;
  private Integer targetScore;

  public ReachedPlayerTargetScore() {
    super(EventsEnum.REACHED_PLAYER_TARGET_SCORE.name());
  }

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

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

  public void setTargetScore(Integer targetScore) {
    this.targetScore = targetScore;
  }
}
