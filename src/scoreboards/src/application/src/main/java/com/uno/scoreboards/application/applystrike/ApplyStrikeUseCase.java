package com.uno.scoreboards.application.applystrike;

import com.uno.scoreboards.application.shared.ports.IEventsRepositoryPort;
import com.uno.scoreboards.application.shared.round.RoundResponse;
import com.uno.scoreboards.domain.round.Round;
import com.uno.shared.application.ICommandUseCase;
import com.uno.shared.domain.generic.DomainEvent;
import reactor.core.publisher.Mono;

import java.util.Comparator;

import static com.uno.scoreboards.application.shared.round.RoundMapper.mapToRound;

public class ApplyStrikeUseCase implements ICommandUseCase<ApplyStrikeRequest, Mono<RoundResponse>> {
  private final IEventsRepositoryPort repository;

  public ApplyStrikeUseCase(IEventsRepositoryPort repository) {
    this.repository = repository;
  }

  @Override
  public Mono<RoundResponse> execute(ApplyStrikeRequest request) {
    return repository.findEventsByAggregateId(request.getAggregateId())
      .collectList()
      .map(events -> {
        events.sort(Comparator.comparing(DomainEvent::getWhen));
        Round round = Round.from(request.getAggregateId(), events);
        round.applyStrike(request.getPlayerId(), request.getDetails());
        round.getUncommittedEvents().forEach(repository::save);
        round.markEventsAsCommitted();

        return mapToRound(round);
      });
  }
}
