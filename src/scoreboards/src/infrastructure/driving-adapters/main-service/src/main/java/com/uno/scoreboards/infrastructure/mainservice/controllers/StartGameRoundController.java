package com.uno.scoreboards.infrastructure.mainservice.controllers;

import com.uno.scoreboards.application.shared.round.RoundResponse;
import com.uno.scoreboards.application.startgameround.StartGameRoundRequest;
import com.uno.scoreboards.application.startgameround.StartGameRoundUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/uno/start-game-round")
public class StartGameRoundController {
  private final StartGameRoundUseCase startGameRoundUseCase;

  public StartGameRoundController(StartGameRoundUseCase startGameRoundUseCase) {
    this.startGameRoundUseCase = startGameRoundUseCase;
  }

  @PostMapping
  public ResponseEntity<Mono<RoundResponse>> execute(@RequestBody StartGameRoundRequest request) {
    return ResponseEntity.status(HttpStatus.OK).body(startGameRoundUseCase.execute(request));
  }
}
