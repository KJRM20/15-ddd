package com.uno.scoreboards.domain.round.values;

import com.uno.shared.domain.generic.IValueObject;

import static com.uno.shared.domain.utils.ValidationUtils.validateInteger;

public class Points implements IValueObject {
  private final Integer value;

  private Points(Integer value) {
    validate();
    this.value = value;
  }

  public static Points of(Integer value) {
    return new Points(value);
  }

  @Override
  public void validate() {
    validateInteger(value, "Points cannot be null or negative");
  }

  public Integer getValue() {
    return value;
  }
}
