package com.uno.scoreboards.domain.round.events;

import com.uno.shared.domain.generic.DomainEvent;

public class StartedRound extends DomainEvent {
  private final String roundId;

  public StartedRound(String roundId) {
    super(EventsEnum.STARTED_ROUND.name());
    this.roundId = roundId;
  }

  public String getRoundId() {
    return roundId;
  }
}
