package com.uno.scoreboards.domain.scoreboard.events;

import com.uno.shared.domain.generic.DomainEvent;

public class AddedPlayer extends DomainEvent {
  private final String playerName;
  private final Integer score;

  public AddedPlayer(String name, Integer score) {
    super(EventsEnum.ADDED_PLAYER.name());
    this.playerName = name;
    this.score = score;
  }

  public String getPlayerName() {
    return playerName;
  }

  public Integer getScore() {
    return score;
  }
}
