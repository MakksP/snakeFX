package com.example.snakefx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;


public class SnakeMovement {
    private final GameBoard board;
    private final GridPane gameLayout;
    private Scene gameScene;
    private final int periodOfTime = 800;
    private final Checker snakeElementsChecker;
    private final Draw drawSnake;

    public SnakeMovement(GameBoard board, GridPane gameLayout, Scene gameScene, Checker snakeElementsChecker, Draw drawSnake){
        this.board = board;
        this.gameLayout = gameLayout;
        this.gameScene = gameScene;
        this.snakeElementsChecker = snakeElementsChecker;
        this.drawSnake = drawSnake;

        Timeline scheduleMove = new Timeline(new KeyFrame(Duration.millis(periodOfTime), event -> {
            Platform.runLater(() -> {
                drawSnake.clearSnakeFromGridPane();
            });
            int oldHeadX = board.getPlayer().getHeadXCord();
            int oldHeadY = board.getPlayer().getHeadYCord();

            Pair tailCords = new Pair(0, 0);
            snakeElementsChecker.findTailCords(board, tailCords);
            moveAndDraw(board, oldHeadX, oldHeadY, tailCords.getY(), tailCords.getX());

        }));
        scheduleMove.setCycleCount(Timeline.INDEFINITE);
        scheduleMove.play();

        this.gameScene.setOnKeyPressed(keyEvent -> {
            KeyCode button = keyEvent.getCode();
            Snake player = board.getPlayer();
            Pair tailCords = new Pair(0, 0);
            snakeElementsChecker.findTailCords(board, tailCords);
            if (button == KeyCode.UP && player.getDirection() != MoveDirection.DOWN) {
                player.setDirection(MoveDirection.UP);
                moveAndDraw(board, board.getPlayer().getHeadXCord(), board.getPlayer().getHeadYCord(), tailCords.getY(), tailCords.getX());

            } else if (button == KeyCode.DOWN && player.getDirection() != MoveDirection.UP) {
                player.setDirection(MoveDirection.DOWN);
                moveAndDraw(board, board.getPlayer().getHeadXCord(), board.getPlayer().getHeadYCord(), tailCords.getY(), tailCords.getX());

            } else if (button == KeyCode.LEFT && player.getDirection() != MoveDirection.RIGHT) {
                player.setDirection(MoveDirection.LEFT);
                moveAndDraw(board, board.getPlayer().getHeadXCord(), board.getPlayer().getHeadYCord(), tailCords.getY(), tailCords.getX());

            } else if (button == KeyCode.RIGHT && player.getDirection() != MoveDirection.LEFT) {
                player.setDirection(MoveDirection.RIGHT);
                moveAndDraw(board, board.getPlayer().getHeadXCord(), board.getPlayer().getHeadYCord(), tailCords.getY(), tailCords.getX());

            }
        });

    }

    private void moveAndDraw(GameBoard board, int oldHeadX, int oldHeadY, int oldTailY, int oldTailX) {
        Platform.runLater(() -> {
            drawSnake.clearSnakeFromGridPane();
        });
        moveTail(board, oldTailY, oldTailX);
        serveHeadMove(board, oldHeadX, oldHeadY);
        Platform.runLater(() -> {
            drawSnake.drawPlayer();
        });
    }

    private void serveHeadMove(GameBoard board, int oldHeadX, int oldHeadY) {
        if (board.getPlayer().getDirection() == MoveDirection.UP){
            moveHeadUp(board, oldHeadX, oldHeadY);

        } else if (board.getPlayer().getDirection() == MoveDirection.DOWN){
            moveHeadDown(board, oldHeadX, oldHeadY);
        } else if (board.getPlayer().getDirection() == MoveDirection.LEFT){
            moveHeadLeft(board, oldHeadX, oldHeadY);
        } else if (board.getPlayer().getDirection() == MoveDirection.RIGHT){
            moveHeadRight(board, oldHeadX, oldHeadY);
        }
    }

