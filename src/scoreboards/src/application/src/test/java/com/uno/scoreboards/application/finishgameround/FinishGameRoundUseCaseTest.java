package com.uno.scoreboards.application.finishgameround;

import com.uno.scoreboards.application.shared.ports.IEventsRepositoryPort;
import com.uno.scoreboards.domain.round.events.RecordedMove;
import com.uno.scoreboards.domain.round.events.StartedRound;
import com.uno.scoreboards.domain.round.values.TypeEnum;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FinishGameRoundUseCaseTest {
  private final IEventsRepositoryPort repository;
  private final FinishGameRoundUseCase finishGameRoundUseCase;

  public FinishGameRoundUseCaseTest() {
    this.repository = mock(IEventsRepositoryPort.class);
    this.finishGameRoundUseCase = new FinishGameRoundUseCase(repository);
  }

  @Test
  void testExecuteSuccess() {

    when(repository.findEventsByAggregateId(anyString())).thenReturn(Flux.just(
      new StartedRound(),
      new RecordedMove("player1", TypeEnum.NUMBER.name(), 1),
      new RecordedMove("player2", TypeEnum.NUMBER.name(), 2)
    ));

    FinishGameRoundRequest request = new FinishGameRoundRequest("1", "player1", 50);
    StepVerifier
      .create(finishGameRoundUseCase.execute(request))
      .assertNext(response-> {
        assertNotNull(response);
        assertEquals(request.getAggregateId(), response.getRoundId());
        assertEquals(2, response.getMoves().size());
        assertEquals("player1", response.getMoves().get(0).getPlayerId());
        assertEquals(TypeEnum.NUMBER.name(), response.getMoves().get(0).getCardType());
        assertEquals(1, response.getMoves().get(0).getNumber());
        assertEquals("player2", response.getMoves().get(1).getPlayerId());
        assertEquals(TypeEnum.NUMBER.name(), response.getMoves().get(1).getCardType());
        assertEquals(2, response.getMoves().get(1).getNumber());
        assertEquals("player1", response.getResult().getRoundWinner().getPlayerId());
        assertEquals(50, response.getResult().getRoundWinner().getExtraPoints());
      })
      .verifyComplete();
  }
}