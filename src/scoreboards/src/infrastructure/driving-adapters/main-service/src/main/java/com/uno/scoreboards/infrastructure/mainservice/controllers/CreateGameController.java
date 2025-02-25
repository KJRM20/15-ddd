package com.uno.scoreboards.infrastructure.mainservice.controllers;

import com.uno.scoreboards.application.creategame.CreateGameRequest;
import com.uno.scoreboards.application.creategame.CreateGameUseCase;
import com.uno.scoreboards.application.shared.scoreboard.ScoreboardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/uno/create-game")
public class CreateGameController {
  private final CreateGameUseCase createGameUseCase;

  public CreateGameController(CreateGameUseCase createGameUseCase) {
    this.createGameUseCase = createGameUseCase;
  }

  @PostMapping
  public ResponseEntity<Mono<ScoreboardResponse>> execute(@RequestBody CreateGameRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(createGameUseCase.execute(request));
  }
}
