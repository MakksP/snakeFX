package com.example.snakefx;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class LostInfoLabel extends Label {
    public static final int WINDOW_WIDTH = 1920;
    public static final int WINDOW_HEIGHT = 1080;
    public static final int LOST_LABEL_WIDTH = 960;
    public static final int LOST_LABEL_HEIGHT = 540;

    private Draw drawAPI;

    public LostInfoLabel(Draw draw){
        super();
        drawAPI = draw;
        ImageView lostLabelBackground = draw.generateImage("/snake/LostLabelImage.png");
        this.setGraphic(lostLabelBackground);
        this.prefWidth((double) WINDOW_WIDTH / 2);
        this.prefHeight((double) WINDOW_HEIGHT / 2);
    }

    public void addLostLabelToGameScene (GridPane gameLayout){
        int lostPaneWidth = LOST_LABEL_WIDTH / Draw.MAP_CELL_WIDTH;
        int lostPaneHeight = LOST_LABEL_HEIGHT / Draw.MAP_CELL_HEIGHT;
        gameLayout.add(this, (drawAPI.MAP_WIDTH / 2) - (lostPaneWidth / 2), (drawAPI.MAP_HEIGHT / 2) - (lostPaneHeight / 2), lostPaneWidth, lostPaneHeight);
    }
}
