//package soccer_minigame;
//
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class GameManager implements GameState {
//    private JFrame mainFrame;
//    private List<MiniGames> playlist = new ArrayList<>();
//    private int currentIndex = 0;
//
//    public GameManager() {
//        mainFrame = new JFrame("Juigistopia: Master of Mischief");
//        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        mainFrame.setSize(800, 600); // Adjust this to fit your biggest game
//        mainFrame.setLocationRelativeTo(null);
//    }
//
//    public void addGame(MiniGames game) {
//        playlist.add(game);
//    }
//
//    public void startEngine() {
//        if (!playlist.isEmpty()) {
//            loadLevel(0);
//            mainFrame.setVisible(true);
//        }
//    }
//
//    private void loadLevel(int index) {
//        if (index >= playlist.size()) return;
//
//        if (currentIndex < playlist.size() && playlist.get(currentIndex) != null) {
//            playlist.get(currentIndex).stop();
//        }
//
//        currentIndex = index;
//        MiniGames nextGame = playlist.get(currentIndex);
//        
//        // 1. Clear the frame
//        mainFrame.getContentPane().removeAll(); 
//        
//        // 2. Add the panel
//        JPanel gamePanel = nextGame.getGamePanel();
//        mainFrame.add(gamePanel); 
//        
//        // 3. Start the game logic
//        nextGame.init();
//        
//        // 4. THE FIX: Force the window to acknowledge the new panel's size
//        mainFrame.revalidate(); 
//        mainFrame.repaint();
//        
//        // 5. Ensure the game can hear your keyboard/mouse
//        gamePanel.setFocusable(true);
//        gamePanel.requestFocusInWindow(); 
//    }
//
//    @Override
//    public void onGameFinished() {
//        System.out.println("Transitioning to the next minigame...");
//        loadLevel(currentIndex + 1);
//    }
//}