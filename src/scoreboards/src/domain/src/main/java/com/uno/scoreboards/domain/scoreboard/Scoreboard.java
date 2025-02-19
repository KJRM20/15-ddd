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
import com.uno.scoreboards.domain.scoreboard.values.PlayerId;
import com.uno.scoreboards.domain.scoreboard.values.ScoreboardId;
import com.uno.scoreboards.domain.scoreboard.values.State;
import com.uno.shared.domain.generic.AggregateRoot;
import com.uno.shared.domain.generic.DomainEvent;

import java.util.List;
import java.util.Map;

public class Scoreboard extends AggregateRoot<ScoreboardId> {
  private State state;
  private Map<PlayerId,Player> players;
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

  public Map<PlayerId,Player> getPlayers() {
    return players;
  }

  public void setPlayers(Map<PlayerId,Player> players) {
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

  public void addRoundToHistory(String roundId) {
    apply(new AddedRoundToHistory(roundId));
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

  public void lockScoreboard() {
    apply(new ScoreboardLocked());
  }
  // endregion

  // region Public Methods
  public void validatePlayersQuantity() {
    if(getPlayers().size() == 10){
      throw new IllegalStateException("Scoreboard must have a maximum of 10 players");
    }
  }

  public void validateHaveWinner() {
    List<Player> playersList = List.copyOf(getPlayers().values());
    if(playersList.stream().noneMatch(player -> player.getIsWinner().getValue())){
      throw new IllegalStateException("Scoreboard must have a winner");
    }
  }

  public static Scoreboard from(final String identity, final List<DomainEvent> events){
    Scoreboard scoreboard = new Scoreboard(ScoreboardId.of(identity));

    events.forEach(scoreboard::apply);
    return scoreboard;
  }
  // endregion
}
