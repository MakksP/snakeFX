package com.example.snakefx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class SnakeMovement {
    public static final int HEAD_INDEX = 0;
    public static final int MIN_X_Y_CORD = 0;
    public static final int ONE_ELEMENT_LEFT = 1;
    public static final int MAX_X_INDEX = 39;
    public static final int ONE_ELEMENT_RIGHT = 1;
    public static final int ONE_ELEMENT_UP = 1;
    public static final int MAX_Y_INDEX = 19;
    public static final int ONE_ELEMENT_DOWN = 1;
    private final GridPane gameLayout;
    private Scene gameScene;
    private final int periodOfTime = 800;
    private final Draw drawElement;
    private Snake player;

    public SnakeMovement(Snake player, GridPane gameLayout, Scene gameScene, Draw drawElement, Pair firstAppleCords){
        this.player = player;
        this.gameLayout = gameLayout;
        this.gameScene = gameScene;
        this.drawElement = drawElement;

        final Pair[] appleCords = {firstAppleCords};
        Timeline scheduleMove = new Timeline(new KeyFrame(Duration.millis(periodOfTime), event -> {
            Platform.runLater(() -> {
                drawElement.clearSnakeFromGridPane();
            });
            player.setSnakeElementsWithCords(generateNewPositionAfterMoveSnake());
            Platform.runLater(() -> {
                drawElement.drawPlayer();
            });
            if (eatenApple(player, appleCords[0])){
                appleCords[0] = drawElement.generateAppleRandomCords();
                Platform.runLater(() -> {
                    repaintEatenApple(drawElement, appleCords[0]);
                });
                if (player.getEatenApples() > 0){
                    player.increaseLevel();
                    player.setEatenZero();
                    //serveHeadMove(board, board.getPlayer().getHeadXCord(), board.getPlayer().getHeadYCord());
                }
            }
        }));
        scheduleMove.setCycleCount(Timeline.INDEFINITE);
        scheduleMove.play();

        this.gameScene.setOnKeyPressed(keyEvent -> {
            KeyCode button = keyEvent.getCode();
            if (button == KeyCode.UP && player.getDirection() != MoveDirection.DOWN) {
                player.setDirection(MoveDirection.UP);

            } else if (button == KeyCode.DOWN && player.getDirection() != MoveDirection.UP) {
                player.setDirection(MoveDirection.DOWN);

            } else if (button == KeyCode.LEFT && player.getDirection() != MoveDirection.RIGHT) {
                player.setDirection(MoveDirection.LEFT);

            } else if (button == KeyCode.RIGHT && player.getDirection() != MoveDirection.LEFT) {
                player.setDirection(MoveDirection.RIGHT);

            }
            Platform.runLater(() -> {
                drawElement.clearSnakeFromGridPane();
            });
            player.setSnakeElementsWithCords(generateNewPositionAfterMoveSnake());
            Platform.runLater(() -> {
                drawElement.drawPlayer();
            });
            if (eatenApple(player, appleCords[0])){
                appleCords[0] = drawElement.generateAppleRandomCords();
                Platform.runLater(() -> {
                    repaintEatenApple(drawElement, appleCords[0]);
                });
                if (player.getEatenApples() > 0){
                    player.increaseLevel();
                    player.setEatenZero();
                    //serveHeadMove(board, board.getPlayer().getHeadXCord(), board.getPlayer().getHeadYCord());
                }
            }
        });

    }

    private static boolean eatenApple(Snake player, Pair appleCords) {
        return player.getHeadCords().getX() == appleCords.getX() && player.getHeadCords().getY() == appleCords.getY();
    }

    public void repaintEatenApple(Draw drawElement, Pair appleCords) {
        Platform.runLater(() -> {
                drawElement.clearAppleFromGridPane();
                drawElement.drawAppleInRandomPlace(appleCords);

        });
    }

    public List<Pair> generateNewPositionAfterMoveSnake() {
        List<Pair> newSnakeCords = new ArrayList<>();
        int elementCount = 0;
        for (Pair element : player.getSnakeElementsWithCords()){
            if (elementCount == 0){
                generateNewHeadPositionAfterMove(newSnakeCords);
            } else {
                generateNewOrdinaryElementsPositionAfterMove(newSnakeCords, element, elementCount);
            }
            elementCount++;
        }

        return newSnakeCords;
    }

    public void generateNewHeadPositionAfterMove(List<Pair> newSnakeCords){
        Pair headCords = player.getSnakeElementsWithCords().get(HEAD_INDEX);
        if (player.getDirection() == MoveDirection.UP){
            newSnakeCords.add(generateCordsForElementAfterGoUp(headCords));
        } else if (player.getDirection() == MoveDirection.DOWN){
            newSnakeCords.add(generateCordsForElementAfterGoDown(headCords));
        } else if (player.getDirection() == MoveDirection.LEFT){
            newSnakeCords.add(generateCordsForElementAfterGoLeft(headCords));
        } else {
            newSnakeCords.add(generateCordsForElementAfterGoRight(headCords));
        }
    }

    public void generateNewOrdinaryElementsPositionAfterMove(List<Pair> newSnakeCords, Pair elementToMove, int elementCount){
        Pair newPositionCords = generateCordsForElementAfterGoLeft(elementToMove);
        if (addElementToPaneIfIsNext(newSnakeCords, newPositionCords, elementCount)) return;
        newPositionCords = generateCordsForElementAfterGoRight(elementToMove);
        if (addElementToPaneIfIsNext(newSnakeCords, newPositionCords, elementCount)) return;
        newPositionCords = generateCordsForElementAfterGoUp(elementToMove);
        if (addElementToPaneIfIsNext(newSnakeCords, newPositionCords, elementCount)) return;
        newPositionCords = generateCordsForElementAfterGoDown(elementToMove);
        addElementToPaneIfIsNext(newSnakeCords, newPositionCords, elementCount);
    }

    private boolean addElementToPaneIfIsNext(List<Pair> newSnakeCords, Pair newPositionCords, int elementCount) {
        if (player.cordsAreEquivalent(newPositionCords, elementCount)){
            newSnakeCords.add(newPositionCords);
            return true;
        }
        return false;
    }


    public Pair generateCordsForElementAfterGoLeft(Pair elementToMove) {
        Pair newPositionCords = new Pair(elementToMove.getX(), elementToMove.getY());
        newPositionCords.setX(newPositionCords.getX() - ONE_ELEMENT_LEFT);
        if (newPositionCords.getX() < MIN_X_Y_CORD){
            newPositionCords.setX(MAX_X_INDEX);
        }
        return newPositionCords;
    }

    public Pair generateCordsForElementAfterGoRight(Pair elementToMove) {
        Pair newPositionCords = new Pair(elementToMove.getX(), elementToMove.getY());
        newPositionCords.setX(newPositionCords.getX() + ONE_ELEMENT_RIGHT);
        if (newPositionCords.getX() > MAX_X_INDEX){
            newPositionCords.setX(MIN_X_Y_CORD);
        }
        return newPositionCords;
    }

    public Pair generateCordsForElementAfterGoUp(Pair elementToMove) {
        Pair newPositionCords = new Pair(elementToMove.getX(), elementToMove.getY());
        newPositionCords.setY(newPositionCords.getY() - ONE_ELEMENT_UP);
        if (newPositionCords.getY() < MIN_X_Y_CORD){
            newPositionCords.setY(MAX_Y_INDEX);
        }
        return newPositionCords;
    }

    public Pair generateCordsForElementAfterGoDown(Pair elementToMove) {
        Pair newPositionCords = new Pair(elementToMove.getX(), elementToMove.getY());
        newPositionCords.setY(newPositionCords.getY() + ONE_ELEMENT_DOWN);
        if (newPositionCords.getY() > MAX_Y_INDEX){
            newPositionCords.setY(MIN_X_Y_CORD);
        }
        return newPositionCords;
    }
}
