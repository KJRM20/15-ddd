package com.uno.scoreboards.application.updateplayerspoints;

import com.uno.scoreboards.application.shared.ports.IEventsRepositoryPort;
import com.uno.scoreboards.application.shared.scoreboard.ScoreboardResponse;
import com.uno.scoreboards.domain.round.Round;
import com.uno.scoreboards.domain.scoreboard.Scoreboard;
import com.uno.shared.application.ICommandUseCase;
import com.uno.shared.domain.generic.DomainEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;

import static com.uno.scoreboards.application.shared.scoreboard.ScoreboardMapper.mapToScoreboard;

public class UpdatePlayersPointsUseCase implements ICommandUseCase<UpdatePlayersPointsRequest, Mono<ScoreboardResponse>> {
  private final IEventsRepositoryPort repository;

  public UpdatePlayersPointsUseCase(IEventsRepositoryPort repository) {
    this.repository = repository;
  }

  @Override
  public Mono<ScoreboardResponse> execute(UpdatePlayersPointsRequest request) {
    return repository.findEventsByAggregateId(request.getAggregateId())
      .collectList()
      .flatMap(scoreboardEvents -> {
        scoreboardEvents.sort(Comparator.comparing(DomainEvent::getWhen));
        Scoreboard scoreboard = Scoreboard.from(request.getAggregateId(), scoreboardEvents);

        List<String> roundIds = scoreboard.getRoundHistory().getRoundHistoryList().getRoundIds();

        return Flux.fromIterable(roundIds)
          .flatMap(roundId -> repository.findEventsByAggregateId(roundId).collectList()
            .map(roundEvents -> {
              roundEvents.sort(Comparator.comparing(DomainEvent::getWhen));
              return Round.from(roundId, roundEvents);
            })).collectList()
          .map(rounds -> updateScoreBoardWithRounds(scoreboard, rounds));
      });
  }

  public ScoreboardResponse updateScoreBoardWithRounds(Scoreboard scoreboard, List<Round> rounds) {
    scoreboard.getPlayers().forEach((playerId, player) -> player.resetScore());
    rounds.forEach(round -> round.getResult().getResultPlayers().forEach(resultPlayer -> {
      String playerId = resultPlayer.getPlayerId().getValue();
      int points = resultPlayer.getTotalPoints().getValue();
      scoreboard.updatePlayerPoints(playerId, points);
    }));

    scoreboard.getUncommittedEvents().forEach(repository::save);
    scoreboard.markEventsAsCommitted();
    return mapToScoreboard(scoreboard);
  }
}
