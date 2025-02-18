package com.uno.scoreboards.domain.scoreboard.events;

import com.uno.shared.domain.generic.DomainEvent;

public class AddedPlayer extends DomainEvent {
  private final String playerName;

  public AddedPlayer(String name) {
    super(EventsEnum.ADDED_PLAYER.name());
    this.playerName = name;
  }

  public String getPlayerName() {
    return playerName;
  }
}
