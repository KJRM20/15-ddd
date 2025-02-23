package com.uno.scoreboards.application.updateplayerspoints;

import com.uno.scoreboards.application.shared.repositories.IEventsRepository;
import com.uno.scoreboards.application.shared.scoreboard.ScoreboardResponse;
import com.uno.scoreboards.domain.round.events.AssignedRoundWinner;
import com.uno.scoreboards.domain.round.events.CalculatedResult;
import com.uno.scoreboards.domain.round.events.FinishedRound;
import com.uno.scoreboards.domain.round.events.RecordedMove;
import com.uno.scoreboards.domain.round.events.StartedRound;
import com.uno.scoreboards.domain.round.values.TypeEnum;
import com.uno.scoreboards.domain.scoreboard.events.AddedPlayer;
import com.uno.scoreboards.domain.scoreboard.events.AddedRoundToHistory;
import com.uno.scoreboards.domain.scoreboard.events.CreatedScoreboard;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UpdatePlayersPointsUseCaseTest {
  private final IEventsRepository scoreboardRepository;
  private final IEventsRepository roundRepository;
  private final UpdatePlayersPointsUseCase updatePlayersPointsUseCase;

  public UpdatePlayersPointsUseCaseTest() {
    this.scoreboardRepository = mock(IEventsRepository.class);
    this.roundRepository = mock(IEventsRepository.class);
    this.updatePlayersPointsUseCase = new UpdatePlayersPointsUseCase(scoreboardRepository, roundRepository);
  }

  @Test
  void testExecuteSuccess() {
    when(scoreboardRepository.findEventsByAggregateId(anyString())).thenReturn(Flux.just(
      new CreatedScoreboard(),
      new AddedPlayer("1","player1"),
      new AddedPlayer("2","player2"),
      new AddedRoundToHistory("round1")
    ));

    when(roundRepository.findEventsByAggregateId(anyString())).thenReturn(Flux.just(
      new StartedRound(),
      new RecordedMove("1", TypeEnum.NUMBER.name(), 9),
      new RecordedMove("2", TypeEnum.NUMBER.name(), 2),
      new AssignedRoundWinner("1", 10),
      new FinishedRound(),
      new CalculatedResult()
    ));


    UpdatePlayersPointsRequest request = new UpdatePlayersPointsRequest("1", "round1");
    StepVerifier
      .create(updatePlayersPointsUseCase.execute(request))
      .assertNext(response-> {
        assertNotNull(response);
        assertEquals(request.getAggregateId(), response.getScoreboardId());
        assertEquals(1, response.getRounds().size());
        assertEquals("round1", response.getRounds().get(0).getRoundId());

        List<ScoreboardResponse.Player> sortedPlayers = response.getPlayers().stream()
          .sorted(Comparator.comparing(ScoreboardResponse.Player::getPlayerId))
          .toList();

        ScoreboardResponse.Player player1 = sortedPlayers.get(0);
        ScoreboardResponse.Player player2 = sortedPlayers.get(1);

        assertEquals(19, player1.getScore());
        assertEquals(2, player2.getScore());
      })
      .verifyComplete();
  }
}