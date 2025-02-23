package com.uno.scoreboards.application.startgameround;

import com.uno.scoreboards.application.shared.repositories.IEventsRepository;
import com.uno.scoreboards.application.shared.round.RoundResponse;
import com.uno.scoreboards.domain.round.Round;
import com.uno.scoreboards.domain.scoreboard.Scoreboard;
import com.uno.shared.application.ICommandUseCase;
import reactor.core.publisher.Mono;

import static com.uno.scoreboards.application.shared.round.RoundMapper.mapToRound;

public class StartGameRoundUseCase implements ICommandUseCase<StartGameRoundRequest, Mono<RoundResponse>> {
  private final IEventsRepository scoreboardRepository;
  private final IEventsRepository roundRepository;

  public StartGameRoundUseCase(IEventsRepository scoreboardRepository, IEventsRepository roundRepository) {
    this.scoreboardRepository = scoreboardRepository;
    this.roundRepository = roundRepository;
  }

  @Override
  public Mono<RoundResponse> execute(StartGameRoundRequest request) {
    return scoreboardRepository.findEventsByAggregateId(request.getAggregateId())
      .collectList()
      .map(events -> {
        Scoreboard scoreboard = Scoreboard.from(request.getAggregateId(), events);
        Round round = new Round();
        scoreboard.addRoundToHistory(round.getIdentity().getValue());
        scoreboard.getUncommittedEvents().forEach(scoreboardRepository::save);
        round.getUncommittedEvents().forEach(roundRepository::save);
        scoreboard.markEventsAsCommitted();
        round.markEventsAsCommitted();

        return mapToRound(round);
      });
  }
}
