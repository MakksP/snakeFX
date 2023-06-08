package com.example.snakefx;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.InputStream;

public class MenuInitializer {

    public static final int WINDOW_WIDTH = 1920;
    public static final int WINDOW_HEIGHT = 1080;
    public static final int ADDITIONAL_OFFSET = 60;

    private final ImageView backgroundWallpaper;
    private final GridPane buttonArea;

    public MenuInitializer(){
        backgroundWallpaper = initImage("/snake/snakeMenu.png");
        buttonArea = initButtonGridPane();
        addButtonsToButtonArea(buttonArea);
    }


    public void addButtonsToButtonArea(GridPane buttonArea) {
        ImageView playButtonDefaultImage = initImage("/snake/playButtonDefault.png");
        ImageView exitGameButtonDefaultImage = initImage("/snake/exitButtonDefault.png");

        ImageView playButtonHoverImage = initImage("/snake/playButtonHover.png");
        ImageView exitButtonHoverImage = initImage("/snake/exitButtonHover.png");

        MenuButton playButton = new MenuButton(playButtonHoverImage, playButtonDefaultImage);
        MenuButton exitButton = new MenuButton(exitButtonHoverImage, exitGameButtonDefaultImage);


        setButtonStyle(playButton, playButtonDefaultImage);


        setButtonStyle(exitButton, exitGameButtonDefaultImage);

        buttonArea.add(playButton, 0, 0);
        buttonArea.add(exitButton, 0, 1);
    }

    private static void setButtonStyle(Button button, ImageView buttonImage) {
        button.setGraphic(buttonImage);
        button.setStyle("-fx-background-color: transparent; -fx-border-width: 2px; -fx-border-style: solid; -fx-padding: 0;");
        button.setFocusTraversable(false);
    }

    public static GridPane initButtonGridPane() {
        GridPane buttonArea = new GridPane();
        buttonArea.setVgap(50);
        buttonArea.setLayoutX((double) WINDOW_WIDTH/2 - ADDITIONAL_OFFSET);
        buttonArea.setLayoutY((double) WINDOW_HEIGHT/2 - ADDITIONAL_OFFSET);
        return buttonArea;
    }

    public ImageView initImage(String path) {
        InputStream imageStream = getClass().getResourceAsStream(path);
        Image Image = new Image(imageStream);
        ImageView imageView = new ImageView(Image);
        return imageView;
    }

    public ImageView getBackgroundWallpaper() {
        return backgroundWallpaper;
    }

    public GridPane getButtonArea(){
        return buttonArea;
    }
}
