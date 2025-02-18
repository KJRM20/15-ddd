package com.uno.scoreboards.domain.scoreboard;

import com.uno.scoreboards.domain.scoreboard.entities.Player;
import com.uno.scoreboards.domain.scoreboard.entities.RoundHistory;
import com.uno.scoreboards.domain.scoreboard.events.AddedPlayer;
import com.uno.scoreboards.domain.scoreboard.events.AddedRoundToHistory;
import com.uno.scoreboards.domain.scoreboard.events.CreatedScoreboard;
import com.uno.scoreboards.domain.scoreboard.events.ReachedPlayerTargetScore;
import com.uno.scoreboards.domain.scoreboard.events.RevertedHistoryToRound;
import com.uno.scoreboards.domain.scoreboard.events.UpdatedPlayerPoints;
import com.uno.scoreboards.domain.scoreboard.values.IsWinner;
import com.uno.scoreboards.domain.scoreboard.values.ScoreboardId;
import com.uno.scoreboards.domain.scoreboard.values.State;
import com.uno.shared.domain.constants.StateEnum;
import com.uno.shared.domain.generic.AggregateRoot;

import java.util.List;

public class Scoreboard extends AggregateRoot<ScoreboardId> {
  private State state;
  private List<Player> players;
  private RoundHistory roundHistory;

  // region Constructors
  public Scoreboard() {
    super(new ScoreboardId());
    subscribe(new ScoreboardHandler(this));
    apply(new CreatedScoreboard());
  }

  private Scoreboard(ScoreboardId identity) {
    super(identity);
    subscribe(new ScoreboardHandler(this));
  }
  // endregion

  // region Getters and Setters
  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public List<Player> getPlayers() {
    return players;
  }

  public void setPlayers(List<Player> players) {
    this.players = players;
  }

  public RoundHistory getRoundHistory() {
    return roundHistory;
  }

  public void setRoundHistory(RoundHistory roundHistory) {
    this.roundHistory = roundHistory;
  }
  // endregion

  // region Domain Actions
  public void addPlayer(String name) {
    apply(new AddedPlayer(name));
  }

  public void addRoundToHistory(String roundId, String roundWinnerId) {
    apply(new AddedRoundToHistory(roundId, roundWinnerId));
  }

  public void revertHistoryToRound(String roundId) {
    apply(new RevertedHistoryToRound(roundId));
  }

  public void reachPlayerTargetScore(String playerId, Integer targetScore) {
    apply(new ReachedPlayerTargetScore(playerId, targetScore));
  }

  public void updatePlayerPoints(String playerId, Integer points) {
    apply(new UpdatedPlayerPoints(playerId, points));
  }
  // endregion

  // region Public Methods
  public void validatePlayersQuantity() {
    if(getPlayers().size() >= 10){
      throw new IllegalStateException("Scoreboard must have a maximum of 10 players");
    }
  }

  public void validateHaveWinner() {
    if(getPlayers().stream().filter(player -> player.getIsWinner().equals(IsWinner.of(true))).count() == 0){
      throw new IllegalStateException("Scoreboard must have a winner");
    }
  }
  // endregion
}
