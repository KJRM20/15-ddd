package com.uno.scoreboards.application.shared.round;

import com.uno.scoreboards.domain.round.Round;

public class RoundMapper {

  public static RoundResponse mapToRound(Round round) {
    return new RoundResponse(
      round.getIdentity().getValue(),
      round.getState().getValue(),
      round.getMoves().stream().map(move -> new RoundResponse.Move(
        move.getPlayerId().getValue(),
        move.getCard().getType(),
        move.getCard().getNumber()
      )).toList(),
      round.getStrikes().stream().map(strike -> new RoundResponse.Strike(
        strike.getPlayerId().getValue(),
        strike.getDetails().getValue(),
        strike.getReducedPoints().getValue()
      )).toList(),
      new RoundResponse.Result(
        round.getResult().getResultPlayers().stream().map(resultPlayer -> new RoundResponse.Result.ResultPlayer(
          resultPlayer.getPlayerId().getValue(),
          resultPlayer.getPointsGained().getValue(),
          resultPlayer.getPointsReduced().getValue(),
          resultPlayer.getTotalPoints().getValue()
        )).toList(),
        round.getResult().getRoundWinner().getPlayerId().getValue()
      )
    );
  }
}