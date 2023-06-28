package com.example.snakefx;

public class Snake {
    private int eatenApples;
    private int level;
    private MoveDirection direction;
    private int headXCord;
    private int headYCord;

    public Snake(){
        eatenApples = 0;
        level = 1;
        direction = MoveDirection.UP;
        headYCord = 10;
        headXCord = 20;
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

    public int getHeadXCord() {
        return headXCord;
    }

    public void setHeadXCord(int headXCord) {
        this.headXCord = headXCord;
    }

    public int getHeadYCord() {
        return headYCord;
    }

    public void setHeadYCord(int headYCord) {
        this.headYCord = headYCord;
    }
}
