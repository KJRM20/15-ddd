package com.uno.scoreboards.domain.round.entities;

import com.uno.scoreboards.domain.round.values.DetailEnum;
import com.uno.scoreboards.domain.round.values.Details;
import com.uno.scoreboards.domain.round.values.PlayerId;
import com.uno.scoreboards.domain.round.values.Points;
import com.uno.scoreboards.domain.round.values.StrikeId;
import com.uno.shared.domain.generic.Entity;

public class Strike extends Entity<StrikeId> {
  private PlayerId playerId;
  private Details details;
  private Points reducedPoints;

  public Strike(StrikeId identity, PlayerId playerId, Details details, Points reducedPoints) {
    super(identity);
    this.playerId = playerId;
    this.details = details;
    this.reducedPoints = reducedPoints;
  }

  public Strike(PlayerId playerId, Details details, Points reducedPoints) {
    super(new StrikeId());
    this.playerId = playerId;
    this.details = details;
    this.reducedPoints = reducedPoints;
  }

  public void assignReducedPoints() {
    DetailEnum detailEnum = DetailEnum.valueOf(details.getValue().toUpperCase());
    reducedPoints = Points.of(detailEnum.getPoints());
  }

  public PlayerId getPlayerId() {
    return playerId;
  }

  public void setPlayerId(PlayerId playerId) {
    this.playerId = playerId;
  }

  public Details getDetails() {
    return details;
  }

  public void setDetails(Details details) {
    this.details = details;
  }

  public Points getReducedPoints() {
    return reducedPoints;
  }

  public void setReducedPoints(Points reducedPoints) {
    this.reducedPoints = reducedPoints;
  }
}
