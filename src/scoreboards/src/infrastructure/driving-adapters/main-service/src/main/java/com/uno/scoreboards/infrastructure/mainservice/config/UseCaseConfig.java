package com.uno.scoreboards.infrastructure.mainservice.config;

import com.uno.scoreboards.application.applystrike.ApplyStrikeUseCase;
import com.uno.scoreboards.application.checkgamestatus.CheckGameStatusUseCase;
import com.uno.scoreboards.application.creategame.CreateGameUseCase;
import com.uno.scoreboards.application.finishgameround.FinishGameRoundUseCase;
import com.uno.scoreboards.application.recordmove.RecordMoveUseCase;
import com.uno.scoreboards.application.reverthistorytoround.RevertHistoryToRoundUseCase;
import com.uno.scoreboards.application.startgameround.StartGameRoundUseCase;
import com.uno.scoreboards.application.updateplayerspoints.UpdatePlayersPointsUseCase;
import com.uno.scoreboards.infrastructure.mongo.adapters.MongoAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
  @Bean
  public CreateGameUseCase createGameUseCase(MongoAdapter adapter) {
    return new CreateGameUseCase(adapter);
  }

  @Bean
  public CheckGameStatusUseCase checkGameStatusUseCase(MongoAdapter adapter) {
    return new CheckGameStatusUseCase(adapter);
  }

  @Bean
  public UpdatePlayersPointsUseCase updatePlayersPointsUseCase(MongoAdapter adapter) {
    return new UpdatePlayersPointsUseCase(adapter);
  }

  @Bean
  public RevertHistoryToRoundUseCase revertHistoryToRoundUseCase(MongoAdapter adapter) {
    return new RevertHistoryToRoundUseCase(adapter);
  }

  @Bean
  public StartGameRoundUseCase startGameRoundUseCase(MongoAdapter adapter) {
    return new StartGameRoundUseCase(adapter);
  }

  @Bean
  public RecordMoveUseCase recordMoveUseCase(MongoAdapter adapter) {
    return new RecordMoveUseCase(adapter);
  }

  @Bean
  public ApplyStrikeUseCase applyStrikeUseCase(MongoAdapter adapter) {
    return new ApplyStrikeUseCase(adapter);
  }

  @Bean
  public FinishGameRoundUseCase finishGameRoundUseCase(MongoAdapter adapter) {
    return new FinishGameRoundUseCase(adapter);
  }
}
