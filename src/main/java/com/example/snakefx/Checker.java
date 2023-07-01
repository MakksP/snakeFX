package com.example.snakefx;

import java.util.List;

public class Checker {
    private GameBoard board;
    public final int MAX_X_INDEX = 39;
    public final int MAX_Y_INDEX = 19;
    public final int MIN_X_Y_INDEX = 0;

    public Checker(GameBoard gameBoard){
        this.board = gameBoard;
    }

    public boolean nodeIsLeftToTail(GameBoard board, Integer oldTailY, Integer oldTailX) {
        int newTailX = getNewSnakeElementPositionGoLeft(oldTailX);
        return board.getGameMap().get(oldTailY).get(newTailX) == GameElement.SNAKE_NODE;
    }



    public boolean nodeIsRightToTail(GameBoard board, Integer oldTailY, Integer oldTailX) {
        int newTailX = getNewSnakeElementPositionGoRight(oldTailX);
        return board.getGameMap().get(oldTailY).get(newTailX) == GameElement.SNAKE_NODE;
    }



    public boolean nodeIsAboveTail(GameBoard board, Integer oldTailY, Integer oldTailX) {
        int newTailY = getNewSnakeElementPositionGoUp(oldTailY);
        return board.getGameMap().get(newTailY).get(oldTailX) == GameElement.SNAKE_NODE;
    }


    public boolean nodeIsUnderTail(GameBoard board, Integer oldTailY, Integer oldTailX) {
        int newTailY = getNewSnakeElementPositionGoDown(oldTailY);
        return board.getGameMap().get(newTailY).get(oldTailX) == GameElement.SNAKE_NODE;
    }
    

    public int getNewSnakeElementPositionGoRight(Integer oldXPosition) {
        int newXposition = oldXPosition + 1;
        if (newXposition > MAX_X_INDEX){
            newXposition = MIN_X_Y_INDEX;
        }
        return newXposition;
    }

    public int getNewSnakeElementPositionGoLeft(Integer oldXPosition) {
        int newXposition = oldXPosition - 1;
        if (newXposition < MIN_X_Y_INDEX){
            newXposition = MAX_X_INDEX;
        }
        return newXposition;
    }

    public int getNewSnakeElementPositionGoUp(Integer oldYPosition) {
        int newYposition = oldYPosition - 1;
        if (newYposition < MIN_X_Y_INDEX){
            newYposition = MAX_Y_INDEX;
        }
        return newYposition;
    }

    public int getNewSnakeElementPositionGoDown(Integer oldYPosition) {
        int newYposition = oldYPosition + 1;
        if (newYposition > MAX_Y_INDEX){
            newYposition = MIN_X_Y_INDEX;
        }
        return newYposition;
    }

    public boolean nodeIsLeft(int consideredPartX, int consideredPartY) {
        int consideredGridXCell = getNewSnakeElementPositionGoLeft(consideredPartX);
        return board.getGameMap().get(consideredPartY).get(consideredGridXCell) == GameElement.SNAKE_NODE
                || board.getGameMap().get(consideredPartY).get(consideredGridXCell) == GameElement.SNAKE_TAIL;
    }

    public boolean nodeIsAbove(int consideredPartX, int consideredPartY) {
        int consideredGridYCell = getNewSnakeElementPositionGoUp(consideredPartY);
        return board.getGameMap().get(consideredGridYCell).get(consideredPartX) == GameElement.SNAKE_NODE
                || board.getGameMap().get(consideredGridYCell).get(consideredPartX) == GameElement.SNAKE_TAIL;
    }

    public boolean nodeIsUnder(int consideredPartX, int consideredPartY) {
        int consideredGridYCell = getNewSnakeElementPositionGoDown(consideredPartY);
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


