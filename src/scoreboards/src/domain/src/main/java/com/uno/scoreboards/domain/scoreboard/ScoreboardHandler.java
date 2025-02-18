package com.uno.scoreboards.domain.scoreboard;

import com.uno.scoreboards.domain.scoreboard.entities.Player;
import com.uno.scoreboards.domain.scoreboard.entities.RoundHistory;
import com.uno.scoreboards.domain.scoreboard.events.AddedPlayer;
import com.uno.scoreboards.domain.scoreboard.events.AddedRoundToHistory;
import com.uno.scoreboards.domain.scoreboard.events.CreatedScoreboard;
import com.uno.scoreboards.domain.scoreboard.events.ReachedPlayerTargetScore;
import com.uno.scoreboards.domain.scoreboard.events.RevertedHistoryToRound;
import com.uno.scoreboards.domain.scoreboard.events.ScoreboardLocked;
import com.uno.scoreboards.domain.scoreboard.events.UpdatedPlayerPoints;
import com.uno.scoreboards.domain.scoreboard.values.IsWinner;
import com.uno.scoreboards.domain.scoreboard.values.Name;
import com.uno.scoreboards.domain.scoreboard.values.PlayerId;
import com.uno.scoreboards.domain.scoreboard.values.RoundHistoryId;
import com.uno.scoreboards.domain.scoreboard.values.RoundList;
import com.uno.scoreboards.domain.scoreboard.values.Score;
import com.uno.scoreboards.domain.scoreboard.values.State;
import com.uno.shared.domain.constants.StateEnum;
import com.uno.shared.domain.generic.DomainActionsContainer;
import com.uno.shared.domain.generic.DomainEvent;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ScoreboardHandler extends DomainActionsContainer {
  public ScoreboardHandler(Scoreboard scoreboard) {
    add(createScoreboard(scoreboard));
    add(addPlayer(scoreboard));
    add(updatePlayerPoints(scoreboard));
    add(reachPlayerTargetScore(scoreboard));
    add(addRoundToHistory(scoreboard));
    add(revertHistoryToRound(scoreboard));
    add(scoreboardLocked(scoreboard));
  }

  public Consumer<? extends DomainEvent> createScoreboard(Scoreboard scoreboard) {
    return (CreatedScoreboard event) -> {
      State state = State.of(StateEnum.IN_PROGRESS.name());
      scoreboard.setState(state);
      scoreboard.setPlayers(new ArrayList<>());
      scoreboard.setRoundHistory(new RoundHistory( RoundList.of(new ArrayList<>())));
    };
  }

  public Consumer<? extends DomainEvent> addPlayer(Scoreboard scoreboard) {
    return (AddedPlayer event) -> {
      scoreboard.validatePlayersQuantity();
      Player player = new Player(Name.of(event.getPlayerName()), Score.of(0), IsWinner.of(false));
      scoreboard.getPlayers().add(player);
    };
  }

  public Consumer<? extends DomainEvent> updatePlayerPoints(Scoreboard scoreboard) {
    return (UpdatedPlayerPoints event) -> {
      scoreboard.getPlayers().stream()
        .filter(player -> player.getIdentity().equals(PlayerId.of(event.getPlayerId())))
        .findFirst()
        .ifPresent(player -> player.addPointsToScore(event.getPoints()));
    };
  }

  public Consumer<? extends DomainEvent> reachPlayerTargetScore(Scoreboard scoreboard) {
    return (ReachedPlayerTargetScore event) -> {
      scoreboard.getPlayers().stream()
        .filter(player -> player.getIdentity().equals(PlayerId.of(event.getPlayerId())))
        .findFirst()
        .ifPresent(player -> player.setIsWinner(IsWinner.of(true)));
    };
  }

  public Consumer<? extends DomainEvent> addRoundToHistory(Scoreboard scoreboard) {
    return (AddedRoundToHistory event) -> {
      RoundHistoryId roundHistoryId = RoundHistoryId.of(event.getRoundId());
      scoreboard.getRoundHistory().addRoundToHistory(roundHistoryId);
    };
  }

  public Consumer<? extends DomainEvent> revertHistoryToRound(Scoreboard scoreboard) {
    return (RevertedHistoryToRound event) -> {
      String roundId = event.getRoundId();
      scoreboard.getRoundHistory().revertToRound(roundId);
    };
  }

  public Consumer<? extends DomainEvent> scoreboardLocked(Scoreboard scoreboard) {
    return (ScoreboardLocked event) -> {
      scoreboard.validateHaveWinner();
      State state = State.of(StateEnum.FINISHED.name());
      scoreboard.setState(state);
    };
  }
}
