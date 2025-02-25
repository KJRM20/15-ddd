package com.uno.scoreboards.infrastructure.mainservice.controllers;

import com.uno.scoreboards.application.finishgameround.FinishGameRoundRequest;
import com.uno.scoreboards.application.finishgameround.FinishGameRoundUseCase;
import com.uno.scoreboards.application.shared.round.RoundResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/uno/finish-game-round")
public class FinishGameRoundController {
  private final FinishGameRoundUseCase finishGameRoundUseCase;

  public FinishGameRoundController(FinishGameRoundUseCase finishGameRoundUseCase) {
    this.finishGameRoundUseCase = finishGameRoundUseCase;
  }

  @PostMapping
  public ResponseEntity<Mono<RoundResponse>> execute(@RequestBody FinishGameRoundRequest request) {
    return ResponseEntity.status(HttpStatus.OK).body(finishGameRoundUseCase.execute(request));
  }
}
