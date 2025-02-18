package com.uno.scoreboards.domain.round.events;

import com.uno.shared.domain.generic.DomainEvent;

public class CalculatedResult extends DomainEvent {
  public CalculatedResult() {
    super(EventsEnum.CALCULATED_RESULT.name());
  }
}
