package com.uno.scoreboards.application.reverthistorytoround;

import com.uno.scoreboards.application.shared.repositories.IEventsRepository;
import com.uno.scoreboards.application.shared.scoreboard.ScoreboardResponse;
import com.uno.scoreboards.domain.scoreboard.Scoreboard;
import com.uno.shared.application.ICommandUseCase;
import reactor.core.publisher.Mono;

import static com.uno.scoreboards.application.shared.scoreboard.ScoreboardMapper.mapToScoreboard;

public class RevertHistoryToRoundUseCase implements ICommandUseCase<RevertHistoryToRoundRequest, Mono<ScoreboardResponse>> {
  private final IEventsRepository scoreboardRepository;

  public RevertHistoryToRoundUseCase(IEventsRepository scoreboardRepository) {
    this.scoreboardRepository = scoreboardRepository;
  }

  @Override
  public Mono<ScoreboardResponse> execute(RevertHistoryToRoundRequest request) {
    return scoreboardRepository.findEventsByAggregateId(request.getAggregateId())
      .collectList()
      .map(events -> {
        Scoreboard scoreboard = Scoreboard.from(request.getAggregateId(), events);
        scoreboard.revertHistoryToRound(request.getRoundId());
        scoreboard.getUncommittedEvents().forEach(scoreboardRepository::save);
        scoreboard.markEventsAsCommitted();

        return mapToScoreboard(scoreboard);
      });
  }
}
