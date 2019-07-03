package com.chioaachen.museum;

import com.chioaachen.museum.slide.FileSlideProvider;
import com.chioaachen.museum.slide.Slide;
import com.chioaachen.museum.slide.SlideCarousel;
import com.chioaachen.museum.util.MediaLoader;
import com.chioaachen.museum.util.ResourceUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;

public final class MuseumApplication extends Application {

  public MuseumApplication() {
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(
      ResourceUtil.getResource("/layouts/app.fxml")
    );

    Scene scene = new Scene(root);

    Button startButton = (Button) scene.lookup("#sap-btn");
    Button backButton = (Button) scene.lookup("#back");
    Button settingsButton = (Button) scene.lookup("#settings");
    Button sapButton = (Button) scene.lookup("#sap-btn");
    Button sliderBackButton = (Button) scene.lookup("#slider-back-btn");
    Button sliderForwardButton = (Button) scene.lookup("#slider-forward-btn");

    ImageView imageView = (ImageView) scene.lookup("#slider-view");
    TextArea captionText = (TextArea) scene.lookup("#caption-text");

    MediaView mediaView = (MediaView) scene.lookup("#media-view");

    File traditionVideo = new File("/MuseumDatenbank/videos/converted/tradition.mp4");
    Media media = MediaLoader.loadMediaFrom(traditionVideo);
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    mediaPlayer.setAutoPlay(true);
    mediaView.setMediaPlayer(mediaPlayer);

    mediaView.setOnError(event -> {
      System.out.println(event.getMediaError().getMessage());
    });

    SlideCarousel slideCarousel = new SlideCarousel(
      new FileSlideProvider("/MuseumDatenbank/potpourri")
    );

    Slide currentSlide = slideCarousel.getCurrentSlide();
    imageView.setImage(currentSlide.getImage());
    captionText.setText(currentSlide.getCaption());

    sliderForwardButton.setOnAction(actionEvent -> {
      slideCarousel.next();
      Slide slide = slideCarousel.getCurrentSlide();
      imageView.setImage(slide.getImage());
      captionText.setText(slide.getCaption());
    });

    sliderBackButton.setOnAction(actionEvent -> {
      slideCarousel.before();
      Slide slide = slideCarousel.getCurrentSlide();
      imageView.setImage(slide.getImage());
      captionText.setText(slide.getCaption());
    });

    primaryStage.setScene(scene);
    primaryStage.setTitle("CHIO Aachen - Museum");
    primaryStage.show();

    if (mediaPlayer.getError() != null) {
      System.out.println(mediaPlayer.getError().toString());
    }
  }
}
