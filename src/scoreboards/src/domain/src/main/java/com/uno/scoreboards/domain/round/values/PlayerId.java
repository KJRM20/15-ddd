package com.uno.scoreboards.domain.round.values;

import com.uno.shared.domain.generic.IValueObject;

import static com.uno.shared.domain.utils.ValidationUtils.validateString;

public class PlayerId implements IValueObject {
  private final String value;

  private PlayerId(String value) {
    this.value = value;
    validate();
  }

  public static PlayerId of(String value) {
    return new PlayerId(value);
  }

  @Override
  public void validate() {
    validateString(value, "Player id cannot be null or empty");
  }

  public String getValue() {
    return value;
  }
}
