package com.uno.scoreboards.domain.round.values;

import com.uno.shared.domain.generic.IValueObject;

import static com.uno.shared.domain.utils.ValidationUtils.validateNotNull;
import static com.uno.shared.domain.utils.ValidationUtils.validateString;

public class ResultPlayer implements IValueObject {
  private final String playerId;
  private final Points pointsGained;
  private final Points pointsReduced;
  private final TotalPoints totalPoints;

  private ResultPlayer(String playerId, Points pointsGained, Points pointsReduced, TotalPoints totalPoints) {
    validate();
    this.playerId = playerId;
    this.pointsGained = pointsGained;
    this.pointsReduced = pointsReduced;
    this.totalPoints = totalPoints;
  }

  public static ResultPlayer of(String playerId, Points pointsGained, Points pointsReduced, TotalPoints totalPoints) {
    return new ResultPlayer(playerId, pointsGained, pointsReduced, totalPoints);
  }

  @Override
  public void validate() {
    validateString(playerId, "Player id cannot be null or empty");
    validateNotNull(pointsGained, "Points gained cannot be null");
    validateNotNull(pointsReduced, "Points reduced cannot be null");
    validateNotNull(totalPoints, "Total points cannot be null");
    pointsGained.validate();
    pointsReduced.validate();
    totalPoints.validate();
  }

  public String getPlayerId() {
    return playerId;
  }

  public Points getPointsGained() {
    return pointsGained;
  }

  public Points getPointsReduced() {
    return pointsReduced;
  }

  public TotalPoints getTotalPoints() {
    return totalPoints;
  }
}
