package com.chioaachen.museum.video;

import javafx.scene.image.Image;
import javafx.scene.media.Media;

public final class Video {

  private final String title;
  private final Image previewImage;
  private final Media media;

  public Video(String title, Image previewImage, Media media) {
    this.title = title;
    this.previewImage = previewImage;
    this.media = media;
  }

  public boolean hasPreviewImage() {
    return previewImage != null;
  }

  public String getTitle() {
    return title;
  }

  public Image getPreviewImage() {
    return previewImage;
  }

  public Media getMedia() {
    return media;
  }
}
