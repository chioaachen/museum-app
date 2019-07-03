package com.chioaachen.museum.util;

import javafx.scene.media.Media;

import java.io.File;

public final class MediaLoader {

  private MediaLoader() {
  }

  public static Media loadMediaFrom(File file) {
    return new Media(file.toURI().toString());
  }
}
