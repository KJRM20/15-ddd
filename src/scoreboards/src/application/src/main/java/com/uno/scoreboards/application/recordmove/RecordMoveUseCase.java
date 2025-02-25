package com.uno.scoreboards.application.recordmove;

import com.uno.scoreboards.application.shared.ports.IEventsRepositoryPort;
import com.uno.scoreboards.application.shared.round.RoundResponse;
import com.uno.scoreboards.domain.round.Round;
import com.uno.shared.application.ICommandUseCase;
import com.uno.shared.domain.generic.DomainEvent;
import reactor.core.publisher.Mono;

import java.util.Comparator;

import static com.uno.scoreboards.application.shared.round.RoundMapper.mapToRound;

public class RecordMoveUseCase implements ICommandUseCase<RecordMoveRequest, Mono<RoundResponse>> {
  private final IEventsRepositoryPort roundRepository;

  public RecordMoveUseCase(IEventsRepositoryPort roundRepository) {
    this.roundRepository = roundRepository;
  }

  @Override
  public Mono<RoundResponse> execute(RecordMoveRequest request) {
    return roundRepository.findEventsByAggregateId(request.getAggregateId())
      .collectList()
      .map(events -> {
        events.sort(Comparator.comparing(DomainEvent::getWhen));
        Round round = Round.from(request.getAggregateId(), events);
        round.recordMove(request.getPlayerId(), request.getCardType(), request.getNumber());
        round.getUncommittedEvents().forEach(roundRepository::save);
        round.markEventsAsCommitted();

        return mapToRound(round);
      });
  }
}
