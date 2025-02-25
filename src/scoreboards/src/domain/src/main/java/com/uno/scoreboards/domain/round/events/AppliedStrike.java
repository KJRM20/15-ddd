package com.uno.scoreboards.domain.round.events;

import com.uno.shared.domain.generic.DomainEvent;

public class AppliedStrike extends DomainEvent {
  private String playerId;
  private String details;

  public AppliedStrike() {
    super(EventsEnum.APPLIED_STRIKE.name());
  }

  public AppliedStrike(String player, String details) {
    super(EventsEnum.APPLIED_STRIKE.name());
    this.playerId = player;
    this.details = details;
  }

  public String getPlayerId() {
    return playerId;
  }

  public String getDetails() {
    return details;
  }

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

  public void setDetails(String details) {
    this.details = details;
  }
}
