package com.uno.scoreboards.domain.round.values;

import com.uno.shared.domain.generic.IValueObject;

import static com.uno.shared.domain.utils.ValidationUtils.validateIntegerCouldBeNegative;

public class TotalPoints implements IValueObject {
  private final Integer value;

  private TotalPoints(Integer value) {
    this.value = value;
    validate();
  }

  public static TotalPoints of(Integer value) {
    return new TotalPoints(value);
  }

  @Override
  public void validate() {
    validateIntegerCouldBeNegative(value, "Total points cannot be null");
  }

  public Integer getValue() {
    return value;
  }
}
