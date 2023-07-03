package com.example.snakefx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;


public class SnakeMovement {
    private final GridPane gameLayout;
    private Scene gameScene;
    private final int periodOfTime = 800;
    private final Draw drawElement;
    private Snake player;

    public SnakeMovement(Snake player, GridPane gameLayout, Scene gameScene, Draw drawElement){
        this.player = player;
        this.gameLayout = gameLayout;
        this.gameScene = gameScene;
        this.drawElement = drawElement;

        Timeline scheduleMove = new Timeline(new KeyFrame(Duration.millis(periodOfTime), event -> {
            Platform.runLater(() -> {
                drawElement.clearSnakeFromGridPane();
            });
            Pair oldHeadCords = player.getHeadCords();


            Pair tailCords = new Pair(0, 0);
            //Pair appleCords = moveAndDrawSnake(board, oldHeadX, oldHeadY, tailCords.getY(), tailCords.getX());
            //repaintEatenAppleAndIncreaseEatenCounter(board, drawElement, appleCords);
            if (player.getEatenApples() > 0){
                player.increaseLevel();
                player.setEatenZero();
                //serveHeadMove(board, board.getPlayer().getHeadXCord(), board.getPlayer().getHeadYCord());
            }

        }));
        scheduleMove.setCycleCount(Timeline.INDEFINITE);
        scheduleMove.play();

        this.gameScene.setOnKeyPressed(keyEvent -> {
            KeyCode button = keyEvent.getCode();
            Pair tailCords = new Pair(0, 0);
            Pair appleCords = new Pair(-1, -1);
            if (button == KeyCode.UP && player.getDirection() != MoveDirection.DOWN) {
                player.setDirection(MoveDirection.UP);
                //appleCords = moveAndDrawSnake(board, board.getPlayer().getHeadXCord(), board.getPlayer().getHeadYCord(), tailCords.getY(), tailCords.getX());

            } else if (button == KeyCode.DOWN && player.getDirection() != MoveDirection.UP) {
                player.setDirection(MoveDirection.DOWN);
                //appleCords = moveAndDrawSnake(board, board.getPlayer().getHeadXCord(), board.getPlayer().getHeadYCord(), tailCords.getY(), tailCords.getX());

            } else if (button == KeyCode.LEFT && player.getDirection() != MoveDirection.RIGHT) {
                player.setDirection(MoveDirection.LEFT);
                //appleCords = moveAndDrawSnake(board, board.getPlayer().getHeadXCord(), board.getPlayer().getHeadYCord(), tailCords.getY(), tailCords.getX());

            } else if (button == KeyCode.RIGHT && player.getDirection() != MoveDirection.LEFT) {
                player.setDirection(MoveDirection.RIGHT);
                //appleCords = moveAndDrawSnake(board, board.getPlayer().getHeadXCord(), board.getPlayer().getHeadYCord(), tailCords.getY(), tailCords.getX());

            }
            repaintEatenAppleAndIncreaseEatenCounter(drawElement, appleCords);
            if (player.getEatenApples() > 0){
                player.increaseLevel();
                player.setEatenZero();
                //serveHeadMove(board, board.getPlayer().getHeadXCord(), board.getPlayer().getHeadYCord());
            }
        });

    }

    public void repaintEatenAppleAndIncreaseEatenCounter(Draw drawElement, Pair appleCords) {
        Platform.runLater(() -> {
            if (!appleCords.nothingHappened()){
                drawElement.clearAppleFromGridPane();
                drawElement.drawAppleInRandomPlace();
                player.setEatenApples(player.getEatenApples() + 1);
            }
        });
    }

    /*private Pair moveAndDrawSnake(GameBoard board, int oldHeadX, int oldHeadY, int oldTailY, int oldTailX) {
        Platform.runLater(() -> {
            drawElement.clearSnakeFromGridPane();
        });
        moveTail(board, oldTailY, oldTailX);
        Pair appleCords = serveHeadMove(board, oldHeadX, oldHeadY);
        Platform.runLater(() -> {
            drawElement.drawPlayer();
        });

        return appleCords;
    }*/




}
