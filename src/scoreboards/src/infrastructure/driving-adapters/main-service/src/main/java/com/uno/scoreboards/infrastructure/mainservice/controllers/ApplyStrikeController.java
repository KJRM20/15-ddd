package com.uno.scoreboards.infrastructure.mainservice.controllers;

import com.uno.scoreboards.application.applystrike.ApplyStrikeRequest;
import com.uno.scoreboards.application.applystrike.ApplyStrikeUseCase;
import com.uno.scoreboards.application.shared.round.RoundResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/uno/apply-strike")
public class ApplyStrikeController {
  private final ApplyStrikeUseCase applyStrikeUseCase;

  public ApplyStrikeController(ApplyStrikeUseCase applyStrikeUseCase) {
    this.applyStrikeUseCase = applyStrikeUseCase;
  }

  @PostMapping
  public ResponseEntity<Mono<RoundResponse>> execute(@RequestBody ApplyStrikeRequest request) {
    return ResponseEntity.status(HttpStatus.OK).body(applyStrikeUseCase.execute(request));
  }
}
