package com.uno.scoreboards.domain.round.values;

import com.uno.shared.domain.generic.IValueObject;

import static com.uno.shared.domain.utils.ValidationUtils.validateState;

public class State implements IValueObject {
  private final String value;

  private State(String value) {
    validate();
    this.value = value;
  }

  public static State of(String value) {
    return new State(value);
  }

  @Override
  public void validate() {
    validateState(value, "State cannot be null, empty or invalid");
  }

  public String getValue() {
    return value;
  }
}
