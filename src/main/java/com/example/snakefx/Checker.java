package com.example.snakefx;

import java.util.List;

public class Checker {
    private GameBoard board;
    public final int MAX_X_INDEX = 39;
    public final int MAX_Y_INDEX = 19;
    public final int MIN_X_Y_INDEX = 0;
    private final int ELEMENT_DOWN = 1;
    private final int ELEMENT_UP = 1;
    private final int ELEMENT_LEFT = 1;

    public Checker(GameBoard gameBoard){
        this.board = gameBoard;
    }

    public boolean nodeIsLeftToTail(GameBoard board, Integer oldTailY, Integer oldTailX) {
        int newTailX = oldTailX - 1;
        if (newTailX < MIN_X_Y_INDEX){
            newTailX = MAX_X_INDEX;
        }
        return board.getGameMap().get(oldTailY).get(newTailX) == GameElement.SNAKE_NODE;
    }

    public boolean nodeIsRightToTail(GameBoard board, Integer oldTailY, Integer oldTailX) {
        int newTailX = oldTailX + 1;
        if (newTailX > MAX_X_INDEX){
            newTailX = MIN_X_Y_INDEX;
        }
        return board.getGameMap().get(oldTailY).get(newTailX) == GameElement.SNAKE_NODE;
    }

    public boolean nodeIsAboveTail(GameBoard board, Integer oldTailY, Integer oldTailX) {
        int newTailY = oldTailY - 1;
        if (newTailY < MIN_X_Y_INDEX){
            newTailY = MAX_Y_INDEX;
        }
        return board.getGameMap().get(newTailY).get(oldTailX) == GameElement.SNAKE_NODE;
    }

    public boolean nodeIsUnderTail(GameBoard board, Integer oldTailY, Integer oldTailX) {
        int newTailY = oldTailY + 1;
        if (newTailY > MAX_Y_INDEX){
            newTailY = MIN_X_Y_INDEX;
        }
        return board.getGameMap().get(newTailY).get(oldTailX) == GameElement.SNAKE_NODE;
    }

    public boolean nodeIsLeft(int consideredPartX, int consideredPartY) {
        int consideredGridXCell = consideredPartX - ELEMENT_LEFT;
        if (consideredGridXCell < MIN_X_Y_INDEX){
            consideredGridXCell = MAX_X_INDEX;
        }
        return board.getGameMap().get(consideredPartY).get(consideredGridXCell) == GameElement.SNAKE_NODE
                || board.getGameMap().get(consideredPartY).get(consideredGridXCell) == GameElement.SNAKE_TAIL;
    }

    public boolean nodeIsAbove(int consideredPartX, int consideredPartY) {
        int consideredGridYCell = consideredPartY - ELEMENT_UP;
        if (consideredGridYCell < MIN_X_Y_INDEX){
            consideredGridYCell = MAX_Y_INDEX;
        }
        return board.getGameMap().get(consideredGridYCell).get(consideredPartX) == GameElement.SNAKE_NODE
                || board.getGameMap().get(consideredGridYCell).get(consideredPartX) == GameElement.SNAKE_TAIL;
    }

    public boolean nodeIsUnder(int consideredPartX, int consideredPartY) {
        int consideredGridYCell = consideredPartY + ELEMENT_DOWN;
        if (consideredGridYCell > MAX_Y_INDEX){
            consideredGridYCell = MIN_X_Y_INDEX;
        }
        return board.getGameMap().get(consideredGridYCell).get(consideredPartX) == GameElement.SNAKE_NODE
                || board.getGameMap().get(consideredGridYCell).get(consideredPartX) == GameElement.SNAKE_TAIL;
    }

    public void findTailCords(GameBoard board, Pair tailCords) {
        for (List<GameElement> elements : board.getGameMap()){
            tailCords.setX(elements.indexOf(GameElement.SNAKE_TAIL));
            if (tailCords.getX() != -1){
                break;
            }
            tailCords.setY(tailCords.getY() + 1);

        }
    }
}


