package soccerMiniGame;

import java.awt.Rectangle;

import soccer_minigame.Ball;

public class Goal {
    public int x, y, width, height;

    public Goal(int screenWidth) {
        // Aligned specifically to match the boundaries of the goal.jpg image
        this.width = 680; 
        this.x = (screenWidth - width) / 2; // Center it perfectly
        this.y = 140;                       // Top Crossbar line
        this.height = 240;                  // Vertical post length
    }

    public boolean isGoal(soccerMiniGame.Ball ball) {
        java.awt.Rectangle goalBox = new java.awt.Rectangle(x, y, width, height);
        return goalBox.intersects(ball.getBounds());
    }
}