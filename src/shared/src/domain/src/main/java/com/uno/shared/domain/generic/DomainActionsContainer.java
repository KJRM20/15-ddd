package com.uno.shared.domain.generic;

import java.util.Set;
import java.util.function.Consumer;

public abstract class DomainActionsContainer {
  protected Set<Consumer<? super DomainEvent>> actions = new java.util.HashSet<>();

  protected void add(final Consumer<? extends DomainEvent> consumer) {
    actions.add((Consumer<? super DomainEvent>)consumer);
  }
}
