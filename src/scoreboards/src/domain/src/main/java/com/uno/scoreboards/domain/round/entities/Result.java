package com.uno.scoreboards.domain.round.entities;

import com.uno.scoreboards.domain.round.values.PlayerId;
import com.uno.scoreboards.domain.round.values.Points;
import com.uno.scoreboards.domain.round.values.ResultId;
import com.uno.scoreboards.domain.round.values.ResultPlayer;
import com.uno.scoreboards.domain.round.values.ResultPlayers;
import com.uno.scoreboards.domain.round.values.RoundWinner;
import com.uno.scoreboards.domain.round.values.TotalPoints;
import com.uno.shared.domain.generic.Entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Result extends Entity<ResultId> {
  private ResultPlayers resultPlayers;
  private RoundWinner roundWinner;

  public Result(ResultId identity, ResultPlayers resultPlayers, RoundWinner roundWinner) {
    super(identity);
    this.resultPlayers = resultPlayers;
    this.roundWinner = roundWinner;
  }

  public Result(ResultPlayers resultPlayers, RoundWinner roundWinner) {
    super(new ResultId());
    this.resultPlayers = resultPlayers;
    this.roundWinner = roundWinner;
  }

  public void calculateResult(List<Move> moves, List<Strike> strikes) {
    Set<PlayerId> playerIds = new HashSet<>();
    moves.forEach(move -> playerIds.add(move.getPlayerId()));
    strikes.forEach(strike -> playerIds.add(strike.getPlayerId()));

    List<ResultPlayer> resultPlayerList = playerIds.stream().map(playerId -> {
      return calculateResultPlayer(moves, strikes, playerId);
    }).collect(Collectors.toList());

    resultPlayers = ResultPlayers.of(resultPlayerList, roundWinner);
  }

  public ResultPlayer calculateResultPlayer(List<Move> moves, List<Strike> strikes,PlayerId playerId) {
    int pointsGained = moves.stream()
      .filter(move -> move.getPlayerId().equals(playerId)).mapToInt(move -> move.getPoints().getValue()).sum();
    int pointsReduced = strikes.stream()
      .filter(strike -> strike.getPlayerId().equals(playerId)).mapToInt(strike -> strike.getReducedPoints().getValue()).sum();
    int totalPoints = pointsGained - pointsReduced + passingExtraPointsToRoundWinner(playerId).getValue();

    return ResultPlayer.of(playerId, Points.of(pointsGained), Points.of(pointsReduced), TotalPoints.of(totalPoints));
  }

  public Points passingExtraPointsToRoundWinner(PlayerId playerId) {
    if(playerId.equals(roundWinner.getPlayerId())){
      return roundWinner.getExtraPoints();
    }
      return Points.of(0);
  }

  public ResultPlayers getResultPlayers() {
    return resultPlayers;
  }

  public void setResultPlayers(ResultPlayers resultPlayers) {
    this.resultPlayers = resultPlayers;
  }

  public RoundWinner getRoundWinner() {
    return roundWinner;
  }

  public void setRoundWinner(RoundWinner roundWinner) {
    this.roundWinner = roundWinner;
  }
}
