package com.uno.scoreboards.domain.scoreboard.events;

import com.uno.shared.domain.generic.DomainEvent;


public class CreatedScoreboard extends DomainEvent {
  public CreatedScoreboard() {
    super(EventsEnum.CREATED_SCOREBOARD.name());
  }
}
