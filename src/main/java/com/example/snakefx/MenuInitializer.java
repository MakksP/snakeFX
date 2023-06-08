package com.example.snakefx;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.InputStream;

public class MenuInitializer {

    public static final int WINDOW_WIDTH = 1920;
    public static final int WINDOW_HEIGHT = 1080;

    private final ImageView backgroundWallpaper;
    private final GridPane buttonArea;

    public MenuInitializer(){
        backgroundWallpaper = initBackgroundImage();
        buttonArea = initButtonGridPane();
        addButtonsToButtonArea(buttonArea);
    }

    public static void addButtonsToButtonArea(GridPane buttonArea) {
        Button playButton = new Button("Graj");
        Button exitButton = new Button("Wyjdź");
        buttonArea.add(playButton, 0, 0);
        buttonArea.add(exitButton, 0, 1);
    }

    public static GridPane initButtonGridPane() {
        GridPane buttonArea = new GridPane();
        buttonArea.setVgap(50);
        buttonArea.setLayoutX((double) WINDOW_WIDTH/2);
        buttonArea.setLayoutY((double) WINDOW_HEIGHT/2);
        return buttonArea;
    }

    public ImageView initBackgroundImage() {
        InputStream imageStream = getClass().getResourceAsStream("/snake/snakeMenu.png");
        Image backgroundImage = new Image(imageStream);
        ImageView backgroundWallpaper = new ImageView(backgroundImage);
        return backgroundWallpaper;
    }

    public ImageView getBackgroundWallpaper() {
        return backgroundWallpaper;
    }

    public GridPane getButtonArea(){
        return buttonArea;
    }
}
