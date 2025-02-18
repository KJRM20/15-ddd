package com.uno.scoreboards.domain.round.events;

import com.uno.shared.domain.generic.DomainEvent;

public class FinishedRound extends DomainEvent {

  public FinishedRound(String roundWinnerId, Integer roundWinnerExtraPoints) {
    super(EventsEnum.FINISHED_ROUND.name());
  }

}
