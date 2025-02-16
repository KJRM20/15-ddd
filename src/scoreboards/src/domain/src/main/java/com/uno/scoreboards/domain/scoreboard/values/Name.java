package com.uno.scoreboards.domain.scoreboard.values;

import com.uno.shared.domain.generic.IValueObject;

import static com.uno.shared.domain.utils.ValidationUtils.validateString;

public class Name implements IValueObject {
  private final String value;

  private Name(String value) {
    validate();
    this.value = value;
  }

  public static Name of(String value) {
    return new Name(value);
  }

  @Override
  public void validate() {
    validateString(value, "Name cannot be null or empty");
  }

  public String getValue() {
    return value;
  }
}
