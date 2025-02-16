package com.uno.scoreboards.domain.round.events;

import com.uno.shared.domain.generic.DomainEvent;

public class AssignedRoundWinner extends DomainEvent {
  private final String winnerId;
  private final Integer extraPoints;

  public AssignedRoundWinner(String winnerId, Integer extraPoints) {
    super(EventsEnum.ASSIGNED_ROUND_WINNER.name());
    this.winnerId = winnerId;
    this.extraPoints = extraPoints;
  }

  public String getWinner() {
    return winnerId;
  }

  public Integer getExtraPoints() {
    return extraPoints;
  }
}
