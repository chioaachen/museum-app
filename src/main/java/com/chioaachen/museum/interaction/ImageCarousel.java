package com.chioaachen.museum.interaction;

import javafx.scene.image.Image;

import java.util.Collection;

/* package */ final class ImageCarousel implements Carousel<Image> {

  private final Image[] images;
  private int currentIndex;

  /* package */ ImageCarousel(Collection<Image> images) {
    this.images = images.toArray(new Image[0]);
    currentIndex = 0;
  }

  @Override
  public Image next() {
    Image next = images[currentIndex + 1];
    if (next == null) {
      reset();
    }
    return images[++currentIndex];
  }

  @Override
  public Image before() {
    if (currentIndex == 0) {
      currentIndex = images.length - 1;
      return images[currentIndex];
    }
    return images[--currentIndex];
  }

  private void reset() {
    currentIndex = 0;
  }
}
