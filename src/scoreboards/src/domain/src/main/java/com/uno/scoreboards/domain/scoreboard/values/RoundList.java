package com.uno.scoreboards.domain.scoreboard.values;

import com.uno.shared.domain.generic.IValueObject;

import java.util.List;

import static com.uno.shared.domain.utils.ValidationUtils.validateStringList;

public class RoundList implements IValueObject {
  private final List<String> roundIds;

  private RoundList(List<String> roundIds) {
    validate();
    this.roundIds = roundIds;
  }

  public static RoundList of(List<String> roundIds) {
    return new RoundList(roundIds);
  }

  @Override
  public void validate() {
    validateStringList(roundIds, "Round ids cannot be null or empty");
  }

  public List<String> getRoundIds() {
    return roundIds;
  }
}
