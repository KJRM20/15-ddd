package com.uno.scoreboards.application.creategame;

import com.uno.scoreboards.application.shared.ports.IEventsRepositoryPort;
import com.uno.scoreboards.application.shared.scoreboard.ScoreboardResponse;
import com.uno.scoreboards.domain.round.Round;
import com.uno.scoreboards.domain.scoreboard.Scoreboard;
import com.uno.shared.application.ICommandUseCase;
import reactor.core.publisher.Mono;

import static com.uno.scoreboards.application.shared.scoreboard.ScoreboardMapper.mapToScoreboard;

public class CreateGameUseCase implements ICommandUseCase<CreateGameRequest, Mono<ScoreboardResponse>> {
  private final IEventsRepositoryPort repository;

  public CreateGameUseCase(IEventsRepositoryPort repository) {
    this.repository = repository;
  }

  @Override
  public Mono<ScoreboardResponse> execute(CreateGameRequest request) {
    Scoreboard scoreboard = new Scoreboard();
    request.getPlayers().forEach(scoreboard::addPlayer);
    Round round = new Round();
    scoreboard.addRoundToHistory(round.getIdentity().getValue());
    scoreboard.getUncommittedEvents().forEach(repository::save);
    round.getUncommittedEvents().forEach(repository::save);
    scoreboard.markEventsAsCommitted();
    round.markEventsAsCommitted();

    return Mono.just(mapToScoreboard(scoreboard));
  }
}
