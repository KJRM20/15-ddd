package com.uno.scoreboards.domain.scoreboard.events;

import com.uno.shared.domain.generic.DomainEvent;

public class AddedRoundToHistory extends DomainEvent {
  private final String roundId;
  private final String roundWinnerId;

  public AddedRoundToHistory(String roundId, String roundWinnerId) {
    super(EventsEnum.ADDED_ROUND_TO_HISTORY.name());
    this.roundId = roundId;
    this.roundWinnerId = roundWinnerId;
  }

  public String getRoundId() {
    return roundId;
  }

  public String getRoundWinnerId() {
    return roundWinnerId;
  }
}
