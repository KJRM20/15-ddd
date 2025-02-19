package com.uno.scoreboards.domain.round.events;

import com.uno.shared.domain.generic.DomainEvent;

public class AppliedStrike extends DomainEvent {
  private final String playerId;
  private final String details;

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
}
