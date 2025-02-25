package com.uno.scoreboards.infrastructure.mainservice.controllers;

import com.uno.scoreboards.application.recordmove.RecordMoveRequest;
import com.uno.scoreboards.application.recordmove.RecordMoveUseCase;
import com.uno.scoreboards.application.shared.round.RoundResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/uno/record-move")
public class RecordMoveController {
  private final RecordMoveUseCase recordMoveUseCase;

  public RecordMoveController(RecordMoveUseCase recordMoveUseCase) {
    this.recordMoveUseCase = recordMoveUseCase;
  }

  @PostMapping
  public ResponseEntity<Mono<RoundResponse>> execute(@RequestBody RecordMoveRequest request) {
    return ResponseEntity.status(HttpStatus.OK).body(recordMoveUseCase.execute(request));
  }
}
