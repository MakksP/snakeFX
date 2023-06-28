package com.example.snakefx;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private static GameBoard instance;
    private List<List<GameElement>> gameMap;
    private final int mapWidth = 40;
    private final int mapHeight = 20;
    private Snake player;

    private GameBoard(){
        gameMap = new ArrayList<>();
        createMap();
        fillMap();
    }

    public static GameBoard getInstance() {
        if (instance == null)
            instance = new GameBoard();

        return instance;
    }

    public void createMap() {
        for (int i = 0; i < mapHeight; i++){
            gameMap.add(new ArrayList<>());
        }
    }

    public void fillMap(){
        for (int i = 0; i < mapHeight; i++){
            for (int j = 0; j < mapWidth; j++) {
                gameMap.get(i).add(j, GameElement.EMPTY);
            }
        }
    }

    public List<List<GameElement>> getGameMap(){
        return gameMap;
    }

    public void createPlayer(){
        player = new Snake();
    }

    public Snake getPlayer() {
        return player;
    }
}
