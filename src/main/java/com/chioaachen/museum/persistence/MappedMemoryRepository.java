package com.chioaachen.museum.persistence;

import java.util.Map;

public final class MappedMemoryRepository<IdentifierT, EntityT>
  implements Repository<IdentifierT, EntityT> {

  private final Map<IdentifierT, EntityT> delegate;

  public MappedMemoryRepository(Map<IdentifierT, EntityT> delegate) {
    this.delegate = delegate;
  }

  @Override
  public void add(IdentifierT identifier, EntityT entity) {
    delegate.put(identifier, entity);
  }

  @Override
  public void remove(IdentifierT identifier) {
    delegate.remove(identifier);
  }

  @Override
  public EntityT findById(IdentifierT identifier) {
    return delegate.get(identifier);
  }
}
