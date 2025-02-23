package com.uno.scoreboards.application.finishgameround;

import com.uno.scoreboards.application.shared.repositories.IEventsRepository;
import com.uno.scoreboards.application.shared.round.RoundResponse;
import com.uno.scoreboards.domain.round.Round;
import com.uno.shared.application.ICommandUseCase;
import reactor.core.publisher.Mono;

import static com.uno.scoreboards.application.shared.round.RoundMapper.mapToRound;

public class FinishGameRoundUseCase implements ICommandUseCase<FinishGameRoundRequest, Mono<RoundResponse>> {
  private final IEventsRepository roundRepository;

  public FinishGameRoundUseCase(IEventsRepository roundRepository) {
    this.roundRepository = roundRepository;
  }

  @Override
  public Mono<RoundResponse> execute(FinishGameRoundRequest request) {
    return roundRepository.findEventsByAggregateId(request.getAggregateId())
      .collectList()
      .map(events -> {
        Round round = Round.from(request.getAggregateId(), events);
        round.assignRoundWinner(request.getWinnerId(), request.getExtraPoints());
        round.finishRound();
        round.calculateResult();
        round.getUncommittedEvents().forEach(roundRepository::save);
        round.markEventsAsCommitted();

        return mapToRound(round);
      });
  }
}
