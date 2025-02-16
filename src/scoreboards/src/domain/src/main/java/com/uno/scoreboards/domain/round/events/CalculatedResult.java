package com.uno.scoreboards.domain.round.events;

import com.uno.shared.domain.generic.DomainEvent;

import java.util.List;

public class CalculatedResult extends DomainEvent {
  private final List<String> playerIds;
  private final List<String> pointsGained;
  private final List<String> pointsReduced;
  private final List<String> totalPoints;
  private final String roundWinnerId;
  private final Integer roundWinnerExtraPoints;

  public CalculatedResult(List<String> playerIds, List<String> pointsGained, List<String> pointsReduced, List<String> totalPoints, String roundWinnerId, Integer roundWinnerExtraPoints) {
    super(EventsEnum.CALCULATED_RESULT.name());
    this.playerIds = playerIds;
    this.pointsGained = pointsGained;
    this.pointsReduced = pointsReduced;
    this.totalPoints = totalPoints;
    this.roundWinnerId = roundWinnerId;
    this.roundWinnerExtraPoints = roundWinnerExtraPoints;
  }

  public List<String> getPlayerIds() {
    return playerIds;
  }

  public List<String> getPointsGained() {
    return pointsGained;
  }

  public List<String> getPointsReduced() {
    return pointsReduced;
  }

  public List<String> getTotalPoints() {
    return totalPoints;
  }

  public String getRoundWinnerId() {
    return roundWinnerId;
  }

  public Integer getRoundWinnerExtraPoints() {
    return roundWinnerExtraPoints;
  }
}
