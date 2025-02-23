package com.uno.scoreboards.application.creategame;

import com.uno.scoreboards.application.shared.repositories.IEventsRepository;
import com.uno.scoreboards.application.shared.scoreboard.ScoreboardResponse;
import com.uno.scoreboards.domain.round.Round;
import com.uno.scoreboards.domain.scoreboard.Scoreboard;
import com.uno.shared.application.ICommandUseCase;
import reactor.core.publisher.Mono;

import static com.uno.scoreboards.application.shared.scoreboard.ScoreboardMapper.mapToScoreboard;

public class CreateGameUseCase implements ICommandUseCase<CreateGameRequest, Mono<ScoreboardResponse>> {
  private final IEventsRepository scoreboardRepository;
  private final IEventsRepository roundRepository;

  public CreateGameUseCase(IEventsRepository scoreboardRepository, IEventsRepository roundRepository) {
    this.scoreboardRepository = scoreboardRepository;
    this.roundRepository = roundRepository;
  }

  @Override
  public Mono<ScoreboardResponse> execute(CreateGameRequest request) {
    Scoreboard scoreboard = new Scoreboard();
    request.getPlayers().forEach(scoreboard::addPlayer);
    Round round = new Round();
    scoreboard.addRoundToHistory(round.getIdentity().getValue());

    scoreboard.getUncommittedEvents().forEach(scoreboardRepository::save);
    round.getUncommittedEvents().forEach(roundRepository::save);
    scoreboard.markEventsAsCommitted();
    round.markEventsAsCommitted();

    return Mono.just(mapToScoreboard(scoreboard));
  }
}
