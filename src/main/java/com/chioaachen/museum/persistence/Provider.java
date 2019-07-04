package com.chioaachen.museum.persistence;

public interface Provider<T> {

  T[] getAll();
}
