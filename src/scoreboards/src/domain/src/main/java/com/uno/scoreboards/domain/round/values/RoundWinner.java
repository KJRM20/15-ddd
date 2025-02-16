package com.uno.scoreboards.domain.round.values;

import com.uno.shared.domain.generic.IValueObject;

import static com.uno.shared.domain.utils.ValidationUtils.validateNotNull;
import static com.uno.shared.domain.utils.ValidationUtils.validateString;

public class RoundWinner implements IValueObject {
  private final String playerId;
  private final Points extraPoints;

  private RoundWinner(String playerId, Points extraPoints) {
    validate();
    this.playerId = playerId;
    this.extraPoints = extraPoints;
  }

  public static RoundWinner of(String playerId, Points extraPoints) {
    return new RoundWinner(playerId, extraPoints);
  }

  @Override
  public void validate() {
    validateString(playerId, "Player id cannot be null or empty");
    validateNotNull(extraPoints, "Extra points cannot be null");
    extraPoints.validate();
  }

  public String getPlayerId() {
    return playerId;
  }

  public Points getExtraPoints() {
    return extraPoints;
  }
}
