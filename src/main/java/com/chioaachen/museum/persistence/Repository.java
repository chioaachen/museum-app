package com.chioaachen.museum.persistence;

public interface Repository<IdentifierT, EntityT> {

  void add(IdentifierT identifier, EntityT entity);

  void remove(IdentifierT identifier);

  EntityT findById(IdentifierT identifier);
}
