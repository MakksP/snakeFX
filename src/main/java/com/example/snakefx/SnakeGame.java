package com.example.snakefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class SnakeGame extends Application {

    public static final int WINDOW_WIDTH = 1920;
    public static final int WINDOW_HEIGHT = 1080;


    @Override
    public void start(Stage stage) throws IOException {


        MenuInitializer menuInitializer = new MenuInitializer();
        AnchorPane anchorPane = new AnchorPane(menuInitializer.getBackgroundWallpaper());
        Scene mainScene = new Scene(anchorPane, WINDOW_WIDTH, WINDOW_HEIGHT);


        anchorPane.getChildren().add(menuInitializer.getButtonArea());

        initStage(stage, mainScene);
    }

    private static void initStage(Stage stage, Scene mainScene) {
        stage.setTitle("Snake Game");
        stage.setScene(mainScene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("Aby wyjść z trybu fullscreen kliknij klawisz esc");
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}