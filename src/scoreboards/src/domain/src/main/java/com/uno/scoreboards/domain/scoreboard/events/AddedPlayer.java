package com.uno.scoreboards.domain.scoreboard.events;

import com.uno.shared.domain.generic.DomainEvent;

public class AddedPlayer extends DomainEvent {
  private String playerId;
  private String playerName;

  public AddedPlayer() {
    super(EventsEnum.ADDED_PLAYER.name());
  }

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

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }
}
