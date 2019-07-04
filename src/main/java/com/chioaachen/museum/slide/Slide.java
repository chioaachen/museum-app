package com.chioaachen.museum.slide;

import javafx.scene.image.Image;

public final class Slide {

  private final Image image;
  private final String caption;

  public Slide(Image image, String caption) {
    this.image = image;
    this.caption = caption;
  }

  public boolean hasCaption() {
    return caption != null;
  }

  public Image getImage() {
    return image;
  }

  public String getCaption() {
    return caption;
  }
}
