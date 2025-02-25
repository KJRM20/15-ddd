package com.uno.scoreboards.application.startgameround;

import com.uno.scoreboards.application.shared.ports.IEventsRepositoryPort;
import com.uno.scoreboards.application.shared.round.RoundResponse;
import com.uno.scoreboards.domain.round.Round;
import com.uno.scoreboards.domain.scoreboard.Scoreboard;
import com.uno.shared.application.ICommandUseCase;
import com.uno.shared.domain.generic.DomainEvent;
import reactor.core.publisher.Mono;

import java.util.Comparator;

import static com.uno.scoreboards.application.shared.round.RoundMapper.mapToRound;

public class StartGameRoundUseCase implements ICommandUseCase<StartGameRoundRequest, Mono<RoundResponse>> {
  private final IEventsRepositoryPort repository;

  public StartGameRoundUseCase(IEventsRepositoryPort repository) {
    this.repository = repository;
  }

  @Override
  public Mono<RoundResponse> execute(StartGameRoundRequest request) {
    return repository.findEventsByAggregateId(request.getAggregateId())
      .collectList()
      .map(events -> {
        events.sort(Comparator.comparing(DomainEvent::getWhen));
        Scoreboard scoreboard = Scoreboard.from(request.getAggregateId(), events);
        Round round = new Round();
        scoreboard.addRoundToHistory(round.getIdentity().getValue());
        scoreboard.getUncommittedEvents().forEach(repository::save);
        round.getUncommittedEvents().forEach(repository::save);
        scoreboard.markEventsAsCommitted();
        round.markEventsAsCommitted();

        return mapToRound(round);
      });
  }
}
