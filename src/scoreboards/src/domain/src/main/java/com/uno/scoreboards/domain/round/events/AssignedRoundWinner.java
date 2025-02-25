package com.uno.scoreboards.domain.round.events;

import com.uno.shared.domain.generic.DomainEvent;

public class AssignedRoundWinner extends DomainEvent {
  private String roundWinnerId;
  private Integer extraPoints;

  public AssignedRoundWinner() {
    super(EventsEnum.ASSIGNED_ROUND_WINNER.name());
  }

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

  public void setRoundWinnerId(String roundWinnerId) {
    this.roundWinnerId = roundWinnerId;
  }

  public void setExtraPoints(Integer extraPoints) {
    this.extraPoints = extraPoints;
  }
}
