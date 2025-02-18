package com.uno.scoreboards.domain.scoreboard.events;

import com.uno.shared.domain.generic.DomainEvent;

public class ScoreboardLocked extends DomainEvent {
  public ScoreboardLocked() {
    super(EventsEnum.SCOREBOARD_LOCKED.name());
  }
}
