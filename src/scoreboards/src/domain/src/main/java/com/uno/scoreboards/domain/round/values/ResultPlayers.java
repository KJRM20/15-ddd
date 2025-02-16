package com.uno.scoreboards.domain.round.values;

import com.uno.shared.domain.generic.IValueObject;

import java.util.List;

import static com.uno.shared.domain.utils.ValidationUtils.validateNotNull;
import static com.uno.shared.domain.utils.ValidationUtils.validateObjectList;

public class ResultPlayers implements IValueObject {
  private final List<ResultPlayer> resultPlayers;
  private final RoundWinner roundWinner;

  private ResultPlayers(List<ResultPlayer> players, RoundWinner roundWinner) {
    validate();
    this.resultPlayers = players;
    this.roundWinner = roundWinner;
  }

  public static ResultPlayers of(List<ResultPlayer> resultPlayers, RoundWinner roundWinner){
    return new ResultPlayers(resultPlayers, roundWinner);
  }

  @Override
  public void validate() {
    validateObjectList(resultPlayers, "Players cannot be null or empty");
    resultPlayers.forEach(ResultPlayer::validate);
    validateNotNull(roundWinner, "Round winner cannot be null");
    roundWinner.validate();
  }

  public List<ResultPlayer> getResultPlayers() {
    return resultPlayers;
  }

  public RoundWinner getRoundWinner() {
    return roundWinner;
  }
}
