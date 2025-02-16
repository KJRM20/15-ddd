package com.uno.scoreboards.domain.scoreboard.events;

import com.uno.shared.domain.generic.DomainEvent;

public class CreatedScoreboard extends DomainEvent {
  private final String scoreboardId;

  public CreatedScoreboard(String scoreboardId) {
    super(EventsEnum.CREATED_SCOREBOARD.name());
    this.scoreboardId = scoreboardId;
  }

  public String getScoreboardId() {
    return scoreboardId;
  }
}
