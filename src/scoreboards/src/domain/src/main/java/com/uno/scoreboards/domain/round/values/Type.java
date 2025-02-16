package com.uno.scoreboards.domain.round.values;

import com.uno.shared.domain.generic.IValueObject;

import java.util.Arrays;

import static com.uno.shared.domain.utils.ValidationUtils.validateInteger;
import static com.uno.shared.domain.utils.ValidationUtils.validateIntegerCouldBeNegative;
import static com.uno.shared.domain.utils.ValidationUtils.validateString;

public class Type implements IValueObject {
  private final String value;
  private final Integer number;

  private Type(String value, Integer number) {
    validate();
    this.number = number;
    this.value = value;
  }

  public static Type of(String value, Integer number) {
    return new Type(value, number);
  }

  @Override
  public void validate() {
    validateString(value, "Type cannot be null or empty");

    boolean isValid = Arrays.stream(TypeEnum.values())
      .anyMatch(type -> type.name().equals(value.toUpperCase()));

    if (!isValid) {
      throw new IllegalArgumentException("Invalid type");
    }

    if(value.toUpperCase().equals(TypeEnum.NUMBER.name())) {
      validateInteger(number, "Number cannot be null or negative");
    }
  }

  public String getValue() {
    return value;
  }

  public Integer getNumber() {
    return number;
  }
}
