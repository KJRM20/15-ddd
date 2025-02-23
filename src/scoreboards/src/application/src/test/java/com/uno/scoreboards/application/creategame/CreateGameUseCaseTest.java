package com.uno.scoreboards.application.creategame;

import com.uno.scoreboards.application.shared.repositories.IEventsRepository;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CreateGameUseCaseTest {
  private final CreateGameUseCase createGameUseCase;
  private final IEventsRepository scoreboardRepository;
  private final IEventsRepository roundRepository;

  public CreateGameUseCaseTest() {
    scoreboardRepository = mock(IEventsRepository.class);
    roundRepository = mock(IEventsRepository.class);
    createGameUseCase = new CreateGameUseCase(scoreboardRepository, roundRepository);
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