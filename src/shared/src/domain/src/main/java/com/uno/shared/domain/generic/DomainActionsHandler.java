package com.uno.shared.domain.generic;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class DomainActionsHandler {
  private final List<DomainEvent> events = new LinkedList<>();
  private final Map<String, AtomicLong> versions = new ConcurrentHashMap<>();
  private final Set<Consumer<? super DomainEvent>> actions = new HashSet<>();

  public List<DomainEvent> getEvents() {
    return events;
  }

  public void subscribe(final DomainActionsContainer actionsContainer) {
    actions.addAll(actionsContainer.actions);
  }

  public void apply(final DomainEvent event) {
    events.add(event);
    actions.forEach(action -> handle(event, action));
  }

  public void handle(final DomainEvent event, final Consumer<? super DomainEvent> consumer) {
    try {
      consumer.accept(event);
      final long newVersion = increaseVersion(event);
      event.setVersion(newVersion);
    } catch (ClassCastException ignored) { }
  }

  private long increaseVersion(final DomainEvent event) {
    final AtomicLong version = versions.get(event.getName());
    final long newVersion = version == null ? event.getVersion() : version.incrementAndGet();

    versions.put(event.getName(), new AtomicLong(newVersion));
    return newVersion;
  }
}
