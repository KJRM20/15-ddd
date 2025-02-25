package com.uno.scoreboards.infrastructure.mainservice.controllers;

import com.uno.scoreboards.application.checkgamestatus.CheckGameStatusRequest;
import com.uno.scoreboards.application.checkgamestatus.CheckGameStatusUseCase;
import com.uno.scoreboards.application.shared.scoreboard.ScoreboardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/uno/check-game-status")
public class CheckGameStatusController {
  private final CheckGameStatusUseCase checkGameStatusUseCase;

  public CheckGameStatusController(CheckGameStatusUseCase checkGameStatusUseCase) {
    this.checkGameStatusUseCase = checkGameStatusUseCase;
  }

  @PostMapping
  public ResponseEntity<Mono<ScoreboardResponse>> execute(@RequestBody CheckGameStatusRequest request) {
    return ResponseEntity.status(HttpStatus.OK).body(checkGameStatusUseCase.execute(request));
  }
}
