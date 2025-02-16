package com.uno.scoreboards.domain.round.events;

import com.uno.shared.domain.generic.DomainEvent;

import java.util.List;

public class CalculatedResult extends DomainEvent {
  private final List<String> playerIds;
  private final List<Integer> pointsGained;
  private final List<Integer> pointsReduced;
  private final List<Integer> totalPoints;
  private final String roundWinnerId;
  private final Integer roundWinnerExtraPoints;

  public CalculatedResult(List<String> playerIds, List<Integer> pointsGained, List<Integer> pointsReduced, List<Integer> totalPoints, String roundWinnerId, Integer roundWinnerExtraPoints) {
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

  public List<Integer> getPointsGained() {
    return pointsGained;
  }

  public List<Integer> getPointsReduced() {
    return pointsReduced;
  }

  public List<Integer> getTotalPoints() {
    return totalPoints;
  }

  public String getRoundWinnerId() {
    return roundWinnerId;
  }

  public Integer getRoundWinnerExtraPoints() {
    return roundWinnerExtraPoints;
  }
}
