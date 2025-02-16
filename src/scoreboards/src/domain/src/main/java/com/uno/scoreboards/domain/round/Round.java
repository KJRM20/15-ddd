package com.uno.scoreboards.domain.round;

import com.uno.scoreboards.domain.round.entities.Move;
import com.uno.scoreboards.domain.round.entities.Result;
import com.uno.scoreboards.domain.round.entities.Strike;
import com.uno.scoreboards.domain.round.events.AppliedStrike;
import com.uno.scoreboards.domain.round.events.AssignedRoundWinner;
import com.uno.scoreboards.domain.round.events.CalculatedResult;
import com.uno.scoreboards.domain.round.events.RecordedMove;
import com.uno.scoreboards.domain.round.values.RoundId;
import com.uno.scoreboards.domain.round.values.State;
import com.uno.shared.domain.constants.StateEnum;
import com.uno.shared.domain.generic.AggregateRoot;

import java.util.List;

public class Round extends AggregateRoot<RoundId> {
  private State state;
  private List<Move> moves;
  private List<Strike> strikes;
  private Result result;

  // region Constructors
  public Round() {
    super(new RoundId());
    this.state = State.of(StateEnum.IN_PROGRESS.name());
  }

  private Round(RoundId identity) {
    super(identity);
  }
  // endregion

  // region Getters and Setters
  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public List<Move> getMoves() {
    return moves;
  }

  public void setMoves(List<Move> moves) {
    this.moves = moves;
  }

  public List<Strike> getStrikes() {
    return strikes;
  }

  public void setStrikes(List<Strike> strikes) {
    this.strikes = strikes;
  }

  public Result getResult() {
    return result;
  }

  public void setResult(Result result) {
    this.result = result;
  }
  // endregion

  // region Domain Actions
  public void recordMove(String playerId, String type) {
    apply(new RecordedMove(playerId, type));
  }

  public void applyStrike(String playerId, String details, Integer reducedPoints) {
    apply(new AppliedStrike(playerId, details, reducedPoints));
  }

  public void assignRoundWinner(String winnerId, Integer extraPoints) {
    apply(new AssignedRoundWinner(winnerId, extraPoints));
  }

  public void calculateResult(List<String> playerIds, List<Integer> pointsGained, List<Integer> pointsReduced, List<Integer> totalPoints, String roundWinnerId, Integer roundWinnerExtraPoints) {
    apply(new CalculatedResult(playerIds, pointsGained, pointsReduced, totalPoints, roundWinnerId, roundWinnerExtraPoints));
  }
  // endregion
}
