package com.chioaachen.museum.slide.caption;

public final class CaptionEntry {

  private final String fileName;
  private final String photographer;
  private final String description;

  public CaptionEntry(String fileName, String photographer, String description) {
    this.fileName = fileName;
    this.photographer = photographer;
    this.description = description;
  }

  public String getFileName() {
    return fileName;
  }

  public String getPhotographer() {
    return photographer;
  }

  public String getDescription() {
    return description;
  }
}
