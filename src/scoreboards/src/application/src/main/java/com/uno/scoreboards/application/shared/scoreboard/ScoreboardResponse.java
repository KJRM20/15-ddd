package com.uno.scoreboards.application.shared.scoreboard;

import java.util.List;

public class ScoreboardResponse {
  private final String scoreboardId;
  private final String state;
  private final List<Player> players;
  private final List<Round> rounds;

  public ScoreboardResponse(String scoreboardId, String state, List<Player> players, List<Round> rounds) {
    this.scoreboardId = scoreboardId;
    this.state = state;
    this.players = players;
    this.rounds = rounds;
  }

  public String getScoreboardId() {
    return scoreboardId;
  }

  public String getState() {
    return state;
  }

  public List<Player> getPlayers() {
    return players;
  }

  public List<Round> getRounds() {
    return rounds;
  }

  public static class Player {
    private final String playerId;
    private final String name;
    private final Integer score;
    private final Boolean isWinner;

    public Player(String playerId, String name, Integer score, Boolean isWinner) {
      this.playerId = playerId;
      this.name = name;
      this.score = score;
      this.isWinner = isWinner;
    }

    public String getPlayerId() {
      return playerId;
    }

    public String getName() {
      return name;
    }

    public Integer getScore() {
      return score;
    }

    public Boolean getWinner() {
      return isWinner;
    }
  }

  public static class Round {
    private final String roundId;

    public Round(String roundId) {
      this.roundId = roundId;
    }

    public String getRoundId() {
      return roundId;
    }
  }
}
