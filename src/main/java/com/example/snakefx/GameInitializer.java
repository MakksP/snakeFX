package com.example.snakefx;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameInitializer {
    public static final int MAP_CELL_WIDTH = 48;
    public static final int MAP_CELL_HEIGHT = 54;
    public static final int WINDOW_WIDTH = 1920;
    public static final int WINDOW_HEIGHT = 1080;
    private GameBoard board;
    private Stage mainStage;
    private GridPane gameLayout;
    private final int mapWidth = 40;
    private final int mapHeight = 20;

    public GameInitializer(Stage mainStage){
        board = GameBoard.getInstance();
        this.mainStage = mainStage;
        gameLayout = new GridPane();
        gameLayout.setPrefWidth(WINDOW_WIDTH);
        gameLayout.setPrefHeight(WINDOW_HEIGHT);

        Scene gameScene = new Scene(gameLayout);
        mainStage.setScene(gameScene);
        spawnPlayer();
        drawBackground();
        SnakeMovement movement = new SnakeMovement(board, gameLayout);
        movement.drawPlayer();
        //todo action listeners for keys

    }

    public void spawnPlayer(){
        board.createPlayer();
        board.getGameMap().get(board.getPlayer().getHeadYCord()).set(board.getPlayer().getHeadXCord(), GameElement.SNAKE_HEAD);
        board.getGameMap().get(board.getPlayer().getHeadYCord() + 1).set(board.getPlayer().getHeadXCord(), GameElement.SNAKE_NODE);
        board.getGameMap().get(board.getPlayer().getHeadYCord() + 2).set(board.getPlayer().getHeadXCord(), GameElement.SNAKE_TAIL);
    }

    public void drawBackground(){
        for (int i = 0; i < mapHeight; i++){
            for (int j = 0; j < mapWidth; j++) {
                Rectangle mapCell = new Rectangle(MAP_CELL_WIDTH, MAP_CELL_HEIGHT, Color.BLACK);
                gameLayout.add(mapCell, j, i);
            }
        }
    }



}
