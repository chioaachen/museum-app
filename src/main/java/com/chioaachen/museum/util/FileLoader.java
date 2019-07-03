package com.chioaachen.museum.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class FileLoader {

  private static final String EXCLUSION_PATTERN = "!";

  private FileLoader() {
  }

  public static File[] loadFilesFrom(File directory) {
    List<File> files = new ArrayList<>();
    File[] readFiles = directory.listFiles();
    if (readFiles == null) {
      return files.toArray(new File[0]);
    }
    for (final File file : readFiles) {
      String fileName = file.getName();
      if (!file.isDirectory() && !fileName.startsWith(EXCLUSION_PATTERN)) {
        files.add(file);
      }
    }
    return files.toArray(new File[0]);
  }
}
