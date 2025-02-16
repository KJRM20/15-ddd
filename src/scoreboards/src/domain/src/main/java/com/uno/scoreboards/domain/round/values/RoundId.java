package com.uno.scoreboards.domain.round.values;

import com.uno.shared.domain.generic.Identity;

public class RoundId extends Identity {
  public  RoundId( ) {
    super();
  }

  private RoundId(String value) {
    super(value);
  }

  public static RoundId of(String value) {
    return new RoundId(value);
  }
}