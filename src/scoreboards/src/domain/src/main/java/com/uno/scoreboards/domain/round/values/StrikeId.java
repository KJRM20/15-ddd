package com.uno.scoreboards.domain.round.values;

import com.uno.shared.domain.generic.Identity;

public class StrikeId extends Identity {
  public  StrikeId( ) {
    super();
  }

  private StrikeId(String value) {
    super(value);
  }

  public static StrikeId of(String value) {
    return new StrikeId(value);
  }
}