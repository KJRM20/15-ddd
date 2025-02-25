package com.uno.scoreboards.domain.scoreboard.events;

import com.uno.shared.domain.generic.DomainEvent;


public class RevertedHistoryToRound extends DomainEvent {
  private String roundId;

  public RevertedHistoryToRound() {
    super(EventsEnum.REVERTED_HISTORY_TO_ROUND.name());
  }

  public RevertedHistoryToRound(String roundId) {
    super(EventsEnum.REVERTED_HISTORY_TO_ROUND.name());
    this.roundId = roundId;
  }

  public String getRoundId() {
    return roundId;
  }

  public void setRoundId(String roundId) {
    this.roundId = roundId;
  }
}
