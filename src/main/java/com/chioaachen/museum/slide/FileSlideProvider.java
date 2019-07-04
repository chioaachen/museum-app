package com.chioaachen.museum.slide;

import com.chioaachen.museum.persistence.Provider;
import com.chioaachen.museum.persistence.Repository;
import com.chioaachen.museum.slide.caption.CaptionEntry;
import com.chioaachen.museum.slide.caption.CaptionEntryDeserializer;
import com.chioaachen.museum.util.FileLoader;
import com.chioaachen.museum.util.ImageLoader;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class FileSlideProvider implements Provider<Slide> {

  private static final String CAPTION_PATH_SUFFIX = "/captions/";
  private static final String NO_CAPTION = "Kein Untertitel";

  private final Slide[] slides;

  public FileSlideProvider(String path) {
    File directory = new File(path);
    File[] imageFiles = FileLoader.loadFilesFrom(directory);
    File captionFile = new File(path + CAPTION_PATH_SUFFIX + "captions.txt");

    List<Slide> slideList = new ArrayList<>();
    Repository<String, CaptionEntry> captions = null;

    try {
      captions = CaptionEntryDeserializer.deserialize(captionFile);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    for (final File imageFile : imageFiles) {
      try {
        Image image = ImageLoader.loadImageFrom(imageFile);
        Slide slide;

        if (captions == null) {
          slide = new Slide(image, NO_CAPTION);
          slideList.add(slide);
          continue;
        }

        CaptionEntry caption = captions.findById(imageFile.getName());

        if (caption == null) {
          slide = new Slide(image, NO_CAPTION);
          slideList.add(slide);
          continue;
        }

        String description = caption.getDescription();
        String photographer = caption.getPhotographer();
        String photoAppendix = photographer.isEmpty() ? "" : "; Foto: " + photographer;
        String captionText = caption.getDescription() + photoAppendix;

        slide = new Slide(image, captionText);
        slideList.add(slide);
      } catch (FileNotFoundException e) {
        // ignore
      }
    }
    slides = slideList.toArray(new Slide[0]);
  }

  @Override
  public Slide[] getAll() {
    return slides;
  }
}
