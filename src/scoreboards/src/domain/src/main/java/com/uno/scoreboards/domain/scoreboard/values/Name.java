package com.uno.scoreboards.domain.scoreboard.values;

import com.uno.shared.domain.generic.IValueObject;
import static com.uno.shared.domain.utils.ValidationUtils.validateString;

public class Name implements IValueObject {
  private final String value;

  private Name(String value) {
    this.value = value;
    validate();
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
