package com.uno.scoreboards.domain.round.entities;

import com.uno.scoreboards.domain.round.values.MoveId;
import com.uno.scoreboards.domain.round.values.PlayerId;
import com.uno.scoreboards.domain.round.values.Points;
import com.uno.scoreboards.domain.round.values.Card;
import com.uno.scoreboards.domain.round.values.TypeEnum;
import com.uno.shared.domain.generic.Entity;

public class Move extends Entity<MoveId> {
  private PlayerId playerId;
  private Card card;
  private Points points;

  public Move(MoveId identity, PlayerId playerId, Card card) {
    super(identity);
    this.card = card;
    this.points = Points.of(0);
  }

  public Move(PlayerId playerId, Card card) {
    super(new MoveId());
    this.card = card;
    this.points = Points.of(0);
  }

  public void assignPoints() {
    TypeEnum typeEnum = TypeEnum.valueOf(card.getType().toUpperCase());

    if (typeEnum.equals(TypeEnum.NUMBER)) {
      points = Points.of(card.getNumber());
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

  public Card getCard() {
    return card;
  }

  public void setCard(Card card) {
    this.card = card;
  }

  public Points getPoints() {
    return points;
  }

  public void setPoints(Points points) {
    this.points = points;
  }
}
