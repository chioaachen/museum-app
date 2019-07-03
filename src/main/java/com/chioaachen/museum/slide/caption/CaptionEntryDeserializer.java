package com.chioaachen.museum.slide.caption;

import com.chioaachen.museum.persistence.MappedMemoryRepository;
import com.chioaachen.museum.persistence.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public final class CaptionEntryDeserializer {

  public static Repository<String, CaptionEntry> deserialize(File file)
      throws FileNotFoundException {
    Repository<String, CaptionEntry> entries = new MappedMemoryRepository<>(new HashMap<>());
    Scanner scanner = new Scanner(new FileInputStream(file));
    while (scanner.hasNext()) {
      String line = scanner.nextLine();
      String[] captionFields = line.split("\\|");
      String fileName = captionFields[0];
      String author = captionFields[1];
      String description = captionFields[2];
      CaptionEntry entry = new CaptionEntry(fileName, author, description);
      entries.add(fileName, entry);
    }
    scanner.close();
    return entries;
  }
}
