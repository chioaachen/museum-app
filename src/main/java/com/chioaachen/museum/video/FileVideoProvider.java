package com.chioaachen.museum.video;

import com.chioaachen.museum.persistence.Provider;
import com.chioaachen.museum.util.FileLoader;
import com.chioaachen.museum.util.FileNameUtil;
import com.chioaachen.museum.util.ImageLoader;
import com.chioaachen.museum.util.MediaLoader;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public final class FileVideoProvider implements Provider<Video> {

  private final static String PREVIEW_PATH_SUFFIX = "/previews/";

  private final Video[] videos;

  public FileVideoProvider(String path) {
    File directory = new File(path);
    File[] videoFiles = FileLoader.loadFilesFrom(directory);
    File previewDirectory = new File(path + PREVIEW_PATH_SUFFIX);
    File[] previewFiles = FileLoader.loadFilesFrom(previewDirectory);

    List<Video> videoList = new ArrayList<>();

    for (final File videoFile : videoFiles) {
      Media media = MediaLoader.loadMediaFrom(videoFile);
      Image preview = null;
      String previewFileName = FileNameUtil.removeExtension(videoFile.getName());

      for (final File previewFile : previewFiles) {
        if (previewFile.getName().startsWith(previewFileName)) {
          try {
            preview = ImageLoader.loadImageFrom(previewFile);
          } catch (FileNotFoundException e) {
            // ignore
          }
        }
      }

      Video video = new Video(preview, media);
      videoList.add(video);
    }

    this.videos = videoList.toArray(new Video[0]);
  }

  @Override
  public Video[] getAll() {
    return videos;
  }
}
