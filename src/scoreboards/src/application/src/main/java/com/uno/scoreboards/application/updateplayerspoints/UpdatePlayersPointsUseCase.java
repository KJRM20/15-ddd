package com.uno.scoreboards.application.updateplayerspoints;

import com.uno.scoreboards.application.shared.ports.IEventsRepositoryPort;
import com.uno.scoreboards.application.shared.scoreboard.ScoreboardResponse;
import com.uno.scoreboards.domain.round.Round;
import com.uno.scoreboards.domain.scoreboard.Scoreboard;
import com.uno.shared.application.ICommandUseCase;
import com.uno.shared.domain.generic.DomainEvent;
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
    Mono<List<DomainEvent>> scoreboardEventsMono = repository.findEventsByAggregateId(request.getAggregateId()).collectList();
    Mono<List<DomainEvent>> roundEventsMono = repository.findEventsByAggregateId(request.getRoundId()).collectList();

    return Mono.zip(scoreboardEventsMono, roundEventsMono).map(tuple -> {
      List<DomainEvent> scoreboardEvents = tuple.getT1();
      List<DomainEvent> roundEvents = tuple.getT2();

      scoreboardEvents.sort(Comparator.comparing(DomainEvent::getWhen));
      roundEvents.sort(Comparator.comparing(DomainEvent::getWhen));

      Scoreboard scoreboard = Scoreboard.from(request.getAggregateId(), scoreboardEvents);
      Round round = Round.from(request.getRoundId(), roundEvents);
      scoreboard.getPlayers().keySet()
        .forEach(playerId -> round.getResult()
          .getResultPlayers().stream()
          .filter(resultPlayer -> resultPlayer.getPlayerId().getValue().equals(playerId.getValue()))
          .findFirst()
          .ifPresent(resultPlayer -> scoreboard.updatePlayerPoints(
            playerId.getValue(), resultPlayer.getTotalPoints().getValue())));

      scoreboard.getUncommittedEvents().forEach(repository::save);
      scoreboard.markEventsAsCommitted();

      return mapToScoreboard(scoreboard);
    });
  }
}
