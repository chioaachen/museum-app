package com.chioaachen.museum;

import javafx.application.Application;

public final class ApplicationRunner {

  private ApplicationRunner() {
  }

  public static void main(String... arguments) {
    Application.launch(MuseumApplication.class, arguments);
  }
}
