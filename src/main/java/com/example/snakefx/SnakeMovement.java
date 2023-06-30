package com.example.snakefx;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SnakeMovement {
    private GameBoard board;
    private GridPane gameLayout;
    private static final int CHECK_HEAD = 1;
    private static final int ELEMENT_DOWN = 1;
    private static final int ELEMENT_UP = 1;
    private static final int ELEMENT_LEFT = 1;
    private final int periodOfTime = 800;
    private final int snakeIndexInGridPane = 800;

    public SnakeMovement(GameBoard board, GridPane gameLayout){
        this.board = board;
        this.gameLayout = gameLayout;

        ScheduledExecutorService scheduleMove = new ScheduledThreadPoolExecutor(1);
        scheduleMove.scheduleAtFixedRate(() -> {
                try {
                    Thread.sleep(periodOfTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Platform.runLater(() -> {
                    clearSnakeFromGridPane();
                });
                int oldHeadX = board.getPlayer().getHeadXCord();
                int oldHeadY = board.getPlayer().getHeadYCord();

                int oldTailY = 0, oldTailX = 0;
                for (List<GameElement> elements : board.getGameMap()){
                    oldTailX = elements.indexOf(GameElement.SNAKE_TAIL);
                    if (oldTailX != -1){
                        break;
                    }
                    oldTailY++;

                }

                if (board.getPlayer().getDirection() == MoveDirection.UP){
                    moveHeadUp(board, oldHeadX, oldHeadY);
                    moveTail(board, oldTailY, oldTailX);

                }
                Platform.runLater(() -> {
                    drawPlayer();
                });

        }, 0, periodOfTime, TimeUnit.MILLISECONDS);




    }

    private static void moveTail(GameBoard board, Integer oldTailY, Integer oldTailX) {
        if (board.getGameMap().get(oldTailY + 1).get(oldTailX) == GameElement.SNAKE_NODE){
            moveTailDown(board, oldTailY, oldTailX);
        } else if (board.getGameMap().get(oldTailY - 1).get(oldTailX) == GameElement.SNAKE_NODE){
            moveTailUp(board, oldTailY, oldTailX);
        } else if ((board.getGameMap().get(oldTailY).get(oldTailX + 1) == GameElement.SNAKE_NODE)){
            moveTailRight(board, oldTailY, oldTailX);
        } else if ((board.getGameMap().get(oldTailY).get(oldTailX - 1) == GameElement.SNAKE_NODE)){
            moveTailLeft(board, oldTailY, oldTailX);
        }
    }

    private static void moveTailLeft(GameBoard board, Integer oldTailY, Integer oldTailX) {
        board.getGameMap().get(oldTailY).set(oldTailX - 1, GameElement.SNAKE_TAIL);
    }

    private static void moveTailRight(GameBoard board, Integer oldTailY, Integer oldTailX) {
        board.getGameMap().get(oldTailY).set(oldTailX + 1, GameElement.SNAKE_TAIL);
    }

    private static void moveTailUp(GameBoard board, Integer oldTailY, Integer oldTailX) {
        board.getGameMap().get(oldTailY - 1).set(oldTailX, GameElement.SNAKE_TAIL);
    }

    private static void moveTailDown(GameBoard board, Integer oldTailY, Integer oldTailX) {
        board.getGameMap().get(oldTailY + 1).set(oldTailX, GameElement.SNAKE_TAIL);
    }

    private static void moveHeadUp(GameBoard board, int oldHeadX, int oldHeadY) {
        board.getGameMap().get(oldHeadY).set(oldHeadX, GameElement.SNAKE_NODE);
        board.getGameMap().get(oldHeadY - 1).set(oldHeadX, GameElement.SNAKE_HEAD);
        board.getPlayer().setHeadYCord(oldHeadY - 1);
    }


    public ImageView generateSnakePart(String path){
        Image image = new Image(getClass().getResourceAsStream(path));
        return new ImageView(image);
    }

    public void drawPlayer(){
        MoveDirection direction = board.getPlayer().getDirection();

        ImageView head = generateSnakePart("/snake/snakeHead.png");


        ImageView node = generateSnakePart("/snake/snakeNode.png");

        ImageView tail = generateSnakePart("/snake/snakeTail.png");

        int consideredPartX = board.getPlayer().getHeadXCord();
        int consideredPartY = board.getPlayer().getHeadYCord();

        drawHead(direction, head);
        findNodesTailPositionAndDraw(node, tail, consideredPartX, consideredPartY);

    }

    private void drawHead(MoveDirection direction, ImageView head) {
        gameLayout.add(head, board.getPlayer().getHeadXCord(), board.getPlayer().getHeadYCord());
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

    public void clearSnakeFromGridPane(){
        List<Node> nodesToRemove = new ArrayList<>();
        for (Node node : gameLayout.getChildren()){
            if (node instanceof ImageView){
                nodesToRemove.add(node);
            }
        }
        gameLayout.getChildren().removeAll(nodesToRemove);

    }
}
