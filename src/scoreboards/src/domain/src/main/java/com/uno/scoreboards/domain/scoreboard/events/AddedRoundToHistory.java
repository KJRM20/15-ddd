package com.uno.scoreboards.domain.scoreboard.events;

import com.uno.shared.domain.generic.DomainEvent;

public class AddedRoundToHistory extends DomainEvent {
  private String roundId;

  public AddedRoundToHistory() {
    super(EventsEnum.ADDED_ROUND_TO_HISTORY.name());
  }

  public AddedRoundToHistory(String roundId) {
    super(EventsEnum.ADDED_ROUND_TO_HISTORY.name());
    this.roundId = roundId;
  }

  public String getRoundId() {
    return roundId;
  }
}
