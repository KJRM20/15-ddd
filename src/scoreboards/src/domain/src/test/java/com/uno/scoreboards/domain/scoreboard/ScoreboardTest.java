package com.uno.scoreboards.domain.scoreboard;

import com.uno.scoreboards.domain.scoreboard.entities.Player;
import com.uno.scoreboards.domain.scoreboard.values.IsWinner;
import com.uno.scoreboards.domain.scoreboard.values.Name;
import com.uno.scoreboards.domain.scoreboard.values.PlayerId;
import com.uno.scoreboards.domain.scoreboard.values.Score;
import com.uno.shared.domain.constants.StateEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreboardTest {
  private Scoreboard scoreboard;

  @BeforeEach
  void setUp(){
    scoreboard = new Scoreboard();
  }

  @Test
  void testCreatedScoreboard(){
    assertNotNull(scoreboard);
    assertEquals(StateEnum.IN_PROGRESS.name(), scoreboard.getState().getValue());
    assertEquals(0, scoreboard.getPlayers().size());
    assertNotNull(scoreboard.getRoundHistory());
    assertEquals(0, scoreboard.getRoundHistory().getRoundHistoryList().getRoundIds().size() );
  }

  @Test
  void testAddPlayerSuccess() {
    Player player = new Player(PlayerId.of("1"), Name.of("player1"), Score.of(0), IsWinner.of(false));
    scoreboard.addPlayer(player.getIdentity().getValue(), player.getName().getValue());
    List<Player> players = List.copyOf(scoreboard.getPlayers().values());
    PlayerId playerId = players.get(0).getIdentity();
    assertEquals(1, scoreboard.getPlayers().size());
    assertEquals("1", scoreboard.getPlayers().get(playerId).getIdentity().getValue());
    assertEquals("player1", scoreboard.getPlayers().get(playerId).getName().getValue());
    assertEquals(0, scoreboard.getPlayers().get(playerId).getScore().getValue());
    assertFalse(scoreboard.getPlayers().get(playerId).getIsWinner().getValue());
  }

  @Test
  void testUpdatePlayerPointsSuccess() {
    scoreboard.addPlayer("1","player1");
    List<Player> players = List.copyOf(scoreboard.getPlayers().values());
    PlayerId playerId = players.get(0).getIdentity();
    scoreboard.updatePlayerPoints(playerId.getValue(), 10);
    assertEquals(10, scoreboard.getPlayers().get(playerId).getScore().getValue());
  }

  @Test
  void testReachPlayerTargetScoreSuccess() {
    scoreboard.addPlayer("1","player1");
    List<Player> players = List.copyOf(scoreboard.getPlayers().values());
    PlayerId playerId = players.get(0).getIdentity();
    scoreboard.updatePlayerPoints(playerId.getValue(), 10);
    scoreboard.reachPlayerTargetScore(playerId.getValue(), 10);
    assertTrue(scoreboard.getPlayers().get(players.get(0).getIdentity()).getIsWinner().getValue());
  }

  @Test
  void testAddRoundToHistorySuccess() {
    scoreboard.addRoundToHistory("round1");
    assertEquals(1, scoreboard.getRoundHistory().getRoundHistoryList().getRoundIds().size());
    assertEquals("round1", scoreboard.getRoundHistory().getRoundHistoryList().getRoundIds().get(0));
  }

  @Test
  void testRevertHistoryToRoundSuccess() {
    scoreboard.addRoundToHistory("round1");
    scoreboard.addRoundToHistory("round2");
    scoreboard.revertHistoryToRound("round1");
    assertEquals(1, scoreboard.getRoundHistory().getTotalRounds());
    assertEquals("round1", scoreboard.getRoundHistory().getRoundHistoryList().getRoundIds().get(0));
  }

  @Test
  void testBlockScoreboardSuccess() {
    scoreboard.addPlayer("1","player1");
    List<Player> players = List.copyOf(scoreboard.getPlayers().values());
    PlayerId playerId = players.get(0).getIdentity();
    scoreboard.updatePlayerPoints(playerId.getValue(), 10);
    scoreboard.reachPlayerTargetScore(playerId.getValue(), 10);
    scoreboard.lockScoreboard();
    assertEquals(StateEnum.FINISHED.name(), scoreboard.getState().getValue());
  }

  @Test
  void testValidatePlayerQuantitySuccess() {
    scoreboard.addPlayer("1","player1");
    assertDoesNotThrow(scoreboard::validatePlayersQuantity);
  }

  @Test
  void testValidatePlayerQuantityFail() {
    Scoreboard scoreboard1 = new Scoreboard();
    for (int i = 0; i < getPlayers().size(); i++) {
      String id = Integer.toString(i+1);
      scoreboard1.addPlayer(id,getPlayers().get(i));
    }

    assertThrows(IllegalStateException.class, () -> scoreboard1.addPlayer("11","player11"));
  }

  @Test
  void testValidateHaveWinnerSuccess() {
    scoreboard.addPlayer("1","player1");
    List<Player> players = List.copyOf(scoreboard.getPlayers().values());
    PlayerId playerId = players.get(0).getIdentity();
    scoreboard.updatePlayerPoints(playerId.getValue(), 10);
    scoreboard.reachPlayerTargetScore(playerId.getValue(), 10);
    scoreboard.lockScoreboard();
    assertDoesNotThrow(scoreboard::validateHaveWinner);
  }

  @Test
  void testValidateHaveWinnerFail() {
    scoreboard.addPlayer("1","player1");
    List<Player> players = List.copyOf(scoreboard.getPlayers().values());
    PlayerId playerId = players.get(0).getIdentity();
    scoreboard.updatePlayerPoints(playerId.getValue(), 0);
    scoreboard.reachPlayerTargetScore(playerId.getValue(), 10);

    assertThrows(IllegalStateException.class, () -> scoreboard.lockScoreboard());
  }

  @Test
  void testFromSuccess(){
    Scoreboard scoreboard1 = new Scoreboard();
    for (int i = 0; i < getPlayers().size(); i++) {
      String id = Integer.toString(i+1);
      scoreboard1.addPlayer(id,getPlayers().get(i));
    }

    scoreboard1.addRoundToHistory("round1");
    scoreboard1.updatePlayerPoints("1", 10);
    scoreboard1.reachPlayerTargetScore("1", 10);
    scoreboard1.lockScoreboard();

    Scoreboard scoreboard2 = Scoreboard.from(scoreboard1.getIdentity().getValue(), scoreboard1.getUncommittedEvents());
    assertNotNull(scoreboard2);
    assertEquals(scoreboard1.getState().getValue(), scoreboard2.getState().getValue());
    assertEquals(scoreboard1.getPlayers().size(), scoreboard2.getPlayers().size());
    assertEquals(scoreboard1.getRoundHistory().getTotalRounds(), scoreboard2.getRoundHistory().getTotalRounds());

    PlayerId playerId1 = getPlayerId("1", scoreboard1);
    PlayerId playerId2 = getPlayerId("1", scoreboard2);

    assertEquals(scoreboard1.getPlayers().get(playerId1).getScore().getValue(), scoreboard2.getPlayers().get(playerId2).getScore().getValue());
    assertEquals(scoreboard1.getPlayers().get(playerId1).getIsWinner().getValue(), scoreboard2.getPlayers().get(playerId2).getIsWinner().getValue());
  }

  private List<String> getPlayers() {
    return List.of("player1", "player2", "player3", "player4","player5","player6", "player7", "player8", "player9", "player10");
  }

  private PlayerId getPlayerId(String playerId, Scoreboard scoreboard) {
    return scoreboard.getPlayers().values().stream()
      .filter(player -> player.getIdentity().getValue().equals(playerId))
      .map(Player::getIdentity)
      .findFirst()
      .orElseThrow();
  }
}