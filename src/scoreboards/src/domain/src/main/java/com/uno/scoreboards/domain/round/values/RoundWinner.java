package com.uno.scoreboards.domain.round.values;

import com.uno.shared.domain.generic.IValueObject;

import static com.uno.shared.domain.utils.ValidationUtils.validateNotNull;

public class RoundWinner implements IValueObject {
  private final PlayerId playerId;
  private final Points extraPoints;

  private RoundWinner(PlayerId playerId, Points extraPoints) {
    validate();
    this.playerId = playerId;
    this.extraPoints = extraPoints;
  }

  public static RoundWinner of(PlayerId playerId, Points extraPoints) {
    return new RoundWinner(playerId, extraPoints);
  }

  @Override
  public void validate() {
    validateNotNull(playerId, "Player id cannot be null");
    validateNotNull(extraPoints, "Extra points cannot be null");
    playerId.validate();
    extraPoints.validate();
  }

  public PlayerId getPlayerId() {
    return playerId;
  }

  public Points getExtraPoints() {
    return extraPoints;
  }
}
