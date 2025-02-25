package com.uno.scoreboards.application.applystrike;

import com.uno.scoreboards.application.shared.ports.IEventsRepositoryPort;
import com.uno.scoreboards.domain.round.events.StartedRound;
import com.uno.scoreboards.domain.round.values.DetailEnum;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApplyStrikeUseCaseTest {
  private final IEventsRepositoryPort repository;
  private final ApplyStrikeUseCase applyStrikeUseCase;

  public ApplyStrikeUseCaseTest() {
    this.repository = mock(IEventsRepositoryPort.class);
    this.applyStrikeUseCase = new ApplyStrikeUseCase(repository);
  }

  @Test
  void testExecuteSuccess() {
    when(repository.findEventsByAggregateId(anyString())).thenReturn(Flux.just(
      new StartedRound()
    ));

    ApplyStrikeRequest request = new ApplyStrikeRequest("1", "player1", DetailEnum.FORGOT_UNO.name());
    StepVerifier
      .create(applyStrikeUseCase.execute(request))
      .assertNext(response-> {
        assertNotNull(response);
        assertEquals(request.getAggregateId(), response.getRoundId());
        assertEquals(1, response.getStrikes().size());
        assertEquals(request.getPlayerId(), response.getStrikes().get(0).getPlayerId());
        assertEquals(request.getDetails(), response.getStrikes().get(0).getDetails());
      })
      .verifyComplete();
  }
}