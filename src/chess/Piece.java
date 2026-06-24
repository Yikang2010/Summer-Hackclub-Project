package chess;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public abstract class Piece {
    protected boolean isWhite;
    protected BufferedImage image;

    public Piece(boolean isWhite, String imagePath) {
        this.isWhite = isWhite;
        try {
            //this.image = ImageIO.read(new File(imagePath));
        	//this.image = ImageIO.read(getClass().getResource(imagePath));
            this.image = ImageIO.read(getClass().getResource("/chess/" + imagePath));
        } catch (Exception e) {
            System.out.println("Could not load image: " + imagePath);
        }
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void draw(Graphics g, int x, int y, int size) {
        if (image != null) {
            // MUST use 'size' for BOTH width and height fields!
            g.drawImage(image, x, y, size, size, null);
        }
    }

    // Every piece defines its own rules
    public abstract boolean isValidMove(int startR, int startC, int endR, int endC, Piece[][] grid);
}