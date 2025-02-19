package com.uno.scoreboards.domain.scoreboard.values;

import com.uno.shared.domain.generic.IValueObject;
import static com.uno.shared.domain.utils.ValidationUtils.validateNotNull;

public class IsWinner implements IValueObject {
  private final boolean value;

  private IsWinner(boolean value) {
    this.value = value;
    validate();
  }

  public static IsWinner of(boolean value) {
    return new IsWinner(value);
  }

  @Override
  public void validate() {
    validateNotNull(value, "Is winner cannot be null");
  }

  public boolean getValue() {
    return value;
  }
}
