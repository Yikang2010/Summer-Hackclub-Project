package _2D_Shooter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Background {
    private BufferedImage backgroundImage;

    public Background() {
        try {
            URL imgUrl = getClass().getResource("/Screenshot 2026-05-27 125938.png");
            if (imgUrl != null) backgroundImage = ImageIO.read(imgUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g) {
        if (backgroundImage != null) {
            // Simply draws at 0,0. If the window grows, this stays at the top-left.
            g.drawImage(backgroundImage, 0, 0, 800, 600, null);
        }
    }
}