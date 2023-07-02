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
        ImageView head = generateImage("/snake/snakeHead.png");
        head.setId("SNAKE_HEAD");

        ImageView node = generateImage("/snake/snakeNode.png");
        node.setId("SNAKE_NODE");

        ImageView tail = generateImage("/snake/snakeTail.png");
        tail.setId("SNAKE_TAIL");

        int consideredPartX = board.getPlayer().getHeadXCord();
        int consideredPartY = board.getPlayer().getHeadYCord();

        drawHead(direction, head);
        findNodesTailPositionAndDraw(node, tail, consideredPartX, consideredPartY);

    }

    public void drawHead(MoveDirection direction, ImageView head) {
        gameLayout.add(head, board.getPlayer().getHeadXCord(), board.getPlayer().getHeadYCord());
        if (direction == MoveDirection.UP){
            head.setRotate(270);
        } else if (direction == MoveDirection.LEFT){
            head.setRotate(180);
        } else if (direction == MoveDirection.DOWN){
            head.setRotate(90);
        }
    }

    public void drawAppleInRandomPlace(){
        ImageView apple = generateImage("/snake/apple.png");
        apple.setId("APPLE");
        Pair appleCords = addAppleToGameBoardInRandomPlace();
        gameLayout.add(apple, appleCords.getX(), appleCords.getY());
    }

    public Pair addAppleToGameBoardInRandomPlace() {
        Pair appleCords = new Pair((int)(Math.random() * 40), (int)(Math.random() * 20));
        board.getGameMap().get(appleCords.getY()).set(appleCords.getX(), GameElement.APPLE);
        return appleCords;
    }

    public ImageView generateImage(String path){
        Image image = new Image(getClass().getResourceAsStream(path));
        return new ImageView(image);
    }

    private void findNodesTailPositionAndDraw(ImageView node, ImageView tail, int consideredPartX, int consideredPartY) {
        ImageView element;
        NodeCheckDirection checkDirection = NodeCheckDirection.DEFAULT;
        for (int i = 0; i < board.getPlayer().getLevel() + CHECK_HEAD; i++) {
            if (i < board.getPlayer().getLevel() + CHECK_HEAD - 1){
                element = generateNewNode(node, i);
            } else {
                element = tail;
            }
            if (snakeElementsChecker.nodeIsUnder(consideredPartX, consideredPartY) && checkDirection != NodeCheckDirection.UP){
                checkDirection = NodeCheckDirection.DOWN;
                consideredPartY = snakeElementsChecker.getNewSnakeElementPositionGoDown(consideredPartY);
                gameLayout.add(element, consideredPartX, consideredPartY);
                element.setRotate(270);
            } else if (snakeElementsChecker.nodeIsAbove(consideredPartX, consideredPartY) && checkDirection != NodeCheckDirection.DOWN){
                checkDirection = NodeCheckDirection.UP;
                consideredPartY = snakeElementsChecker.getNewSnakeElementPositionGoUp(consideredPartY);
                gameLayout.add(element, consideredPartX, consideredPartY);
                element.setRotate(90);
            } else if (snakeElementsChecker.nodeIsLeft(consideredPartX, consideredPartY) && checkDirection != NodeCheckDirection.RIGHT){
                checkDirection = NodeCheckDirection.LEFT;
                consideredPartX = snakeElementsChecker.getNewSnakeElementPositionGoLeft(consideredPartX);
                gameLayout.add(element, consideredPartX, consideredPartY);
            } else if (checkDirection != NodeCheckDirection.LEFT){
                checkDirection = NodeCheckDirection.RIGHT;
                consideredPartX = snakeElementsChecker.getNewSnakeElementPositionGoRight(consideredPartX);
                gameLayout.add(element, consideredPartX, consideredPartY);
                element.setRotate(180);
            }
        }
    }
    

    private ImageView generateNewNode(ImageView node, int i) {
        ImageView element;
        element = generateImage("/snake/snakeNode.png");
        element.setId(node.getId() + i);
        return element;
    }

    public void clearSnakeFromGridPane(){
        List<Node> nodesToRemove = new ArrayList<>();
        for (Node node : gameLayout.getChildren()){
            if (node instanceof ImageView){
                if (node.getId().equals("SNAKE_HEAD") || node.getId().contains("SNAKE_NODE") || node.getId().equals("SNAKE_TAIL")){
                    nodesToRemove.add(node);
                }
            }
        }
        gameLayout.getChildren().removeAll(nodesToRemove);

    }

    public void clearAppleFromGridPane(){
        for (Node node : gameLayout.getChildren()){
            if (node instanceof ImageView){
                if (node.getId().equals("APPLE")){
                    gameLayout.getChildren().remove(node);
                    return;
                }
            }
        }

    }
}
