package com.uno.scoreboards.domain.scoreboard.values;

import com.uno.shared.domain.generic.Identity;

public class ScoreBoardId extends Identity {
  public  ScoreBoardId( ) {
    super();
  }

  private ScoreBoardId(String value) {
    super(value);
  }

  public static ScoreBoardId of(String value) {
    return new ScoreBoardId(value);
  }
}