package com.chioaachen.museum.slide;

public final class SlideCarousel {

  private final Slide[] slides;
  private int index;

  public SlideCarousel(SlideProvider slideProvider) {
    this.slides = slideProvider.getSlides();
    reset();
  }

  public void next() {
    if ((index + 1) == slides.length) {
      reset();
    } else {
      index++;
    }
  }

  public void before() {
    if (index == 0) {
      index = slides.length - 1;
    } else {
      index--;
    }
  }

  private void reset() {
    index = 0;
  }

  public Slide getCurrentSlide() {
    return slides[index];
  }
}
