package com.uno.scoreboards.application.recordmove;

import com.uno.scoreboards.application.shared.repositories.IEventsRepository;
import com.uno.scoreboards.domain.round.events.StartedRound;
import com.uno.scoreboards.domain.round.values.TypeEnum;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RecordMoveUseCaseTest {
  private final IEventsRepository roundRepository;
  private final RecordMoveUseCase recordMoveUseCase;

  public RecordMoveUseCaseTest() {
    this.roundRepository = mock(IEventsRepository.class);
    this.recordMoveUseCase = new RecordMoveUseCase(roundRepository);
  }

  @Test
  void testExecuteSuccess() {
    when(roundRepository.findEventsByAggregateId(anyString())).thenReturn(Flux.just(
      new StartedRound()
    ));

    RecordMoveRequest request = new RecordMoveRequest("1", "player1", TypeEnum.NUMBER.name(), 1);

    StepVerifier
      .create(recordMoveUseCase.execute(request))
      .assertNext(response-> {
        assertNotNull(response);
        assertEquals(request.getAggregateId(), response.getRoundId());
        assertEquals(1, response.getMoves().size());
        assertEquals("player1", response.getMoves().get(0).getPlayerId());
        assertEquals(TypeEnum.NUMBER.name(), response.getMoves().get(0).getCardType());
        assertEquals(1, response.getMoves().get(0).getNumber());
      })
      .verifyComplete();
  }
}