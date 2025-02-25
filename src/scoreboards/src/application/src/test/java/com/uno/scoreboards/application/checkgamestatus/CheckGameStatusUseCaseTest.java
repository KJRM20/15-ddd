package com.uno.scoreboards.application.checkgamestatus;

import com.uno.scoreboards.application.shared.ports.IEventsRepositoryPort;
import com.uno.scoreboards.application.shared.scoreboard.ScoreboardResponse;
import com.uno.scoreboards.domain.scoreboard.events.AddedPlayer;
import com.uno.scoreboards.domain.scoreboard.events.CreatedScoreboard;
import com.uno.scoreboards.domain.scoreboard.events.UpdatedPlayerPoints;
import com.uno.shared.domain.constants.StateEnum;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CheckGameStatusUseCaseTest {
  private final IEventsRepositoryPort repository;
  private final CheckGameStatusUseCase checkGameStatusUseCase;

  public CheckGameStatusUseCaseTest() {
    this.repository = mock(IEventsRepositoryPort.class);
    this.checkGameStatusUseCase = new CheckGameStatusUseCase(repository);
  }

  @Test
  void testExecuteSuccess() {
    when(repository.findEventsByAggregateId(anyString())).thenReturn(Flux.just(
      new CreatedScoreboard(),
      new AddedPlayer("1","player1"),
      new AddedPlayer("2","player2"),
      new UpdatedPlayerPoints("1",100),
      new UpdatedPlayerPoints("2",50)
    ));

    CheckGameStatusRequest request = new CheckGameStatusRequest("1", 100);
    StepVerifier
      .create(checkGameStatusUseCase.execute(request))
      .assertNext(response-> {
        assertNotNull(response);
        assertEquals(response.getPlayers().size(), 2);

        List<ScoreboardResponse.Player> sortedPlayers = response.getPlayers().stream()
          .sorted(Comparator.comparing(ScoreboardResponse.Player::getPlayerId))
          .toList();

        ScoreboardResponse.Player player1 = sortedPlayers.get(0);
        ScoreboardResponse.Player player2 = sortedPlayers.get(1);

        assertEquals("player1", player1.getName());
        assertEquals(100, player1.getScore());
        assertTrue(player1.getWinner());

        assertEquals("player2", player2.getName());
        assertEquals(50, player2.getScore());
        assertFalse(player2.getWinner());
        assertEquals(response.getState(), StateEnum.FINISHED.name());
      })
      .verifyComplete();
  }
}