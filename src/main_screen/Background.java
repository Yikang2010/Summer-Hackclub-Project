package main_screen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Background {

    private BufferedImage backgroundImage;

    public Background() {
        try {
            java.net.URL imgUrl = getClass().getResource("/Screenshot 2026-05-27 125938.png");
            if (imgUrl != null) {
                backgroundImage = ImageIO.read(imgUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g, int width, int height) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, width, height, null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, width, height);
        }
    }
}