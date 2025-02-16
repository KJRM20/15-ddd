package com.uno.scoreboards.domain.round.values;

import com.uno.shared.domain.generic.IValueObject;

import java.util.Arrays;

import static com.uno.shared.domain.utils.ValidationUtils.validateInteger;
import static com.uno.shared.domain.utils.ValidationUtils.validateString;

public class Card implements IValueObject {
  private final String type;
  private final Integer number;

  private Card(String value, Integer number) {
    validate();
    this.number = number;
    this.type = value;
  }

  public static Card of(String value, Integer number) {
    return new Card(value, number);
  }

  @Override
  public void validate() {
    validateString(type, "Type cannot be null or empty");

    boolean isValid = Arrays.stream(TypeEnum.values())
      .anyMatch(type -> type.name().equals(this.type.toUpperCase()));

    if (!isValid) {
      throw new IllegalArgumentException("Invalid type");
    }

    if(type.toUpperCase().equals(TypeEnum.NUMBER.name())) {
      validateInteger(number, "Number cannot be null or negative");
    }
  }

  public String getType() {
    return type;
  }

  public Integer getNumber() {
    return number;
  }
}
