package com.example.snakefx;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private static GameBoard instance;
    private List<List<GameElement>> gameMap;
    private final int mapSize = 40;

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

    private void createMap() {
        for (int i = 0; i < mapSize; i++){
            gameMap.add(new ArrayList<>());
        }
    }

    private void fillMap(){
        for (int i = 0; i < mapSize; i++){
            for (int j = 0; j < mapSize; j++) {
                gameMap.get(i).set(j, GameElement.EMPTY);
            }
        }
    }

}
