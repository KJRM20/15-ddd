package com.uno.scoreboards.domain.round;

import com.uno.scoreboards.domain.round.entities.Move;
import com.uno.scoreboards.domain.round.entities.Result;
import com.uno.scoreboards.domain.round.entities.Strike;
import com.uno.scoreboards.domain.round.events.AppliedStrike;
import com.uno.scoreboards.domain.round.events.AssignedRoundWinner;
import com.uno.scoreboards.domain.round.events.CalculatedResult;
import com.uno.scoreboards.domain.round.events.FinishedRound;
import com.uno.scoreboards.domain.round.events.RecordedMove;
import com.uno.scoreboards.domain.round.events.StartedRound;
import com.uno.scoreboards.domain.round.values.RoundId;
import com.uno.scoreboards.domain.round.values.State;
import com.uno.shared.domain.constants.StateEnum;
import com.uno.shared.domain.generic.AggregateRoot;
import com.uno.shared.domain.generic.DomainEvent;

import java.util.List;

public class Round extends AggregateRoot<RoundId> {
  private State state;
  private List<Move> moves;
  private List<Strike> strikes;
  private Result result;

  // region Constructors
  public Round() {
    super(new RoundId());
    subscribe(new RoundHandler(this));
    apply(new StartedRound());
  }

  private Round(RoundId identity) {
    super(identity);
    subscribe(new RoundHandler(this));
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
  public void recordMove(String playerId, String type, Integer number) {
    apply(new RecordedMove(playerId, type, number));
  }

  public void applyStrike(String playerId, String details) {
    apply(new AppliedStrike(playerId, details));
  }

  public void assignRoundWinner(String winnerId, Integer extraPoints) {
    apply(new AssignedRoundWinner(winnerId, extraPoints));
  }

  public void calculateResult() {
    apply(new CalculatedResult());
  }

  public void finishRound() {
    apply(new FinishedRound());
  }
  // endregion

  // region Public Methods
  public void validateFinishedRound() {
    if(!getState().getValue().equals(StateEnum.FINISHED.name())){
      throw new IllegalStateException("Round must be in finished state");
    }
  }

  public void validateHaveRoundWinner() {
    if (getResult().getRoundWinner() == null) {
      throw new IllegalStateException("Round must have winner");
    }
  }

  public static Round from(final String identity, final List<DomainEvent> events) {
    Round round = new Round(RoundId.of(identity));
    events.forEach(round::apply);
    round.markEventsAsCommitted();
    return round;
  }
  // endregion

}
