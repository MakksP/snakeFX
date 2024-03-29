package com.example.snakefx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;


public class GameControl {
    public static final int HEAD_INDEX = 0;
    public static final int MIN_X_Y_CORD = 0;
    public static final int ONE_ELEMENT_LEFT = 1;
    public static final int MAX_X_INDEX = 39;
    public static final int ONE_ELEMENT_RIGHT = 1;
    public static final int ONE_ELEMENT_UP = 1;
    public static final int MAX_Y_INDEX = 19;
    public static final int ONE_ELEMENT_DOWN = 1;
    public static final int FIRST_SNAKE_NODE_INDEX = 1;
    public static final int FIRST_NODE = 1;
    public static final int APPLE_EATEN_NEEDS_TO_GROW = 1;
    public static final int SCORE_AMOUNT_AFTER_EATING_APPLE = 10;
    private GridPane gameLayout;
    private Scene gameScene;
    private int periodOfTime = 800;
    private final Draw drawElement;
    private Snake player;
    private Timeline scheduleMove;
    private GameInitializer currentGame;

    public GameControl(Snake player, GridPane gameLayout, Scene gameScene, Draw drawElement, Pair firstAppleCords, GameInitializer gameInitializer){
        this.player = player;
        this.gameLayout = gameLayout;
        this.gameScene = gameScene;
        this.drawElement = drawElement;
        currentGame = gameInitializer;

        final Pair[] appleCords = {firstAppleCords};
        KeyFrame snakeMoveTask = generateSnakeMoveTask(player, gameLayout, drawElement, appleCords);

        scheduleMove = new Timeline(snakeMoveTask);
        scheduleMove.setCycleCount(Timeline.INDEFINITE);
        scheduleMove.play();

        this.gameScene.setOnKeyPressed(keyEvent -> {
            KeyCode button = keyEvent.getCode();
            if (button == KeyCode.R){
                currentGame.clearGame();
                currentGame = null;
                currentGame = new GameInitializer((Stage) gameLayout.getScene().getWindow());
                return;
            } else if (button == KeyCode.P){
                InfoLabel infoLabel;
                if (player.isActive()){
                    player.setActive(false);
                    gameStop();
                    infoLabel = new InfoLabel(drawElement, "/snake/PauseLabelImage.png");
                    infoLabel.setId("PAUSE_GAME_LABEL");
                    infoLabel.addLabelToGameScene(gameLayout);
                } else {
                    player.setActive(true);
                    gameStart();
                    gameLayout.getChildren().remove(drawElement.findNodeById("PAUSE_GAME_LABEL"));

                }
            } else if (!player.isActive()){
                return;
            }

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
                serveEatenAppleAction(player, drawElement, appleCords);
            } else if (playerLost()){
                InfoLabel infoLabel = new InfoLabel(drawElement, "/snake/LostLabelImage.png");
                infoLabel.addLabelToGameScene(gameLayout);
                player.setActive(false);
                gameStop();
            }
        });

    }

    private KeyFrame generateSnakeMoveTask(Snake player, GridPane gameLayout, Draw drawElement, Pair[] appleCords) {
        KeyFrame snakeMoveTask = new KeyFrame(Duration.millis(periodOfTime), event -> {
            Platform.runLater(() -> {
                drawElement.clearSnakeFromGridPane();
            });
            player.setSnakeElementsWithCords(generateNewPositionAfterMoveSnake());
            Platform.runLater(() -> {
                drawElement.drawPlayer();
            });
            if (eatenApple(player, appleCords[0])){
                serveEatenAppleAction(player, drawElement, appleCords);
            } else if (playerLost()){
                InfoLabel infoLabel = new InfoLabel(drawElement, "/snake/LostLabelImage.png");
                infoLabel.addLabelToGameScene(gameLayout);
                player.setActive(false);
                gameStop();
            }
        });
        return snakeMoveTask;
    }

    private void serveEatenAppleAction(Snake player, Draw drawElement, Pair[] appleCords) {
        appleCords[0] = drawElement.generateAppleRandomCords();
        player.setEatenApples(player.getEatenApples() + 1);
        player.setScore(player.getScore() + SCORE_AMOUNT_AFTER_EATING_APPLE);
        Platform.runLater(() -> {
            repaintEatenApple(drawElement, appleCords[0]);
            drawElement.repaintUpdatedPoints();
        });

        if (player.getEatenApples() >= APPLE_EATEN_NEEDS_TO_GROW){
            player.increaseLevel();
            player.setEatenZero();
            player.setSnakeElementsWithCords(snakeLengthen());
            increaseGameSpeed(player, drawElement, appleCords);

        }
    }

    private void increaseGameSpeed(Snake player, Draw drawElement, Pair[] appleCords) {
        gameStop();
        if (periodOfTime != 50) {
            periodOfTime -= 50;
        }
        scheduleMove = new Timeline(generateSnakeMoveTask(player, gameLayout, drawElement, appleCords));
        scheduleMove.setCycleCount(Timeline.INDEFINITE);
        gameStart();
    }

    public void gameStop(){
        scheduleMove.stop();
    }

    public void gameStart(){
        scheduleMove.play();
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

    public List<Pair> snakeLengthen(){
        List<Pair> newSnakeCords = new ArrayList<>();
        generateNewHeadPositionAfterMove(newSnakeCords);
        newSnakeCords.add(new Pair(player.getHeadCords().getX(), player.getHeadCords().getY()));
        for (Pair element : player.getSnakeElementsWithCords().subList(FIRST_SNAKE_NODE_INDEX, player.getSnakeElementsWithCords().size())){
            newSnakeCords.add(element);
        }
        return newSnakeCords;
    }

    public boolean playerLost(){
        List <Pair> snakeCords = player.getSnakeElementsWithCords();
        for (Pair element : snakeCords.subList(FIRST_NODE, snakeCords.size())){
            if (element.getX() == player.getHeadCords().getX() && element.getY() == player.getHeadCords().getY()){
                return true;
            }
        }
        return false;
    }
}
