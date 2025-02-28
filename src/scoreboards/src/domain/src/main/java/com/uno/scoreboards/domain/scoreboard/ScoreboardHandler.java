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
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class ScoreboardHandler extends DomainActionsContainer {
  public ScoreboardHandler(Scoreboard scoreboard) {
    add(createScoreboard(scoreboard));
    add(addPlayer(scoreboard));
    add(updatePlayerPoints(scoreboard));
    add(reachPlayerTargetScore(scoreboard));
    add(addRoundToHistory(scoreboard));
    add(revertHistoryToRound(scoreboard));
    add(lockScoreboard(scoreboard));
  }

  public Consumer<? extends DomainEvent> createScoreboard(Scoreboard scoreboard) {
    return (CreatedScoreboard event) -> {
      State state = State.of(StateEnum.IN_PROGRESS.name());
      scoreboard.setState(state);
      scoreboard.setPlayers(new HashMap<>());
      scoreboard.setRoundHistory(new RoundHistory( RoundList.of(new ArrayList<>())));
    };
  }

  public Consumer<? extends DomainEvent> addPlayer(Scoreboard scoreboard) {
    return (AddedPlayer event) -> {
      scoreboard.validatePlayersQuantity();
      Player player = new Player(PlayerId.of(event.getPlayerId()), Name.of(event.getPlayerName()), Score.of(0), IsWinner.of(false));
      scoreboard.getPlayers().put(player.getIdentity(), player);
    };
  }

  public Consumer<? extends DomainEvent> updatePlayerPoints(Scoreboard scoreboard) {
    return (UpdatedPlayerPoints event) -> {
      List<Player> players = List.copyOf(scoreboard.getPlayers().values());
      players.stream().filter(p ->
        p.getIdentity().getValue()
          .equals(event.getPlayerId()))
          .findFirst()
          .ifPresent(player -> scoreboard.getPlayers().get(player.getIdentity()).addPointsToScore(event.getPoints()));
    };
  }

  public Consumer<? extends DomainEvent> reachPlayerTargetScore(Scoreboard scoreboard) {
    return (ReachedPlayerTargetScore event) -> {
      List<Player> players = List.copyOf(scoreboard.getPlayers().values());
      players.stream().filter(p ->
        p.getIdentity().getValue()
          .equals(event.getPlayerId()))
          .findFirst().ifPresent(player -> scoreboard.getPlayers()
          .get(player.getIdentity())
          .setIsWinner(
            IsWinner.of(scoreboard.getPlayers().get(player.getIdentity())
            .hasReachedTargetScore(event.getTargetScore()))));

      boolean notWinners = players.stream()
        .noneMatch(player -> player.getIsWinner().getValue());

      if (notWinners) {
        scoreboard.setState(State.of(StateEnum.IN_PROGRESS.name()));
      }
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
      scoreboard.getPlayers().values().forEach(player -> player.setIsWinner(IsWinner.of(false)));
      scoreboard.getPlayers().values().forEach(Player::resetScore);
      scoreboard.getRoundHistory().revertToRound(roundId);
    };
  }

  public Consumer<? extends DomainEvent> lockScoreboard(Scoreboard scoreboard) {
    return (ScoreboardLocked event) -> {
      scoreboard.validateHaveWinner();
      State state = State.of(StateEnum.FINISHED.name());
      scoreboard.setState(state);
    };
  }
}
