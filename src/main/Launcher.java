package main;

import game_engine.GameManager;
import main_screen.MainScreen;
import _2D_Shooter.Frame;
import chess.Game;
import soccerMiniGame.SoccerManager;

public class Launcher {
    public static void main(String[] args) {
        GameManager boss = new GameManager();
        boss.addGame(new MainScreen(boss));
        boss.addGame(new Frame(boss));
        boss.addGame(new Game(boss));
        boss.addGame(new SoccerManager(boss));
        boss.startEngine(); 
    }
}
