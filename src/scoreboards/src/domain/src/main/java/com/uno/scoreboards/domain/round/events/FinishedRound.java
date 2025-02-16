package com.uno.scoreboards.domain.round.events;

import com.uno.shared.domain.generic.DomainEvent;

public class FinishedRound extends DomainEvent {
  private final String roundId;

  public FinishedRound(String roundId) {
    super(EventsEnum.FINISHED_ROUND.name());
    this.roundId = roundId;
  }

  public String getRoundId() {
    return roundId;
  }
}
