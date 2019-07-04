package com.chioaachen.museum.video;

import javafx.scene.image.Image;
import javafx.scene.media.Media;

public final class Video {

  private final Image preview;
  private final Media media;

  public Video(Image preview, Media media) {
    this.preview = preview;
    this.media = media;
  }

  public boolean hasPreviewImage() {
    return preview != null;
  }

  public Image getPreview() {
    return preview;
  }

  public Media getMedia() {
    return media;
  }
}
