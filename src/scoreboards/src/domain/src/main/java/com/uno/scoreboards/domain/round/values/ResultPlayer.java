package com.uno.scoreboards.domain.round.values;

import com.uno.shared.domain.generic.IValueObject;

import static com.uno.shared.domain.utils.ValidationUtils.validateNotNull;

public class ResultPlayer implements IValueObject {
  private final PlayerId playerId;
  private final Points pointsGained;
  private final Points pointsReduced;
  private final TotalPoints totalPoints;

  private ResultPlayer(PlayerId playerId, Points pointsGained, Points pointsReduced, TotalPoints totalPoints) {
    this.playerId = playerId;
    this.pointsGained = pointsGained;
    this.pointsReduced = pointsReduced;
    this.totalPoints = totalPoints;
    validate();
  }

  public static ResultPlayer of(PlayerId playerId, Points pointsGained, Points pointsReduced, TotalPoints totalPoints) {
    return new ResultPlayer(playerId, pointsGained, pointsReduced, totalPoints);
  }

  @Override
  public void validate() {
    validateNotNull(playerId, "Player id cannot be null");
    validateNotNull(pointsGained, "Points gained cannot be null");
    validateNotNull(pointsReduced, "Points reduced cannot be null");
    validateNotNull(totalPoints, "Total points cannot be null");
    playerId.validate();
    pointsGained.validate();
    pointsReduced.validate();
    totalPoints.validate();
  }

  public PlayerId getPlayerId() {
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
