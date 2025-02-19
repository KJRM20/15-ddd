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
import com.uno.scoreboards.domain.round.values.Card;
import com.uno.scoreboards.domain.round.values.Details;
import com.uno.scoreboards.domain.round.values.PlayerId;
import com.uno.scoreboards.domain.round.values.Points;
import com.uno.scoreboards.domain.round.values.RoundWinner;
import com.uno.scoreboards.domain.round.values.State;
import com.uno.shared.domain.constants.StateEnum;
import com.uno.shared.domain.generic.DomainActionsContainer;
import com.uno.shared.domain.generic.DomainEvent;

import java.util.ArrayList;
import java.util.function.Consumer;

public class RoundHandler extends DomainActionsContainer {
  public RoundHandler(Round round) {
    add(startRound(round));
    add(recordMove(round));
    add(applyStrike(round));
    add(assignRoundWinner(round));
    add(calculateResult(round));
    add(finishRound(round));
  }

  public Consumer<? extends DomainEvent> startRound(Round round) {
    return (StartedRound event) -> {
      State state = State.of(StateEnum.IN_PROGRESS.name());
      round.setState(state);
      round.setMoves(new ArrayList<>());
      round.setStrikes(new ArrayList<>());
      round.setResult(new Result(new ArrayList<>(), null));
    };
  }

  public Consumer<? extends DomainEvent> recordMove(Round round) {
    return (RecordedMove event) -> {
      Move move = new Move(PlayerId.of(event.getPlayerId()), Card.of(event.getType(), event.getNumber()));
      move.assignPoints();
      round.getMoves().add(move);
    };
  }

  public Consumer<? extends DomainEvent> applyStrike(Round round) {
    return (AppliedStrike event) -> {
      Strike strike = new Strike(PlayerId.of(event.getPlayerId()), Details.of(event.getDetails()), Points.of(0));
      strike.assignReducedPoints();
      round.getStrikes().add(strike);
    };
  }

  public Consumer<? extends DomainEvent> assignRoundWinner(Round round) {
    return (AssignedRoundWinner event) -> {
      RoundWinner roundWinner = RoundWinner.of(PlayerId.of(event.getRoundWinner()), Points.of(event.getExtraPoints()));
      Result result = new Result( round.getResult().getResultPlayers(), roundWinner);
      round.setResult(result);
    };
  }

  public Consumer<? extends DomainEvent> calculateResult(Round round) {
    return (CalculatedResult event) -> {
      round.validateFinishedRound();
      round.getResult().calculateResult(round.getMoves(), round.getStrikes());
    };
  }

  public Consumer<? extends DomainEvent> finishRound(Round round) {
    return (FinishedRound event) -> {
      round.validateHaveRoundWinner();
      State state = State.of(StateEnum.FINISHED.name());
      round.setState(state);
    };
  }
}
