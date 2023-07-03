package com.example.snakefx;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private int eatenApples;
    private int level;
    private MoveDirection direction;
    private List<Pair> snakeElementsWithCords;

    public Snake(){
        eatenApples = 0;
        level = 1;
        direction = MoveDirection.UP;
        snakeElementsWithCords = new ArrayList<>();
    }

    public int getEatenApples() {
        return eatenApples;
    }

    public void setEatenApples(int eatenApples) {
        this.eatenApples = eatenApples;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public MoveDirection getDirection() {
        return direction;
    }

    public void setDirection(MoveDirection direction) {
        this.direction = direction;
    }

    public void increaseLevel(){
        level++;
    }

    public void setEatenZero(){
        eatenApples = 0;
    }

    public List<Pair> getSnakeElementsWithCords() {
        return snakeElementsWithCords;
    }

    public Pair getHeadCords(){
        return new Pair(snakeElementsWithCords.get(0).getX(), snakeElementsWithCords.get(0).getY());
    }

}
