package com.uno.scoreboards.domain.round.values;

public enum TypeEnum {
  NUMBER(0),
  SKIP(20),
  REVERSE(20),
  DRAW_TWO(20),
  WILD(50),
  WILD_DRAW_FOUR(50),
  WILD_DRAW_SIX(50),
  WILD_DRAW_EIGHT(50),
  WILD_DRAW_TEN(50);

  private final int points;

  TypeEnum(int points) {
    this.points = points;
  }

  public int getPoints() {
    return points;
  }
}
