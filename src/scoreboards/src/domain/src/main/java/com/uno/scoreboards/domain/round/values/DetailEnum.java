package com.uno.scoreboards.domain.round.values;

public enum DetailEnum {
  FORGOT_UNO(20),
  FAKE_UNO(50),
  ILLEGAL_MOVE(20);

  private final int points;

  DetailEnum(int points) {
    this.points = points;
  }

  public int getPoints() {
    return points;
  }
}
