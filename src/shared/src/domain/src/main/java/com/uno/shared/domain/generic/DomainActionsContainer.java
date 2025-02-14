package com.uno.shared.domain.generic;

import java.util.Set;
import java.util.function.Consumer;

public abstract class DomainActionsContainer {
  protected Set<Consumer<? super DomainEvent>> actions = new java.util.HashSet<>();

  protected void addAction(final Consumer<? super DomainEvent> consumer) {
    actions.add(consumer);
  }
}
