package com.uno.scoreboards.application.updateplayerspoints;

import com.uno.scoreboards.application.shared.repositories.IEventsRepository;
import com.uno.scoreboards.application.shared.scoreboard.ScoreboardResponse;
import com.uno.scoreboards.domain.round.Round;
import com.uno.scoreboards.domain.scoreboard.Scoreboard;
import com.uno.shared.application.ICommandUseCase;
import reactor.core.publisher.Mono;

import static com.uno.scoreboards.application.shared.scoreboard.ScoreboardMapper.mapToScoreboard;

public class UpdatePlayersPointsUseCase implements ICommandUseCase<UpdatePlayersPointsRequest, Mono<ScoreboardResponse>> {
  private final IEventsRepository scoreboardRepository;
  private final IEventsRepository roundRepository;

  public UpdatePlayersPointsUseCase(IEventsRepository scoreboardRepository, IEventsRepository roundRepository) {
    this.scoreboardRepository = scoreboardRepository;
    this.roundRepository = roundRepository;
  }

  @Override
  public Mono<ScoreboardResponse> execute(UpdatePlayersPointsRequest request) {
    return scoreboardRepository.findEventsByAggregateId(request.getAggregateId())
      .collectList()
      .flatMap(events -> {
        Scoreboard scoreboard = Scoreboard.from(request.getAggregateId(), events);

        return roundRepository.findEventsByAggregateId(request.getRoundId())
          .collectList()
          .map(roundEvents -> {
            Round round = Round.from(request.getRoundId(), roundEvents);
            scoreboard.getPlayers().keySet()
              .forEach(playerId -> round.getResult()
                .getResultPlayers().stream()
                .filter(resultPlayer -> resultPlayer.getPlayerId().getValue().equals(playerId.getValue()))
                .findFirst()
                .ifPresent(resultPlayer -> scoreboard.updatePlayerPoints(
                  playerId.getValue(), resultPlayer.getTotalPoints().getValue())));

            scoreboard.getUncommittedEvents().forEach(scoreboardRepository::save);
            scoreboard.markEventsAsCommitted();

            return mapToScoreboard(scoreboard);
          });
      });
  }
}
