package com.uno.scoreboards.application.checkgamestatus;

import com.uno.scoreboards.application.shared.repositories.IEventsRepository;
import com.uno.scoreboards.application.shared.scoreboard.ScoreboardResponse;
import com.uno.scoreboards.domain.scoreboard.Scoreboard;
import com.uno.shared.application.ICommandUseCase;
import reactor.core.publisher.Mono;

import static com.uno.scoreboards.application.shared.scoreboard.ScoreboardMapper.mapToScoreboard;

public class CheckGameStatusUseCase implements ICommandUseCase<CheckGameStatusRequest, Mono<ScoreboardResponse>> {
  private final IEventsRepository scoreboardRepository;

  public CheckGameStatusUseCase(IEventsRepository scoreboardRepository) {
    this.scoreboardRepository = scoreboardRepository;
  }

  @Override
  public Mono<ScoreboardResponse> execute(CheckGameStatusRequest request) {
    return scoreboardRepository.findEventsByAggregateId(request.getAggregateId())
      .collectList()
      .map(events -> {
        Scoreboard scoreboard = Scoreboard.from(request.getAggregateId(), events);
        scoreboard.getPlayers().keySet().forEach(playerId -> scoreboard.reachPlayerTargetScore(playerId.getValue(), request.getTargetScore()));
        scoreboard.getPlayers().values().stream()
          .filter(player -> player.getIsWinner().getValue())
          .findFirst().ifPresent(player -> scoreboard.lockScoreboard());
        scoreboard.getUncommittedEvents().forEach(scoreboardRepository::save);
        scoreboard.markEventsAsCommitted();

        return mapToScoreboard(scoreboard);
      });
  }
}
