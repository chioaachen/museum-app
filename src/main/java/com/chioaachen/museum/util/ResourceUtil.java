package com.chioaachen.museum.util;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class ResourceUtil {

  private ResourceUtil() {
  }

  private URL getResourceDelegate(String uri) {
    return getClass().getResource(uri);
  }

  public static URL getResource(String uri) {
    return Lazy.INSTANCE.getResourceDelegate(uri);
  }

  private static class Lazy {
    private static final ResourceUtil INSTANCE = new ResourceUtil();
  }
}
