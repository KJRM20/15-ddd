package com.uno.shared.domain.generic;

public abstract class Entity<I extends Identity> {
  private final I identity;

  protected Entity(I identity) {
    this.identity = identity;
  }

  public I getIdentity() {
    return identity;
  }
}
