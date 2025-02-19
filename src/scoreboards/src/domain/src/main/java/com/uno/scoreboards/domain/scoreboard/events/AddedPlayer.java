package com.uno.scoreboards.domain.scoreboard.events;

import com.uno.shared.domain.generic.DomainEvent;

public class AddedPlayer extends DomainEvent {
  private final String playerId;
  private final String playerName;

  public AddedPlayer(String playerId, String name) {
    super(EventsEnum.ADDED_PLAYER.name());
    this.playerId = playerId;
    this.playerName = name;
  }

  public String getPlayerId() {
    return playerId;
  }

  public String getPlayerName() {
    return playerName;
  }
}
