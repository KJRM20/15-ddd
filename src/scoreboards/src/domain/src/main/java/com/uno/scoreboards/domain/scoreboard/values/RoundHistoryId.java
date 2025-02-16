package com.uno.scoreboards.domain.scoreboard.values;

import com.uno.shared.domain.generic.Identity;

public class RoundHistoryId extends Identity {
  public  RoundHistoryId( ) {
    super();
  }

  private RoundHistoryId(String value) {
    super(value);
  }

  public static RoundHistoryId of(String value) {
    return new RoundHistoryId(value);
  }
}