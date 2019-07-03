package com.chioaachen.museum.util;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public final class ImageLoader {

  private ImageLoader() {
  }

  public static Image loadImageFrom(File file) throws FileNotFoundException {
    return new Image(new FileInputStream(file));
  }
}
