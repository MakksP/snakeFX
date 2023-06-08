package com.example.snakefx;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.InputStream;

public class MenuInitializer {

    public static final int WINDOW_WIDTH = 1920;
    public static final int WINDOW_HEIGHT = 1080;
    public static final int ADDITIONAL_OFFSET = 60;
    public static final int DEFAULT_BUTTONS_GAP = 50;

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

        MenuButton playButton = new MenuButton(playButtonHoverImage, playButtonDefaultImage, buttonArea, ButtonType.PLAY);
        MenuButton exitButton = new MenuButton(exitButtonHoverImage, exitGameButtonDefaultImage, buttonArea, ButtonType.EXIT);


        setButtonStyle(playButton, playButtonDefaultImage);

        setButtonStyle(exitButton, exitGameButtonDefaultImage);

        buttonArea.add(playButton, 0, 0);
        buttonArea.add(exitButton, 0, 1);
    }

    private static void setButtonStyle(Button button, ImageView buttonImage) {
        button.setGraphic(buttonImage);
        button.setStyle("-fx-background-color: transparent; -fx-border-width: 2px; -fx-border-style: solid; -fx-padding: 0; -fx-border-color: red");
        button.setFocusTraversable(false);
    }

    public static GridPane initButtonGridPane() {
        GridPane buttonArea = new GridPane();
        buttonArea.setVgap(DEFAULT_BUTTONS_GAP);
        buttonArea.setLayoutX((double) WINDOW_WIDTH/2 - ADDITIONAL_OFFSET);
        buttonArea.setLayoutY((double) WINDOW_HEIGHT/2 - ADDITIONAL_OFFSET);
        buttonArea.setPrefWidth(200);
        buttonArea.setAlignment(Pos.CENTER);
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
