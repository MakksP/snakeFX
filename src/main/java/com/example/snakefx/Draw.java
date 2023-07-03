package com.example.snakefx;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.security.URIParameter;
import java.util.ArrayList;
import java.util.List;

public class Draw {
    public static final int UP_ANGLE = 270;
    public static final int LEFT_ANGLE = 180;
    public static final int DOWN_ANGLE = 90;
    private final GridPane gameLayout;
    private final Snake player;
    private final int MAP_WIDTH = 40;
    private final int MAP_HEIGHT = 20;
    public static final int MAP_CELL_WIDTH = 48;
    public static final int MAP_CELL_HEIGHT = 54;

    public Draw(GridPane gameLayout, Snake player){
        this.gameLayout = gameLayout;
        this.player = player;
    }

    public void drawMapBackground(){
        for (int i = 0; i < MAP_HEIGHT; i++){
            for (int j = 0; j <MAP_WIDTH; j++) {
                Rectangle mapCell = new Rectangle(MAP_CELL_WIDTH, MAP_CELL_HEIGHT, Color.BLACK);
                gameLayout.add(mapCell, j, i);
            }
        }
    }

    public void drawPlayer(){
        ImageView head = generateImage("/snake/snakeHead.png");
        head.setId("SNAKE_HEAD");

        ImageView node = generateImage("/snake/snakeNode.png");
        node.setId("SNAKE_NODE");

        ImageView tail = generateImage("/snake/snakeTail.png");
        tail.setId("SNAKE_TAIL");

        int elementCounter = 0;
        Pair previousSnakeElement = new Pair(-1, -1);
        for (Pair snakeElement : player.getSnakeElementsWithCords()){
            if (elementIsHead(elementCounter)){
                rotateHead(head);
                gameLayout.add(head, snakeElement.getX(), snakeElement.getY());

            } else if (elementIsTail(elementCounter)){
                rotateOrdinaryElement(tail, previousSnakeElement, snakeElement);
                gameLayout.add(tail, snakeElement.getX(), snakeElement.getY());

            } else {
                node = generateNewNode(node, elementCounter);
                rotateOrdinaryElement(node, previousSnakeElement, snakeElement);
                gameLayout.add(node, snakeElement.getX(), snakeElement.getY());

            }
            assignPreviousElementCords(previousSnakeElement, snakeElement);
            elementCounter++;

        }

    }

    public void assignPreviousElementCords(Pair previousSnakeElement, Pair snakeElement) {
        previousSnakeElement.setX(snakeElement.getX());
        previousSnakeElement.setY(snakeElement.getY());
    }

    public boolean elementIsTail(int elementCounter) {
        return elementCounter == player.getSnakeElementsWithCords().size() - 1;
    }

    public boolean elementIsHead(int elementCounter) {
        return elementCounter == 0;
    }

    public void rotateHead(ImageView head) {
        if (player.getDirection() == MoveDirection.UP){
            head.setRotate(UP_ANGLE);
        } else if (player.getDirection() == MoveDirection.LEFT){
            head.setRotate(LEFT_ANGLE);
        } else if (player.getDirection() == MoveDirection.DOWN){
            head.setRotate(DOWN_ANGLE);
        }
    }

    public void rotateOrdinaryElement(ImageView element, Pair previousElement, Pair currentElement){
        if (currentElementAtLeftToPrevious(previousElement, currentElement)){
            element.setRotate(LEFT_ANGLE);
        } else if (currentElementAbovePrevious(previousElement, currentElement)) {
            element.setRotate(DOWN_ANGLE);
        } else if (currentElementUnderPrevious(previousElement, currentElement)) {
            element.setRotate(UP_ANGLE);
        }
    }

    public boolean currentElementAtLeftToPrevious(Pair previousElement, Pair currentElement) {
        return currentElement.getX() < previousElement.getX();
    }

    public boolean currentElementAbovePrevious(Pair previousElement, Pair currentElement){
        return currentElement.getY() < previousElement.getY();
    }

    public boolean currentElementUnderPrevious(Pair previousElement, Pair currentElement){
        return currentElement.getY() > previousElement.getY();
    }


    public void drawAppleInRandomPlace(){
        ImageView apple = generateImage("/snake/apple.png");
        apple.setId("APPLE");
        Pair appleCords = generateAppleRandomCords();
        gameLayout.add(apple, appleCords.getX(), appleCords.getY());
    }

    public Pair generateAppleRandomCords() {
        return new Pair((int)(Math.random() * 40), (int)(Math.random() * 20));
    }

    public ImageView generateImage(String path){
        Image image = new Image(getClass().getResourceAsStream(path));
        return new ImageView(image);
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
