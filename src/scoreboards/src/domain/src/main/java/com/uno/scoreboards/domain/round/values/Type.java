package com.uno.scoreboards.domain.round.values;

import com.uno.shared.domain.generic.IValueObject;

import java.util.Arrays;

import static com.uno.shared.domain.utils.ValidationUtils.validateString;

public class Type implements IValueObject {
  private final String value;

  private Type(String value) {
    validate();
    this.value = value;
  }

  public static Type of(String value) {
    return new Type(value);
  }

  @Override
  public void validate() {
    validateString(value, "Type cannot be null or empty");

    boolean isValid = Arrays.stream(TypeEnum.values())
      .anyMatch(type -> type.name().equals(value.toUpperCase()));

    if (!isValid) {
      throw new IllegalArgumentException("Invalid type");
    }
  }

  public String getValue() {
    return value;
  }
}
