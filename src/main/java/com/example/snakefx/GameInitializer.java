package com.example.snakefx;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GameInitializer {
    public static final int WINDOW_WIDTH = 1920;
    public static final int WINDOW_HEIGHT = 1080;
    public static final int HEAD_START_X = 20;
    public static final int HEAD_START_Y = 10;
    private Stage mainStage;
    private Scene gameScene;
    private GridPane gameLayout;
    private Draw drawSnake;
    private Snake player;

    public GameInitializer(Stage mainStage){
        this.mainStage = mainStage;
        gameLayout = new GridPane();
        player = new Snake();
        drawSnake = new Draw(gameLayout, player);
        gameLayout.setPrefWidth(WINDOW_WIDTH);
        gameLayout.setPrefHeight(WINDOW_HEIGHT);

        gameScene = new Scene(gameLayout);
        mainStage.setScene(gameScene);
        spawnPlayer();
        drawSnake.drawMapBackground();
        Pair firstAppleCords = drawSnake.drawAppleInRandomPlace(drawSnake.generateAppleRandomCords());
        GameControl movement = new GameControl(player, gameLayout, gameScene, drawSnake, firstAppleCords, this);
        drawSnake.drawPlayer();

    }


    public void spawnPlayer(){
        player.getSnakeElementsWithCords().add(new Pair(HEAD_START_X, HEAD_START_Y));
        player.getSnakeElementsWithCords().add(new Pair(HEAD_START_X, HEAD_START_Y + 1));
        player.getSnakeElementsWithCords().add(new Pair(HEAD_START_X, HEAD_START_Y + 2));
    }

    public void clearGame(){
        mainStage = null;
        gameScene = null;
        gameLayout = null;
        drawSnake = null;
        player = null;
    }

}
