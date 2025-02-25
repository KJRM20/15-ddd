package com.uno.scoreboards.application.reverthistorytoround;

import com.uno.scoreboards.application.shared.ports.IEventsRepositoryPort;
import com.uno.scoreboards.application.shared.scoreboard.ScoreboardResponse;
import com.uno.scoreboards.domain.round.Round;
import com.uno.scoreboards.domain.round.values.ResultPlayer;
import com.uno.scoreboards.domain.scoreboard.Scoreboard;
import com.uno.shared.application.ICommandUseCase;
import com.uno.shared.domain.generic.DomainEvent;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.uno.scoreboards.application.shared.scoreboard.ScoreboardMapper.mapToScoreboard;

public class RevertHistoryToRoundUseCase implements ICommandUseCase<RevertHistoryToRoundRequest, Mono<ScoreboardResponse>> {
  private final IEventsRepositoryPort repository;

  public RevertHistoryToRoundUseCase(IEventsRepositoryPort scoreboardRepository) {
    this.repository = scoreboardRepository;
  }

  @Override
  public Mono<ScoreboardResponse> execute(RevertHistoryToRoundRequest request) {
    return repository.findEventsByAggregateId(request.getAggregateId())
      .collectList()
      .map(events -> {
        events.sort(Comparator.comparing(DomainEvent::getWhen));
        Scoreboard scoreboard = Scoreboard.from(request.getAggregateId(), events);
        scoreboard.revertHistoryToRound(request.getRoundId());
        scoreboard.getUncommittedEvents().forEach(repository::save);
        scoreboard.markEventsAsCommitted();

        return mapToScoreboard(scoreboard);
      });
  }
}
