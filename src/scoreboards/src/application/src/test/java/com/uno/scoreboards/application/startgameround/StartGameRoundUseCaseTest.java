package com.uno.scoreboards.application.startgameround;

import com.uno.scoreboards.application.shared.ports.IEventsRepositoryPort;
import com.uno.scoreboards.domain.scoreboard.events.AddedPlayer;
import com.uno.scoreboards.domain.scoreboard.events.AddedRoundToHistory;
import com.uno.scoreboards.domain.scoreboard.events.CreatedScoreboard;
import com.uno.shared.domain.constants.StateEnum;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StartGameRoundUseCaseTest {
  private final IEventsRepositoryPort scoreboardRepository;
  private final StartGameRoundUseCase startGameRoundUseCase;

  public StartGameRoundUseCaseTest() {
    this.scoreboardRepository = mock(IEventsRepositoryPort.class);
    this.startGameRoundUseCase = new StartGameRoundUseCase(scoreboardRepository);
  }

  @Test
  void testExecuteSuccess() {
    when(scoreboardRepository.findEventsByAggregateId(anyString())).thenReturn(Flux.just(
      new CreatedScoreboard(),
      new AddedPlayer("1","player1"),
      new AddedPlayer("2","player2"),
      new AddedRoundToHistory("round1"),
      new AddedRoundToHistory("round2")
    ));

    StartGameRoundRequest request = new StartGameRoundRequest("1");

    StepVerifier
      .create(startGameRoundUseCase.execute(request))
      .assertNext(response-> {
        assertNotNull(response);
        assertEquals(StateEnum.IN_PROGRESS.name(), response.getState());
        assertEquals(0, response.getMoves().size());
        assertEquals(0, response.getStrikes().size());
        assertNull(response.getResult().getRoundWinner());
      })
      .verifyComplete();
  }
}