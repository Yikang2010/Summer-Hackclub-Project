//package main_screen;
//
//import main_screen.Background;
//
//import game_engine.GameState;
//import game_engine.MiniGames;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class MainScreen extends JPanel implements MiniGames {
//
//    private static final int WIDTH  = 800;
//    private static final int HEIGHT = 600;
//
//    private Background background;
//    private MainScreenUI ui;
//    private GameSelector selector;
//
//    private JButton shooterBtn;
//    private JButton chessBtn;
//    private JButton soccerBtn;
//
//    public MainScreen(GameState engine) {
//        setPreferredSize(new Dimension(WIDTH, HEIGHT));
//        setLayout(null);
//
//        background = new Background();
//        ui         = new MainScreenUI();
//        selector   = new GameSelector(engine);
//
//        shooterBtn = makeButton("Start", 300, 220);
//        chessBtn   = makeButton("Chess",      300, 320);
//        soccerBtn  = makeButton("Soccer",     300, 420);
//
//        shooterBtn.addActionListener(e -> selector.selectGame());
//        chessBtn.addActionListener(e -> selector.selectGame());
//        soccerBtn.addActionListener(e -> selector.selectGame());
//
//        add(shooterBtn);
//        add(chessBtn);
//        add(soccerBtn);
//    }
//
//    private JButton makeButton(String text, int x, int y) {
//        JButton btn = new JButton(text);
//        btn.setBounds(x, y, 200, 60);
//        btn.setFont(new Font("Monospaced", Font.BOLD, 22));
//        btn.setBackground(Color.DARK_GRAY);
//        btn.setForeground(Color.WHITE);
//        btn.setFocusPainted(false);
//        return btn;
//
//                    
//        
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        background.render(g, getWidth(), getHeight());
//        ui.drawTitle(g, getWidth());
//        ui.drawInstructions(g, getWidth(), getHeight());
//    }
//
//    @Override public JPanel getGamePanel() { return this; }
//    @Override public void init() { repaint(); }
//    @Override public void stop() {}
//}
//
package main_screen;

import game_engine.GameState;
import game_engine.MiniGames;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JPanel implements MiniGames {

    private static final int WIDTH  = 800;
    private static final int HEIGHT = 600;

    private Background background;
    private MainScreenUI ui;
    private GameSelector selector;

    private JButton playBtn;

    public MainScreen(GameState engine) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(null);

        background = new Background();
        ui         = new MainScreenUI();
        selector   = new GameSelector(engine);

        playBtn = makeButton("PLAY", 300, 320);
        playBtn.addActionListener(e -> selector.selectGame());

        add(playBtn);
    }

    private JButton makeButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 200, 60);
        btn.setFont(new Font("Monospaced", Font.BOLD, 22));
        btn.setBackground(Color.DARK_GRAY);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        background.render(g, getWidth(), getHeight());
        ui.drawTitle(g, getWidth());
        ui.drawInstructions(g, getWidth(), getHeight());
    }

    @Override public JPanel getGamePanel() { return this; }
    @Override public void init() { repaint(); }
    @Override public void stop() {}
}
