package com.uno.scoreboards.domain.scoreboard.values;

import com.uno.shared.domain.generic.Identity;

public class ScoreboardId extends Identity {
  public ScoreboardId( ) {
    super();
  }

  private ScoreboardId(String value) {
    super(value);
  }

  public static ScoreboardId of(String value) {
    return new ScoreboardId(value);
  }
}