package com.chioaachen.museum.util;

public final class FileNameUtil {

  public static String removeExtension(String s) {
    String separator = System.getProperty("file.separator");
    String filename;

    int lastSeparatorIndex = s.lastIndexOf(separator);
    if (lastSeparatorIndex == -1) {
      filename = s;
    } else {
      filename = s.substring(lastSeparatorIndex + 1);
    }

    // Remove the extension.
    int extensionIndex = filename.lastIndexOf(".");
    if (extensionIndex == -1)
      return filename;

    return filename.substring(0, extensionIndex);
  }
}
