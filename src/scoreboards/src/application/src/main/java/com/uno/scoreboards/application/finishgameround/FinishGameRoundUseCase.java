package com.uno.scoreboards.application.finishgameround;

import com.uno.scoreboards.application.shared.ports.IEventsRepositoryPort;
import com.uno.scoreboards.application.shared.round.RoundResponse;
import com.uno.scoreboards.domain.round.Round;
import com.uno.shared.application.ICommandUseCase;
import com.uno.shared.domain.generic.DomainEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Comparator;

import static com.uno.scoreboards.application.shared.round.RoundMapper.mapToRound;

public class FinishGameRoundUseCase implements ICommandUseCase<FinishGameRoundRequest, Mono<RoundResponse>> {
  private final IEventsRepositoryPort repository;

  public FinishGameRoundUseCase(IEventsRepositoryPort repository) {
    this.repository = repository;
  }

  @Override
  public Mono<RoundResponse> execute(FinishGameRoundRequest request) {
    return repository.findEventsByAggregateId(request.getAggregateId())
      .collectList()
      .flatMap(events -> {
        events.sort(Comparator.comparing(DomainEvent::getWhen));
        Round round = Round.from(request.getAggregateId(), events);

        return Flux.concat(
            Mono.delay(Duration.ofMillis(100)).doOnNext(ignored -> round.assignRoundWinner(request.getWinnerId(), request.getExtraPoints())),
            Mono.delay(Duration.ofMillis(100)).doOnNext(ignored -> round.finishRound()),
            Mono.delay(Duration.ofMillis(100)).doOnNext(ignored -> round.calculateResult())
          )
          .then(Mono.fromCallable(() -> {
            round.getUncommittedEvents().forEach(repository::save);
            round.markEventsAsCommitted();
            return mapToRound(round);
          }));
      });
  }
}
