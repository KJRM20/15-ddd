package com.uno.scoreboards.domain.round.entities;

import com.uno.scoreboards.domain.round.values.PlayerId;
import com.uno.scoreboards.domain.round.values.Points;
import com.uno.scoreboards.domain.round.values.ResultId;
import com.uno.scoreboards.domain.round.values.ResultPlayer;
import com.uno.scoreboards.domain.round.values.RoundWinner;
import com.uno.scoreboards.domain.round.values.TotalPoints;
import com.uno.shared.domain.generic.Entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Result extends Entity<ResultId> {
  private List<ResultPlayer> resultPlayers;
  private RoundWinner roundWinner;

  public Result(ResultId identity, List<ResultPlayer> resultPlayers, RoundWinner roundWinner) {
    super(identity);
    this.resultPlayers = resultPlayers;
    this.roundWinner = roundWinner;
  }

  public Result(List<ResultPlayer> resultPlayers, RoundWinner roundWinner) {
    super(new ResultId());
    this.resultPlayers = resultPlayers;
    this.roundWinner = roundWinner;
  }

  public void calculateResult(List<Move> moves, List<Strike> strikes) {
    Set<String> playerIds = new HashSet<>();
    moves.forEach(move -> playerIds.add(move.getPlayerId().getValue()));
    strikes.forEach(strike -> playerIds.add(strike.getPlayerId().getValue()));

    resultPlayers = playerIds.stream().map(playerId -> {
      return calculateResultPlayer(moves, strikes, PlayerId.of(playerId));
    }).collect(toList());
  }

  private ResultPlayer calculateResultPlayer(List<Move> moves, List<Strike> strikes,PlayerId playerId) {
    int pointsGained = moves.stream()
      .filter(move -> move.getPlayerId().getValue().equals(playerId.getValue())).mapToInt(move -> move.getPoints().getValue()).sum();
    int pointsReduced = strikes.stream()
      .filter(strike -> strike.getPlayerId().getValue().equals(playerId.getValue())).mapToInt(strike -> strike.getReducedPoints().getValue()).sum();
    int totalPoints = pointsGained - pointsReduced + passingExtraPointsToRoundWinner(playerId).getValue();

    return ResultPlayer.of(playerId, Points.of(pointsGained), Points.of(pointsReduced), TotalPoints.of(totalPoints));
  }

  private Points passingExtraPointsToRoundWinner(PlayerId playerId) {
    if(playerId.getValue().equals(roundWinner.getPlayerId().getValue())){
      return roundWinner.getExtraPoints();
    }
    return Points.of(0);
  }

  public List<ResultPlayer> getResultPlayers() {
    return resultPlayers;
  }

  public void setResultPlayers(List<ResultPlayer> resultPlayers) {
    this.resultPlayers = resultPlayers;
  }

  public RoundWinner getRoundWinner() {
    return roundWinner;
  }

  public void setRoundWinner(RoundWinner roundWinner) {
    this.roundWinner = roundWinner;
  }
}
