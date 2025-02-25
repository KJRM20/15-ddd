package com.uno.scoreboards.application.reverthistorytoround;

import com.uno.scoreboards.application.shared.ports.IEventsRepositoryPort;
import com.uno.scoreboards.domain.round.events.RecordedMove;
import com.uno.scoreboards.domain.scoreboard.events.AddedPlayer;
import com.uno.scoreboards.domain.scoreboard.events.AddedRoundToHistory;
import com.uno.scoreboards.domain.scoreboard.events.CreatedScoreboard;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RevertHistoryToRoundUseCaseTest {
  private final IEventsRepositoryPort repository;
  private final RevertHistoryToRoundUseCase revertHistoryToRoundUseCase;

  public RevertHistoryToRoundUseCaseTest() {
    this.repository = mock(IEventsRepositoryPort.class);
    this.revertHistoryToRoundUseCase = new RevertHistoryToRoundUseCase(repository);
  }

  @Test
  void testExecuteSuccess() {
    when(repository.findEventsByAggregateId("1")).thenReturn(Flux.just(
      new CreatedScoreboard(),
      new AddedPlayer("1","player1"),
      new AddedPlayer("2","player2"),
      new AddedRoundToHistory("round1"),
      new AddedRoundToHistory("round2")
    ));

    RevertHistoryToRoundRequest request = new RevertHistoryToRoundRequest("1", "round1");
    StepVerifier
      .create(revertHistoryToRoundUseCase.execute(request))
      .assertNext(response-> {
        assertNotNull(response);
        assertEquals(request.getAggregateId(), response.getScoreboardId());
        System.out.println(response.getRounds());
        assertEquals(1, response.getRounds().size());
        assertEquals("round1", response.getRounds().get(0).getRoundId());
      })
      .verifyComplete();
  }
}