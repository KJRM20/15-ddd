package com.uno.scoreboards.application.creategame;

import com.uno.shared.application.Request;

import java.util.Map;

public class CreateGameRequest extends Request {
  private final Map<String,String> players;

  public CreateGameRequest(String aggregateId, Map<String, String> players) {
    super(aggregateId);
    this.players = players;
  }

  public Map<String, String> getPlayers() {
    return players;
  }
}
