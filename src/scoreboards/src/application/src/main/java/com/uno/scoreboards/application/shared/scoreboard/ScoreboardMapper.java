package com.uno.scoreboards.application.shared.scoreboard;

import com.uno.scoreboards.domain.scoreboard.Scoreboard;

public class ScoreboardMapper {
  public static ScoreboardResponse mapToScoreboard(Scoreboard scoreboard) {
    return new ScoreboardResponse(
      scoreboard.getIdentity().getValue(),
      scoreboard.getState().getValue(),
      scoreboard.getPlayers().values().stream().map(player -> new ScoreboardResponse.Player(
        player.getIdentity().getValue(),
        player.getName().getValue(),
        player.getScore().getValue(),
        player.getIsWinner().getValue()
      )).toList(),
      scoreboard.getRoundHistory().getRoundHistoryList().getRoundIds()
        .stream().map(ScoreboardResponse.Round::new).toList()
    );
  }
}
