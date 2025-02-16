package com.uno.scoreboards.domain.round.values;

import com.uno.shared.domain.generic.IValueObject;

import java.util.List;

import static com.uno.shared.domain.utils.ValidationUtils.validateNotNull;
import static com.uno.shared.domain.utils.ValidationUtils.validateObjectList;
import static com.uno.shared.domain.utils.ValidationUtils.validateString;

public class ResultPlayers implements IValueObject {
  private final List<ResultPlayer> resultPlayers;
  private final String roundWinnerId;
  private final Points winnerExtraPoints;

  private ResultPlayers(List<ResultPlayer> players, String roundWinnerId, Points winnerExtraPoints) {
    validate();
    this.resultPlayers = players;
    this.roundWinnerId = roundWinnerId;
    this.winnerExtraPoints = winnerExtraPoints;
  }

  public static ResultPlayers of(List<ResultPlayer> players, String roundWinnerId, Points winnerExtraPoints) {
    return new ResultPlayers(players, roundWinnerId, winnerExtraPoints);
  }

  @Override
  public void validate() {
    validateObjectList(resultPlayers, "Players cannot be null or empty");
    resultPlayers.forEach(ResultPlayer::validate);
    validateString(roundWinnerId, "Round winner id cannot be null or empty");
    validateNotNull(winnerExtraPoints, "Winner extra points cannot be null");
    winnerExtraPoints.validate();
  }

  public List<ResultPlayer> getResultPlayers() {
    return resultPlayers;
  }

  public String getRoundWinnerId() {
    return roundWinnerId;
  }

  public Points getWinnerExtraPoints() {
    return winnerExtraPoints;
  }
}
