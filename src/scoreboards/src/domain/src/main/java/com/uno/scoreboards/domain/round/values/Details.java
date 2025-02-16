package com.uno.scoreboards.domain.round.values;

import com.uno.shared.domain.generic.IValueObject;

import static com.uno.shared.domain.utils.ValidationUtils.validateString;

public class Details implements IValueObject {
  private final String value;

  private Details(String value) {
    validate();
    this.value = value;
  }

  public static Details of(String value) {
    return new Details(value);
  }

  @Override
  public void validate() {
    validateString(value, "Details cannot be null or empty");
  }

  public String getValue() {
    return value;
  }
}
