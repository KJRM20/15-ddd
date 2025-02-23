package com.uno.scoreboards.application.shared.round;

import java.util.List;

public class RoundResponse {
  private final String roundId;
  private final String state;
  private final List<Move> moves;
  private final List<Strike> strikes;
  private final Result result;

  public RoundResponse(String roundId, String state, List<Move> moves, List<Strike> strikes, Result result) {
    this.roundId = roundId;
    this.state = state;
    this.moves = moves;
    this.strikes = strikes;
    this.result = result;
  }

  public String getRoundId() {
    return roundId;
  }

  public String getState() {
    return state;
  }

  public List<Move> getMoves() {
    return moves;
  }

  public List<Strike> getStrikes() {
    return strikes;
  }

  public Result getResult() {
    return result;
  }

  public static class Move {
    private final String playerId;
    private final String cardType;
    private final Integer number;

    public Move(String playerId, String cardType, Integer number) {
      this.playerId = playerId;
      this.cardType = cardType;
      this.number = number;
    }

    public String getPlayerId() {
      return playerId;
    }

    public String getCardType() {
      return cardType;
    }

    public Integer getNumber() {
      return number;
    }
  }

  public static class Strike {
    private final String playerId;
    private final String details;
    private final Integer reducedPoints;

    public Strike(String playerId, String details, Integer reducedPoints) {
      this.playerId = playerId;
      this.details = details;
      this.reducedPoints = reducedPoints;
    }

    public String getPlayerId() {
      return playerId;
    }

    public String getDetails() {
      return details;
    }

    public Integer getReducedPoints() {
      return reducedPoints;
    }
  }

  public static class Result {
    private final List<ResultPlayer> resultPlayers;
    private final RoundWinner roundWinner;

    public Result(List<ResultPlayer> resultPlayers, RoundWinner roundWinner) {
      this.resultPlayers = resultPlayers;
      this.roundWinner = roundWinner;
    }

    public List<ResultPlayer> getResultPlayers() {
      return resultPlayers;
    }

    public RoundWinner getRoundWinner() {
      return roundWinner;
    }

    public static class ResultPlayer {
      private final String playerId;
      private final Integer pointsGained;
      private final Integer pointsReduced;
      private final Integer totalPoints;

      public ResultPlayer(String playerId, Integer pointsGained, Integer pointsReduced, Integer totalPoints) {
        this.playerId = playerId;
        this.pointsGained = pointsGained;
        this.pointsReduced = pointsReduced;
        this.totalPoints = totalPoints;
      }

      public String getPlayerId() {
        return playerId;
      }

      public Integer getPointsGained() {
        return pointsGained;
      }

      public Integer getPointsReduced() {
        return pointsReduced;
      }

      public Integer getTotalPoints() {
        return totalPoints;
      }
    }

    public static class RoundWinner {
      private final String playerId;
      private final Integer extraPoints;

      public RoundWinner(String playerId, Integer extraPoints) {
        this.playerId = playerId;
        this.extraPoints = extraPoints;
      }

      public String getPlayerId() {
        return playerId;
      }

      public Integer getExtraPoints() {
        return extraPoints;
      }
    }
  }
}
