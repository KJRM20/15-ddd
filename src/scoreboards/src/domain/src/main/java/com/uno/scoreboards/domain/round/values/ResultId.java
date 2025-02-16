package com.uno.scoreboards.domain.round.values;

import com.uno.shared.domain.generic.Identity;

public class ResultId extends Identity {
  public  ResultId( ) {
    super();
  }

  private ResultId(String value) {
    super(value);
  }

  public static ResultId of(String value) {
    return new ResultId(value);
  }
}