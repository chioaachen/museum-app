package com.chioaachen.museum;

import com.chioaachen.museum.persistence.Provider;
import com.chioaachen.museum.slide.FileSlideProvider;
import com.chioaachen.museum.slide.Slide;
import com.chioaachen.museum.slide.SlideCarousel;
import com.chioaachen.museum.util.ResourceUtil;
import com.chioaachen.museum.video.FileVideoProvider;

import com.chioaachen.museum.video.Video;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

public final class MuseumApplication extends Application {

  public MuseumApplication() {
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Scene scene = FXMLLoader.load(
      ResourceUtil.getResource("/layouts/app.fxml")
    );
    GridPane potpourri = FXMLLoader.load(
      ResourceUtil.getResource("/layouts/potpourri.fxml")
    );
    GridPane videoChooser = FXMLLoader.load(
      ResourceUtil.getResource("/layouts/video-chooser.fxml")
    );
    MediaView videoViewer = FXMLLoader.load(
      ResourceUtil.getResource("/layouts/video-viewer.fxml")
    );

    GridPane container = (GridPane) scene.lookup("#container");

    container.add(videoChooser, 0, 0);
    container.add(potpourri, 1, 0);

    GridPane.setConstraints(videoChooser, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER);
    GridPane.setConstraints(videoViewer, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER);
    GridPane.setConstraints(potpourri, 1, 0, 1, 1, HPos.CENTER, VPos.CENTER);

    Button startButton = (Button) scene.lookup("#start-btn");
    Button backButton = (Button) scene.lookup("#back-btn");
    Button settingsButton = (Button) scene.lookup("#settings");
    Button sapButton = (Button) scene.lookup("#sap-btn");
    Button sliderBackButton = (Button) scene.lookup("#slider-back-btn");
    Button sliderForwardButton = (Button) scene.lookup("#slider-forward-btn");

    ImageView sliderView = (ImageView) scene.lookup("#slider-view");
    TextArea captionText = (TextArea) scene.lookup("#caption-text");

    ObservableList<Node> children = container.getChildren();

    SlideCarousel slideCarousel = new SlideCarousel(
      new FileSlideProvider("/MuseumDatenbank/potpourri")
    );

    Slide currentSlide = slideCarousel.getCurrentSlide();
    sliderView.setImage(currentSlide.getImage());
    captionText.setText(currentSlide.getCaption());

    ScheduledService<Void> timedSlider = new ScheduledService<>() {
      @Override
      protected Task<Void> createTask() {
        return new Task<>() {
          @Override
          protected Void call() {
            slideCarousel.next();
            Slide slide = slideCarousel.getCurrentSlide();
            sliderView.setImage(slide.getImage());
            captionText.setText(slide.getCaption());
            return null;
          }
        };
      }
    };
    timedSlider.setPeriod(Duration.seconds(10));
    timedSlider.start();

    sliderForwardButton.setOnAction(actionEvent -> {
      slideCarousel.next();
      Slide slide = slideCarousel.getCurrentSlide();
      sliderView.setImage(slide.getImage());
      captionText.setText(slide.getCaption());
    });

    sliderBackButton.setOnAction(actionEvent -> {
      slideCarousel.before();
      Slide slide = slideCarousel.getCurrentSlide();
      sliderView.setImage(slide.getImage());
      captionText.setText(slide.getCaption());
    });

    Provider<Video> videoProvider = new FileVideoProvider("/MuseumDatenbank/videos");
    Video[] videos = videoProvider.getAll();
    int rowIndex = 0;
    int columnIndex = 0;

    for (final Video current : videos) {
      if (rowIndex % 2 == 0 && rowIndex != 0) {
        rowIndex = 0;
        columnIndex++;
      }

      ImageView preview = new ImageView(current.getPreviewImage());
      preview.setFitHeight(50);
      preview.setFitWidth(50);
      Button videoButton;

      if (current.hasPreviewImage()) {
        videoButton = new Button(current.getTitle(), preview);
      } else {
        videoButton = new Button(current.getTitle());
      }

      videoButton.setId("btn");
      videoButton.setPrefWidth(200);

      videoButton.setOnAction(event -> {
        replaceAt(container, 0, 0, videoChooser, videoViewer);

        MediaPlayer mediaPlayer = new MediaPlayer(current.getMedia());
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(() -> replaceAt(container, 0, 0, videoViewer, videoChooser));

        backButton.setOnAction(e -> {
          if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
          }

          if (children.contains(videoChooser)) {
            replaceAt(container, 0, 0, videoChooser, videoViewer);
          } else {
            replaceAt(container, 0, 0, videoViewer, videoChooser);
          }

          backButton.setOnAction(null);
        });

        videoViewer.setMediaPlayer(mediaPlayer);
      });

      GridPane.setConstraints(videoButton, rowIndex, columnIndex, 1, 1, HPos.CENTER, VPos.CENTER);

      videoChooser.getChildren()
          .add(videoButton);
      rowIndex++;
    }

    primaryStage.setResizable(false);
    primaryStage.setAlwaysOnTop(true);
    primaryStage.setFullScreen(true);
    primaryStage.setScene(scene);
    primaryStage.setTitle("CHIO Aachen - Museum");
    primaryStage.show();
  }

  private static void replaceAt(GridPane container, int column, int row, Node nodeToReplace, Node newNode) {
    ObservableList<Node> children = container.getChildren();
    children.remove(nodeToReplace);
    container.add(newNode, column, row);
  }
}
