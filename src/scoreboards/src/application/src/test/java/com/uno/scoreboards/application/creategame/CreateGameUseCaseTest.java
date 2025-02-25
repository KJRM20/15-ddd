package com.uno.scoreboards.application.creategame;

import com.uno.scoreboards.application.shared.ports.IEventsRepositoryPort;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CreateGameUseCaseTest {
  private final CreateGameUseCase createGameUseCase;
  private final IEventsRepositoryPort repository;

  public CreateGameUseCaseTest() {
    repository = mock(IEventsRepositoryPort.class);
    createGameUseCase = new CreateGameUseCase(repository);
  }

  @Test
  void testExecuteSuccess() {
    CreateGameRequest request = new CreateGameRequest(null, Map.of("1", "player1", "2", "player2"));
    StepVerifier
      .create(createGameUseCase.execute(request))
      .assertNext(response-> {
        assertNotNull(response);
        assertEquals(request.getPlayers().size(), response.getPlayers().size());
        assertEquals("1", response.getPlayers().get(0).getPlayerId());
        assertEquals("player1", response.getPlayers().get(0).getName());
        assertEquals("2", response.getPlayers().get(1).getPlayerId());
        assertEquals("player2", response.getPlayers().get(1).getName());
        assertEquals(1, response.getRounds().size());
      })
      .verifyComplete();
  }

}