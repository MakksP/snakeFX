package com.example.snakefx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;


public class SnakeMovement {
    private final GameBoard board;
    private final GridPane gameLayout;
    private Scene gameScene;
    private final int periodOfTime = 800;
    private final Checker snakeElementsChecker;
    private final Draw drawElement;

    public SnakeMovement(GameBoard board, GridPane gameLayout, Scene gameScene, Checker snakeElementsChecker, Draw drawElement){
        this.board = board;
        this.gameLayout = gameLayout;
        this.gameScene = gameScene;
        this.snakeElementsChecker = snakeElementsChecker;
        this.drawElement = drawElement;

        Timeline scheduleMove = new Timeline(new KeyFrame(Duration.millis(periodOfTime), event -> {
            Platform.runLater(() -> {
                drawElement.clearSnakeFromGridPane();
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
            drawElement.clearSnakeFromGridPane();
        });
        moveTail(board, oldTailY, oldTailX);
        Pair appleCords = serveHeadMove(board, oldHeadX, oldHeadY);
        Platform.runLater(() -> {
            drawElement.drawPlayer();
            if (!appleCords.nothingHappened()){
                drawElement.clearAppleFromGridPane();
                drawElement.drawAppleInRandomPlace();
            }
        });
    }

    private Pair serveHeadMove(GameBoard board, int oldHeadX, int oldHeadY) {
        Pair appleCords = new Pair(-1, -1);
        if (board.getPlayer().getDirection() == MoveDirection.UP){
            appleCords = moveHeadUp(board, oldHeadX, oldHeadY);

        } else if (board.getPlayer().getDirection() == MoveDirection.DOWN){
            appleCords = moveHeadDown(board, oldHeadX, oldHeadY);
        } else if (board.getPlayer().getDirection() == MoveDirection.LEFT){
            appleCords = moveHeadLeft(board, oldHeadX, oldHeadY);
        } else if (board.getPlayer().getDirection() == MoveDirection.RIGHT){
            appleCords = moveHeadRight(board, oldHeadX, oldHeadY);
        }
        return appleCords;
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

    private Pair moveHeadUp(GameBoard board, int oldHeadX, int oldHeadY) {
        board.getGameMap().get(oldHeadY).set(oldHeadX, GameElement.SNAKE_NODE);
        int newHeadY = snakeElementsChecker.getNewSnakeElementPositionGoUp(oldHeadY);
        Pair appleCords = new Pair(-1, -1);
        checkIfAppleIsAboveOrUnderHeadAndSetAppleCords(board, oldHeadX, newHeadY, appleCords);
        board.getGameMap().get(newHeadY).set(oldHeadX, GameElement.SNAKE_HEAD);
        board.getPlayer().setHeadYCord(newHeadY);
        return appleCords;
    }

    private Pair moveHeadDown(GameBoard board, int oldHeadX, int oldHeadY) {
        board.getGameMap().get(oldHeadY).set(oldHeadX, GameElement.SNAKE_NODE);
        int newHeadY = snakeElementsChecker.getNewSnakeElementPositionGoDown(oldHeadY);
        Pair appleCords = new Pair(-1, -1);
        checkIfAppleIsAboveOrUnderHeadAndSetAppleCords(board, oldHeadX, newHeadY, appleCords);
        board.getGameMap().get(newHeadY).set(oldHeadX, GameElement.SNAKE_HEAD);
        board.getPlayer().setHeadYCord(newHeadY);
        return appleCords;
    }

    private static void checkIfAppleIsAboveOrUnderHeadAndSetAppleCords(GameBoard board, int oldHeadX, int newHeadY, Pair appleCords) {
        if (board.getGameMap().get(newHeadY).get(oldHeadX) == GameElement.APPLE){
            appleCords.setX(oldHeadX);
            appleCords.setY(newHeadY);
        }
    }

    private Pair moveHeadLeft(GameBoard board, int oldHeadX, int oldHeadY) {
        board.getGameMap().get(oldHeadY).set(oldHeadX, GameElement.SNAKE_NODE);
        int newHeadX = snakeElementsChecker.getNewSnakeElementPositionGoLeft(oldHeadX);
        Pair appleCords = new Pair(-1, -1);
        checkIfAppleIsLeftOrRightHeadAndSetAppleCords(board, oldHeadY, newHeadX, appleCords);
        board.getGameMap().get(oldHeadY).set((newHeadX), GameElement.SNAKE_HEAD);
        board.getPlayer().setHeadXCord(newHeadX);
        return appleCords;
    }

    private static void checkIfAppleIsLeftOrRightHeadAndSetAppleCords(GameBoard board, int oldHeadY, int newHeadX, Pair appleCords) {
        if (board.getGameMap().get(oldHeadY).get(newHeadX) == GameElement.APPLE){
            appleCords.setX(newHeadX);
            appleCords.setY(oldHeadY);
        }
    }

    private Pair moveHeadRight(GameBoard board, int oldHeadX, int oldHeadY) {
        board.getGameMap().get(oldHeadY).set(oldHeadX, GameElement.SNAKE_NODE);
        int newHeadX = snakeElementsChecker.getNewSnakeElementPositionGoRight(oldHeadX);
        Pair appleCords = new Pair(-1, -1);
        checkIfAppleIsLeftOrRightHeadAndSetAppleCords(board, oldHeadY, newHeadX, appleCords);
        board.getGameMap().get(oldHeadY).set((newHeadX), GameElement.SNAKE_HEAD);
        board.getPlayer().setHeadXCord(newHeadX);
        return appleCords;
    }

}
