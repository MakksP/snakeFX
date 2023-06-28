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
    public static final int CHECK_HEAD = 1;
    public static final int ELEMENT_DOWN = 1;
    public static final int ELEMENT_UP = 1;
    public static final int ELEMENT_LEFT = 1;
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
        drawPlayer();
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

    public ImageView generateSnakePart(String path){
        Image image = new Image(getClass().getResourceAsStream(path));
        return new ImageView(image);
    }

    public void drawPlayer(){
        MoveDirection direction = board.getPlayer().getDirection();

        ImageView head = generateSnakePart("/snake/snakeHead.png");
        gameLayout.add(head, board.getPlayer().getHeadXCord(), board.getPlayer().getHeadYCord());

        ImageView node = generateSnakePart("/snake/snakeNode.png");

        ImageView tail = generateSnakePart("/snake/snakeTail.png");

        int consideredPartX = board.getPlayer().getHeadXCord();
        int consideredPartY = board.getPlayer().getHeadYCord();

        drawHead(direction, head);
        findNodesTailPositionAndDraw(node, tail, consideredPartX, consideredPartY);

    }

    private void drawHead(MoveDirection direction, ImageView head) {
        if (direction == MoveDirection.UP){
            head.setRotate(270);
        } else if (direction == MoveDirection.LEFT){
            head.setRotate(180);
        } else if (direction == MoveDirection.DOWN){
            head.setRotate(90);
        }
    }


    private void findNodesTailPositionAndDraw(ImageView node, ImageView tail, int consideredPartX, int consideredPartY) {
        ImageView element;
        for (int i = 0; i < board.getPlayer().getLevel() + CHECK_HEAD; i++) {
            if (i < board.getPlayer().getLevel() + CHECK_HEAD - 1){
                element = node;
            } else {
                element = tail;
            }
            if (nodeIsUnder(consideredPartX, consideredPartY)){
                consideredPartY++;
                gameLayout.add(element, consideredPartX, consideredPartY);
                element.setRotate(270);
            } else if (nodeIsAbove(consideredPartX, consideredPartY)){
                consideredPartY--;
                gameLayout.add(element, consideredPartX, consideredPartY);
                element.setRotate(90);
            } else if (nodeIsLeft(consideredPartX, consideredPartY)){
                consideredPartX--;
                gameLayout.add(element, consideredPartX, consideredPartY);
            } else{
                consideredPartX++;
                gameLayout.add(element, consideredPartX, consideredPartY);
                element.setRotate(180);
            }
        }
    }

    private boolean nodeIsLeft(int consideredPartX, int consideredPartY) {
        return board.getGameMap().get(consideredPartY).get(consideredPartX - ELEMENT_LEFT) == GameElement.SNAKE_NODE
                || board.getGameMap().get(consideredPartY).get(consideredPartX - ELEMENT_LEFT) == GameElement.SNAKE_TAIL;
    }

    private boolean nodeIsAbove(int consideredPartX, int consideredPartY) {
        return board.getGameMap().get(consideredPartY - ELEMENT_UP).get(consideredPartX) == GameElement.SNAKE_NODE
                || board.getGameMap().get(consideredPartY - ELEMENT_UP).get(consideredPartX) == GameElement.SNAKE_TAIL;
    }

    private boolean nodeIsUnder(int consideredPartX, int consideredPartY) {
        return board.getGameMap().get(consideredPartY + ELEMENT_DOWN).get(consideredPartX) == GameElement.SNAKE_NODE
                || board.getGameMap().get(consideredPartY + ELEMENT_DOWN).get(consideredPartX) == GameElement.SNAKE_TAIL;
    }

}
