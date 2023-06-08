package com.example.snakefx;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class MenuButton extends Button {
    public static final int EXTRA_GAP = 18;
    public static final int DEFAULT_BUTTONS_GAP = 50;
    public static final int MOVE_PANEL_LEFT = 20;
    private final ImageView hoverImage;
    private final ImageView defaultImage;
    private final GridPane buttonArea;

    public MenuButton(ImageView hoverImage, ImageView defaultImage, GridPane buttonArea){
        super();
        this.hoverImage = hoverImage;
        this.defaultImage = defaultImage;
        this.buttonArea = buttonArea;
        setOnMouseEntered(mouseEvent -> {
            setImage(hoverImage);
            buttonArea.setVgap(buttonArea.getVgap() - EXTRA_GAP);
        });

        setOnMouseExited(mouseEvent -> {
            setImage(defaultImage);
            buttonArea.setVgap(DEFAULT_BUTTONS_GAP);
        });
    }

    public void setImage(ImageView image){
        this.setGraphic(image);
    }
}
