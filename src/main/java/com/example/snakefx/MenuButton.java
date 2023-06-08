package com.example.snakefx;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class MenuButton extends Button {
    private final ImageView hoverImage;
    private final ImageView defaultImage;

    public MenuButton(ImageView hoverImage, ImageView defaultImage){
        super();
        this.hoverImage = hoverImage;
        this.defaultImage = defaultImage;
        setOnMouseEntered(mouseEvent -> {
            setImage(hoverImage);
        });

        setOnMouseExited(mouseEvent -> {
            setImage(defaultImage);
        });
    }

    public void setImage(ImageView image){
        this.setGraphic(image);
    }
}
