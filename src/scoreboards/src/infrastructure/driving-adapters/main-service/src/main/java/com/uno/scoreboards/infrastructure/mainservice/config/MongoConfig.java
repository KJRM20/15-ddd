package com.uno.scoreboards.infrastructure.mainservice.config;

import com.uno.scoreboards.infrastructure.mongo.adapters.MongoAdapter;
import com.uno.scoreboards.infrastructure.mongo.repositories.IEventsRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EntityScan(basePackages = "com.uno.scoreboards.infrastructure.mongo.entities")
@EnableReactiveMongoRepositories(basePackages = "com.uno.scoreboards.infrastructure.mongo.repositories")
public class MongoConfig {
  @Bean
  public MongoAdapter mongoAdapter(IEventsRepository repository) {
    return new MongoAdapter(repository);
  }
}
