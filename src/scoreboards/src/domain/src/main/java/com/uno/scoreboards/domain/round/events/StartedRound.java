package com.uno.scoreboards.domain.round.events;

import com.uno.shared.domain.generic.DomainEvent;

public class StartedRound extends DomainEvent {
  public StartedRound() {
    super(EventsEnum.STARTED_ROUND.name());
  }
}
