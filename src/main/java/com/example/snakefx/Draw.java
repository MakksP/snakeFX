package com.example.snakefx;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class Draw {
    private final GridPane gameLayout;
    private final GameBoard board;
    private final int CHECK_HEAD = 1;
    private final Checker snakeElementsChecker;

    public Draw(GridPane gameLayout, GameBoard board, Checker snakeElementsChecker){
        this.gameLayout = gameLayout;
        this.board = board;
        this.snakeElementsChecker = snakeElementsChecker;
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

    public ImageView generateSnakePart(String path){
        Image image = new Image(getClass().getResourceAsStream(path));
        return new ImageView(image);
    }

    private void findNodesTailPositionAndDraw(ImageView node, ImageView tail, int consideredPartX, int consideredPartY) {
        ImageView element;
        for (int i = 0; i < board.getPlayer().getLevel() + CHECK_HEAD; i++) {
            if (i < board.getPlayer().getLevel() + CHECK_HEAD - 1){
                element = node;
            } else {
                element = tail;
            }
            if (snakeElementsChecker.nodeIsUnder(consideredPartX, consideredPartY)){
                consideredPartY = snakeElementsChecker.getNewSnakeElementPositionGoDown(consideredPartY);
                gameLayout.add(element, consideredPartX, consideredPartY);
                element.setRotate(270);
            } else if (snakeElementsChecker.nodeIsAbove(consideredPartX, consideredPartY)){
                consideredPartY = snakeElementsChecker.getNewSnakeElementPositionGoUp(consideredPartY);
                gameLayout.add(element, consideredPartX, consideredPartY);
                element.setRotate(90);
            } else if (snakeElementsChecker.nodeIsLeft(consideredPartX, consideredPartY)){
                consideredPartX = snakeElementsChecker.getNewSnakeElementPositionGoLeft(consideredPartX);
                gameLayout.add(element, consideredPartX, consideredPartY);
            } else{
                consideredPartX = snakeElementsChecker.getNewSnakeElementPositionGoRight(consideredPartX);
                gameLayout.add(element, consideredPartX, consideredPartY);
                element.setRotate(180);
            }
        }
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
