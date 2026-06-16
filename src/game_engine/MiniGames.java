package game_engine;

import javax.swing.JPanel;

public interface MiniGames {
    JPanel getGamePanel(); 
    void init();
    void stop();
}