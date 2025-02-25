package com.uno.scoreboards.application.checkgamestatus;

import com.uno.scoreboards.application.shared.ports.IEventsRepositoryPort;
import com.uno.scoreboards.application.shared.scoreboard.ScoreboardResponse;
import com.uno.scoreboards.domain.scoreboard.Scoreboard;
import com.uno.shared.application.ICommandUseCase;
import com.uno.shared.domain.generic.DomainEvent;
import reactor.core.publisher.Mono;

import java.util.Comparator;

import static com.uno.scoreboards.application.shared.scoreboard.ScoreboardMapper.mapToScoreboard;

public class CheckGameStatusUseCase implements ICommandUseCase<CheckGameStatusRequest, Mono<ScoreboardResponse>> {
  private final IEventsRepositoryPort repository;

  public CheckGameStatusUseCase(IEventsRepositoryPort repository) {
    this.repository = repository;
  }

  @Override
  public Mono<ScoreboardResponse> execute(CheckGameStatusRequest request) {
    return repository.findEventsByAggregateId(request.getAggregateId())
      .collectList()
      .map(events -> {
        events.sort(Comparator.comparing(DomainEvent::getWhen));
        Scoreboard scoreboard = Scoreboard.from(request.getAggregateId(), events);
        scoreboard.getPlayers().keySet().forEach(playerId ->
          scoreboard.reachPlayerTargetScore(playerId.getValue(), request.getTargetScore()));
        scoreboard.getPlayers().values().stream()
          .filter(player -> player.getIsWinner().getValue())
          .findFirst().ifPresent(player -> scoreboard.lockScoreboard());
        scoreboard.getUncommittedEvents().forEach(repository::save);
        scoreboard.markEventsAsCommitted();

        return mapToScoreboard(scoreboard);
      });
  }
}
