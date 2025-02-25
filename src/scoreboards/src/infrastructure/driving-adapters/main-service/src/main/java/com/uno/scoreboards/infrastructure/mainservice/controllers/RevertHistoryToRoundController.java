package com.uno.scoreboards.infrastructure.mainservice.controllers;

import com.uno.scoreboards.application.reverthistorytoround.RevertHistoryToRoundRequest;
import com.uno.scoreboards.application.reverthistorytoround.RevertHistoryToRoundUseCase;
import com.uno.scoreboards.application.shared.scoreboard.ScoreboardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/uno/revert-history-to-round")
public class RevertHistoryToRoundController {
  private final RevertHistoryToRoundUseCase revertHistoryToRoundUseCase;

  public RevertHistoryToRoundController(RevertHistoryToRoundUseCase revertHistoryToRoundUseCase) {
    this.revertHistoryToRoundUseCase = revertHistoryToRoundUseCase;
  }

  @PostMapping
  public ResponseEntity<Mono<ScoreboardResponse>> execute(@RequestBody RevertHistoryToRoundRequest request) {
    return ResponseEntity.status(HttpStatus.OK).body(revertHistoryToRoundUseCase.execute(request));
  }
}
