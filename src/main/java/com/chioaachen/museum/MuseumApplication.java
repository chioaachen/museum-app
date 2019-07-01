package com.chioaachen.museum;

import com.chioaachen.museum.interaction.Carousel;
import com.chioaachen.museum.util.ResourceUtil;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public final class MuseumApplication extends Application {

  public MuseumApplication() {
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(
      ResourceUtil.getResource("/layouts/app.fxml")
    );

    ObservableList<String> stylesheets = root.getStylesheets();
    stylesheets.addAll(
      ResourceUtil.getResource("/styles/style.css")
        .toExternalForm()
    );

    Scene scene = new Scene(root);

    Button startButton = (Button) scene.lookup("#sap-btn");
    Button backButton = (Button) scene.lookup("#back");
    Button settingsButton = (Button) scene.lookup("#settings");
    Button sapButton = (Button) scene.lookup("#sap-btn");
    Button imageBackButton = (Button) scene.lookup("#slider-back-btn");
    Button imageForwardButton = (Button) scene.lookup("#slider-forward-btn");

    ImageView imageView = (ImageView) scene.lookup("#image-view");
    MediaView mediaView = (MediaView) scene.lookup("#media-view");

    Carousel<Image> imageCarousel = Carousel.of(
      ResourceUtil.getImagesFrom("C:\\Users\\klassen\\IdeaProjects\\museum-app\\src\\main\\resources\\potpourri")
    );
    imageForwardButton.setOnAction(actionEvent -> imageView.setImage(imageCarousel.next()));
    imageBackButton.setOnAction(actionEvent -> imageView.setImage(imageCarousel.before()));

    primaryStage.setScene(scene);
    primaryStage.setTitle("CHIO Aachen - Museum");
    primaryStage.show();
  }
}
