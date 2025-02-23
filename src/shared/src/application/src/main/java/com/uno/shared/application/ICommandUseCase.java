package com.uno.shared.application;

public interface ICommandUseCase<T extends Request, R> {
  R execute(T request);
}

// Command An Query Responsibility Segregation (CQRS) -> EDA (Event Driven Architecture)
