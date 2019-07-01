package com.chioaachen.museum.interaction;

import javafx.scene.image.Image;

import java.util.Collection;
import java.util.Objects;

public interface Carousel<T> {

  T next();

  T before();

  static Carousel<Image> of(Collection<Image> images) {
    Objects.requireNonNull(images);
    return new ImageCarousel(images);
  }
}
