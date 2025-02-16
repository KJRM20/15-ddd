package com.uno.scoreboards.domain.round.events;

import com.uno.shared.domain.generic.DomainEvent;

public class AssignedRoundWinner extends DomainEvent {
  private final String roundWinnerId;
  private final Integer extraPoints;

  public AssignedRoundWinner(String roundWinnerId, Integer extraPoints) {
    super(EventsEnum.ASSIGNED_ROUND_WINNER.name());
    this.roundWinnerId = roundWinnerId;
    this.extraPoints = extraPoints;
  }

  public String getRoundWinner() {
    return roundWinnerId;
  }

  public Integer getExtraPoints() {
    return extraPoints;
  }
}
