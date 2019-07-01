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

  public static List<File> getFilesFromFolder(File folder) {
    List<File> files = new ArrayList<>();
    File[] readFiles = folder.listFiles();
    if (readFiles == null) {
      System.out.println("no files found in directory");
      return files;
    }
    for (final File file : readFiles) {
      if (file.isDirectory()) {
        files.addAll(getFilesFromFolder(file));
      } else {
        System.out.println("File added");
        files.add(file);
      }
    }
    return files;
  }

  public static List<Image> getImagesFrom(String uri) {
    List<Image> images = new ArrayList<>();
    for (final File file : getFilesFromFolder(new File(uri))) {
      try {
        Image image = new Image(new FileInputStream(file));
        images.add(image);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
    return images;
  }

  private static class Lazy {
    private static final ResourceUtil INSTANCE = new ResourceUtil();
  }
}
