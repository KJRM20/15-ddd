package com.uno.scoreboards.domain.round.events;

import com.uno.shared.domain.generic.DomainEvent;

public class AppliedStrike extends DomainEvent {
  private final String playerId;
  private final String details;
  private final Integer reducedPoints;

  public AppliedStrike(String player, String details, Integer reducedPoints) {
    super(EventsEnum.APPLIED_STRIKE.name());
    this.playerId = player;
    this.details = details;
    this.reducedPoints = reducedPoints;
  }

  public String getPlayerId() {
    return playerId;
  }

  public String getDetails() {
    return details;
  }

  public Integer getReducedPoints() {
    return reducedPoints;
  }
}
