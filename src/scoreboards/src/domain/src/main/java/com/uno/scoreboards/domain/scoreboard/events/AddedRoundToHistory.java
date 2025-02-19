package com.uno.scoreboards.domain.scoreboard.events;

import com.uno.shared.domain.generic.DomainEvent;

public class AddedRoundToHistory extends DomainEvent {
  private final String roundId;

  public AddedRoundToHistory(String roundId) {
    super(EventsEnum.ADDED_ROUND_TO_HISTORY.name());
    this.roundId = roundId;
  }

  public String getRoundId() {
    return roundId;
  }
}
