package game_engine;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.List;

public class GameManager implements GameState {
    private JFrame mainFrame;
    private List<MiniGames> playlist = new ArrayList<>();
    private int currentIndex = 0;

    public GameManager() {
        mainFrame = new JFrame("Mini Games");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
    }

    public void addGame(MiniGames game) {
        playlist.add(game);
    }

    public void startEngine() {
        if (!playlist.isEmpty()) {
            loadLevel(0);
            mainFrame.setVisible(true);
        }
    }

    private void loadLevel(int index) {
        if (index >= playlist.size()) return;

        if (currentIndex < playlist.size() && playlist.get(currentIndex) != null) {
            playlist.get(currentIndex).stop();
        }

        currentIndex = index;
        MiniGames nextGame = playlist.get(currentIndex);
        
        // 1. Clear the frame completely
        mainFrame.getContentPane().removeAll();
        
        // 2. Add the panel belonging to the next minigame
        JPanel gamePanel = nextGame.getGamePanel();
        mainFrame.add(gamePanel);
        
        // 3. Automatically adjust window size to match the minigame panel's preferred size
        mainFrame.pack(); 
        mainFrame.setLocationRelativeTo(null); // Re-centers the window on the screen
        
        // 4. Start the game logic
        nextGame.init();
        
        // 5. Force the window to acknowledge the new panel's size
        mainFrame.revalidate(); 
        mainFrame.repaint();
        
        // 6. Ensure the game can hear your keyboard/mouse
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow(); 
    
    }

    @Override
    public void onGameFinished() {
        System.out.println("Current game finished! Moving to next...");
        loadLevel(currentIndex + 1);
    }
}

