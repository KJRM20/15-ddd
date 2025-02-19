package com.uno.scoreboards.domain.round;

import com.uno.shared.domain.constants.StateEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
  private Round round;

  @BeforeEach
  void setUp() {
    round = new Round();
  }

  @Test
  void testStartRoundSuccess() {
    assertNotNull(round);
    assertNotNull(round.getState());
    assertNotNull(round.getMoves());
    assertNotNull(round.getStrikes());
    assertNotNull(round.getResult());
    assertEquals(0, round.getMoves().size());
    assertEquals(0, round.getStrikes().size());
    assertEquals(0, round.getResult().getResultPlayers().size());
    assertNull(round.getResult().getRoundWinner());
    assertEquals(StateEnum.IN_PROGRESS.name(), round.getState().getValue());
  }

  @Test
  void testRecordMoveSuccess() {
    round.recordMove("1", "NUMBER", 1);
    assertEquals(1, round.getMoves().size());
    assertEquals("1", round.getMoves().get(0).getPlayerId().getValue());
    assertEquals("NUMBER", round.getMoves().get(0).getCard().getType());
    assertEquals(1, round.getMoves().get(0).getCard().getNumber());
  }

  @Test
  void testRecordMoveFailWrongType() {
    assertThrows(IllegalArgumentException.class, () -> {
        round.recordMove("1", "NOT_A_TYPE", 1);
    });
  }

  @Test
  void testRecordMoveFailWrongNumber() {
    assertThrows(IllegalArgumentException.class, () -> {
        round.recordMove("1", "NUMBER", -1);
    });
  }

  @Test
  void testApplyStrikeSuccess() {
    round.applyStrike("1", "FORGOT_UNO");
    assertEquals(1, round.getStrikes().size());
    assertEquals("1", round.getStrikes().get(0).getPlayerId().getValue());
    assertEquals("FORGOT_UNO", round.getStrikes().get(0).getDetails().getValue());
    assertEquals(20, round.getStrikes().get(0).getReducedPoints().getValue());
  }

  @Test
  void testApplyStrikeFailWrongDetails() {
    assertThrows(IllegalArgumentException.class, () -> {
      round.applyStrike("1", "NOT_A_DETAIL");
    });
  }

  @Test
  void testAssignRoundWinnerSuccess() {
    round.assignRoundWinner("1", 10);
    assertEquals(10, round.getResult().getRoundWinner().getExtraPoints().getValue());
    assertEquals("1", round.getResult().getRoundWinner().getPlayerId().getValue());
  }

  @Test
  void testAssignRoundWinnerFailWrongExtraPoints() {
    assertThrows(IllegalArgumentException.class, () -> {
      round.assignRoundWinner("1", -10);
    });
  }

  @Test
  void testFinishRoundSuccess() {
    round.assignRoundWinner("1", 10);
    round.finishRound();
    assertEquals(StateEnum.FINISHED.name(), round.getState().getValue());
  }

  @Test
  void testCalculateResultSuccess() {
    Round round1 = new Round();
    round1.recordMove("1", "NUMBER", 1);
    round1.recordMove("2", "NUMBER", 2);
    round1.applyStrike("1", "FORGOT_UNO");
    round1.assignRoundWinner("1", 10);
    round1.finishRound();
    round1.calculateResult();
    assertEquals(2, round1.getResult().getResultPlayers().size());
    assertEquals("1", round1.getResult().getResultPlayers().get(0).getPlayerId().getValue());
    assertEquals(1, round1.getResult().getResultPlayers().get(0).getPointsGained().getValue());
    assertEquals(20, round1.getResult().getResultPlayers().get(0).getPointsReduced().getValue());
    assertEquals("1", round1.getResult().getRoundWinner().getPlayerId().getValue());
    assertEquals(10, round1.getResult().getRoundWinner().getExtraPoints().getValue());
    assertEquals(-9, round1.getResult().getResultPlayers().get(0).getTotalPoints().getValue());

    assertEquals("2", round1.getResult().getResultPlayers().get(1).getPlayerId().getValue());
    assertEquals(2, round1.getResult().getResultPlayers().get(1).getPointsGained().getValue());
    assertEquals(0, round1.getResult().getResultPlayers().get(1).getPointsReduced().getValue());
    assertEquals(2, round1.getResult().getResultPlayers().get(1).getTotalPoints().getValue());
  }

  @Test
  void testValidateFinishedRoundSuccess() {
    Round round1 = new Round();
    round1.recordMove("1", "NUMBER", 1);
    round1.recordMove("2", "NUMBER", 2);
    round1.applyStrike("1", "FORGOT_UNO");
    round1.assignRoundWinner("1", 10);
    round1.finishRound();
    assertDoesNotThrow(round1::validateFinishedRound);
  }

  @Test
  void testValidateFinishedRoundFail() {
    Round round1 = new Round();
    round1.recordMove("1", "NUMBER", 1);
    round1.recordMove("2", "NUMBER", 2);
    round1.applyStrike("1", "FORGOT_UNO");
    round1.assignRoundWinner("1", 10);
    assertThrows(IllegalStateException.class, round1::validateFinishedRound);
  }

  @Test
  void testValidateHaveRoundWinnerSuccess() {
    Round round1 = new Round();
    round1.recordMove("1", "NUMBER", 1);
    round1.recordMove("2", "NUMBER", 2);
    round1.applyStrike("1", "FORGOT_UNO");
    round1.assignRoundWinner("1", 10);
    assertDoesNotThrow(round1::validateHaveRoundWinner);
  }

  @Test
  void testValidateHaveRoundWinnerFail() {
    Round round1 = new Round();
    round1.recordMove("1", "NUMBER", 1);
    round1.recordMove("2", "NUMBER", 2);
    round1.applyStrike("1", "FORGOT_UNO");
    assertThrows(IllegalStateException.class, round1::validateHaveRoundWinner);
  }

  @Test
  void testFromSuccess() {
    Round round1 = new Round();
    round1.recordMove("1", "NUMBER", 1);
    round1.recordMove("2", "NUMBER", 2);
    round1.applyStrike("1", "FORGOT_UNO");
    round1.assignRoundWinner("1", 10);
    round1.finishRound();
    round1.calculateResult();
    Round round2 = Round.from(round1.getIdentity().getValue(), round1.getUncommittedEvents());
    assertEquals(round1.getIdentity().getValue(), round2.getIdentity().getValue());
    assertEquals(round1.getState().getValue(), round2.getState().getValue());
    assertEquals(round1.getMoves().size(), round2.getMoves().size());
    assertEquals(round1.getStrikes().size(), round2.getStrikes().size());
    assertEquals(round1.getResult().getResultPlayers().size(), round2.getResult().getResultPlayers().size());
    assertEquals(round1.getResult().getRoundWinner().getPlayerId().getValue(), round2.getResult().getRoundWinner().getPlayerId().getValue());
  }
}