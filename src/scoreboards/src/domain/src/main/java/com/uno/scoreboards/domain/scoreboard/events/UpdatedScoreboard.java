package com.uno.scoreboards.domain.scoreboard.events;

import com.uno.shared.domain.generic.DomainEvent;

public class UpdatedScoreboard extends DomainEvent {
  private final String scoreboardId;

  public UpdatedScoreboard(String scoreboardId) {
    super(EventsEnum.UPDATED_SCOREBOARD.name());
    this.scoreboardId = scoreboardId;
  }

  public String getScoreboardId() {
    return scoreboardId;
  }
}
