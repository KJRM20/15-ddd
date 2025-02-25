package com.uno.scoreboards.infrastructure.mainservice.controllers;

import com.uno.scoreboards.application.shared.scoreboard.ScoreboardResponse;
import com.uno.scoreboards.application.updateplayerspoints.UpdatePlayersPointsRequest;
import com.uno.scoreboards.application.updateplayerspoints.UpdatePlayersPointsUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/uno/update-player-points")
public class UpdatePlayerPointsController {
  private final UpdatePlayersPointsUseCase updatePlayersPointsUseCase;

  public UpdatePlayerPointsController(UpdatePlayersPointsUseCase updatePlayersPointsUseCase) {
    this.updatePlayersPointsUseCase = updatePlayersPointsUseCase;
  }

  @PostMapping
  public ResponseEntity<Mono<ScoreboardResponse>> execute(@RequestBody UpdatePlayersPointsRequest request) {
    return ResponseEntity.status(HttpStatus.OK).body(updatePlayersPointsUseCase.execute(request));
  }
}
