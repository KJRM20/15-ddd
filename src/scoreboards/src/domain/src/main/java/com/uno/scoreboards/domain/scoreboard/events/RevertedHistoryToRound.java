package com.uno.scoreboards.domain.scoreboard.events;

import com.uno.shared.domain.generic.DomainEvent;

public class RevertedHistoryToRound extends DomainEvent {
  private final String roundId;

  public RevertedHistoryToRound(String roundId) {
    super(EventsEnum.REVERTED_HISTORY_TO_ROUND.name());
    this.roundId = roundId;
  }

  public String getRoundId() {
    return roundId;
  }
}
