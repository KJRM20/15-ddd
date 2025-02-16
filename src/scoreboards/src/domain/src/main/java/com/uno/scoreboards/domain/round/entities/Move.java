package com.uno.scoreboards.domain.round.entities;

import com.uno.scoreboards.domain.round.values.MoveId;
import com.uno.scoreboards.domain.round.values.PlayerId;
import com.uno.scoreboards.domain.round.values.Points;
import com.uno.scoreboards.domain.round.values.Type;
import com.uno.scoreboards.domain.round.values.TypeEnum;
import com.uno.shared.domain.generic.Entity;

public class Move extends Entity<MoveId> {
  private PlayerId playerId;
  private Type type;
  private Points points;

  public Move(MoveId identity, PlayerId playerId, Type type) {
    super(identity);
    this.type = type;
    this.points = Points.of(0);
  }

  public Move(PlayerId playerId, Type type) {
    super(new MoveId());
    this.type = type;
    this.points = Points.of(0);
  }

  private void assignPoints() {
    TypeEnum typeEnum = TypeEnum.valueOf(type.getValue().toUpperCase());

    if (typeEnum.equals(TypeEnum.NUMBER)) {
      points = Points.of(type.getNumber());
    } else {
      points = Points.of(typeEnum.getPoints());
    }
  }

  public PlayerId getPlayerId() {
    return playerId;
  }

  public void setPlayerId(PlayerId playerId) {
    this.playerId = playerId;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public Points getPoints() {
    return points;
  }

  public void setPoints(Points points) {
    this.points = points;
  }
}
