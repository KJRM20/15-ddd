package com.uno.scoreboards.application.applystrike;

import com.uno.scoreboards.application.shared.repositories.IEventsRepository;
import com.uno.scoreboards.application.shared.round.RoundResponse;
import com.uno.scoreboards.domain.round.Round;
import com.uno.shared.application.ICommandUseCase;
import reactor.core.publisher.Mono;

import static com.uno.scoreboards.application.shared.round.RoundMapper.mapToRound;

public class ApplyStrikeUseCase implements ICommandUseCase<ApplyStrikeRequest, Mono<RoundResponse>> {
  private final IEventsRepository roundRepository;

  public ApplyStrikeUseCase(IEventsRepository roundRepository) {
    this.roundRepository = roundRepository;
  }

  @Override
  public Mono<RoundResponse> execute(ApplyStrikeRequest request) {
    return roundRepository.findEventsByAggregateId(request.getAggregateId())
      .collectList()
      .map(events -> {
        Round round = Round.from(request.getAggregateId(), events);
        round.applyStrike(request.getPlayerId(), request.getDetails());
        round.getUncommittedEvents().forEach(roundRepository::save);
        round.markEventsAsCommitted();

        return mapToRound(round);
      });
  }
}
