package com.uno.scoreboards.infrastructure.mongo.repositories;

import com.uno.scoreboards.infrastructure.mongo.entities.Event;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IEventsRepository extends ReactiveMongoRepository<Event, String> {
}