    private void moveTail(GameBoard board, Integer oldTailY, Integer oldTailX) {

        if (snakeElementsChecker.nodeIsUnderTail(board, oldTailY, oldTailX)){
            moveTailDown(board, oldTailY, oldTailX);
        } else if (snakeElementsChecker.nodeIsAboveTail(board, oldTailY, oldTailX)){
            moveTailUp(board, oldTailY, oldTailX);
        } else if (snakeElementsChecker.nodeIsRightToTail(board, oldTailY, oldTailX)){
            moveTailRight(board, oldTailY, oldTailX);
        } else if (snakeElementsChecker.nodeIsLeftToTail(board, oldTailY, oldTailX)){
            moveTailLeft(board, oldTailY, oldTailX);
        }
    }

    private void moveTailLeft(GameBoard board, Integer oldTailY, Integer oldTailX) {
        board.getGameMap().get(oldTailY).set(oldTailX, GameElement.EMPTY);
        int newTailX = snakeElementsChecker.getNewSnakeElementPositionGoLeft(oldTailX);
        board.getGameMap().get(oldTailY).set(newTailX, GameElement.SNAKE_TAIL);
    }

    private void moveTailRight(GameBoard board, Integer oldTailY, Integer oldTailX) {
        board.getGameMap().get(oldTailY).set(oldTailX, GameElement.EMPTY);
        int newTailX = snakeElementsChecker.getNewSnakeElementPositionGoRight(oldTailX);
        board.getGameMap().get(oldTailY).set(newTailX, GameElement.SNAKE_TAIL);
    }

    private void moveTailUp(GameBoard board, Integer oldTailY, Integer oldTailX) {
        board.getGameMap().get(oldTailY).set(oldTailX, GameElement.EMPTY);
        int newTailY = snakeElementsChecker.getNewSnakeElementPositionGoUp(oldTailY);
        board.getGameMap().get(newTailY).set(oldTailX, GameElement.SNAKE_TAIL);
    }

    private void moveTailDown(GameBoard board, Integer oldTailY, Integer oldTailX) {
        board.getGameMap().get(oldTailY).set(oldTailX, GameElement.EMPTY);
        int newTailY = snakeElementsChecker.getNewSnakeElementPositionGoDown(oldTailY);
        board.getGameMap().get(newTailY).set(oldTailX, GameElement.SNAKE_TAIL);
    }

    private void moveHeadUp(GameBoard board, int oldHeadX, int oldHeadY) {
        board.getGameMap().get(oldHeadY).set(oldHeadX, GameElement.SNAKE_NODE);
        int newHeadY = snakeElementsChecker.getNewSnakeElementPositionGoUp(oldHeadY);
        board.getGameMap().get(newHeadY).set(oldHeadX, GameElement.SNAKE_HEAD);
        board.getPlayer().setHeadYCord(newHeadY);
    }

    private void moveHeadDown(GameBoard board, int oldHeadX, int oldHeadY) {
        board.getGameMap().get(oldHeadY).set(oldHeadX, GameElement.SNAKE_NODE);
        int newHeadY = snakeElementsChecker.getNewSnakeElementPositionGoDown(oldHeadY);
        board.getGameMap().get(newHeadY).set(oldHeadX, GameElement.SNAKE_HEAD);
        board.getPlayer().setHeadYCord(newHeadY);
    }

    private void moveHeadLeft(GameBoard board, int oldHeadX, int oldHeadY) {
        board.getGameMap().get(oldHeadY).set(oldHeadX, GameElement.SNAKE_NODE);
        int newHeadX = snakeElementsChecker.getNewSnakeElementPositionGoLeft(oldHeadX);
        board.getGameMap().get(oldHeadY).set((newHeadX), GameElement.SNAKE_HEAD);
        board.getPlayer().setHeadXCord(newHeadX);
    }

    private void moveHeadRight(GameBoard board, int oldHeadX, int oldHeadY) {
        board.getGameMap().get(oldHeadY).set(oldHeadX, GameElement.SNAKE_NODE);
        int newHeadX = snakeElementsChecker.getNewSnakeElementPositionGoRight(oldHeadX);
        board.getGameMap().get(oldHeadY).set((newHeadX), GameElement.SNAKE_HEAD);
        board.getPlayer().setHeadXCord(newHeadX);
    }

}
