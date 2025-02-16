package com.uno.scoreboards.domain.scoreboard.values;

import com.uno.shared.domain.generic.IValueObject;

import static com.uno.shared.domain.utils.ValidationUtils.validateIntegerCouldBeNegative;

public class Score implements IValueObject {
  private final Integer value;

  private Score(Integer value) {
    validate();
    this.value = value;
  }

  public static Score of(Integer value) {
    return new Score(value);
  }

  @Override
  public void validate() {
    validateIntegerCouldBeNegative(value, "Score cannot be null");
  }

  public Integer getValue() {
    return value;
  }
}
