package com.example.snakefx;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class MenuButton extends Button {
    public static final int EXTRA_GAP = 18;
    public static final int DEFAULT_BUTTONS_GAP = 50;
    private final ImageView hoverImage;
    private final ImageView defaultImage;
    private final GridPane buttonArea;
    private ButtonType buttonType;

    public MenuButton(ImageView hoverImage, ImageView defaultImage, GridPane buttonArea, ButtonType buttonType){
        super();
        this.hoverImage = hoverImage;
        this.defaultImage = defaultImage;
        this.buttonArea = buttonArea;
        this.buttonType = buttonType;
        setOnMouseEntered(mouseEvent -> {
            this.setScaleX(1.2);
            this.setScaleY(1.2);
            this.setCursor(Cursor.HAND);
        });

        setOnMouseExited(mouseEvent -> {
            this.setScaleX(1);
            this.setScaleY(1);
        });
        if (buttonType == ButtonType.PLAY){
            setOnMouseClicked(mouseEvent -> {

            });
        } else if (buttonType == ButtonType.EXIT){
            setOnMouseClicked(mouseEvent -> {
                System.exit(0);
            });
        }

    }

    public void setImage(ImageView image){
        this.setGraphic(image);
    }
}
