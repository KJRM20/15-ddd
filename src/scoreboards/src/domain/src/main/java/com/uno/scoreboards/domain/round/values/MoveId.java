package com.uno.scoreboards.domain.round.values;

import com.uno.shared.domain.generic.Identity;

public class MoveId extends Identity {
  public  MoveId( ) {
    super();
  }

  private MoveId(String value) {
    super(value);
  }

  public static MoveId of(String value) {
    return new MoveId(value);
  }
}
