package com.example.snakefx;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private int eatenApples;
    private int level;
    private boolean isActive;
    private MoveDirection direction;
    private List<Pair> snakeElementsWithCords;
    private int score;

    public Snake(){
        isActive = true;
        eatenApples = 0;
        level = 1;
        score = 0;
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

    public void setSnakeElementsWithCords(List <Pair> newSnakeElementsWithCords){
        snakeElementsWithCords = newSnakeElementsWithCords;
    }

    public Pair getHeadCords(){
        return new Pair(snakeElementsWithCords.get(0).getX(), snakeElementsWithCords.get(0).getY());
    }

    public boolean cordsAreEquivalent(Pair cords, int elementCount){
        Pair equivalentElement = snakeElementsWithCords.get(elementCount - 1);
        if (equivalentElement.getX() == cords.getX() && equivalentElement.getY() == cords.getY()){
            return true;
        }
        return false;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
