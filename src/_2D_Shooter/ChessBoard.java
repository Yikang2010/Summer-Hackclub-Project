package _2D_Shooter;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class ChessBoard {
	private Image img;
	private AffineTransform tx;
	private double x = 0, y = 0;
	private double scaleX = 1.0, scaleY = 1.0;

	public ChessBoard() {
		img = getImage("greenChessBoard.jpg");
		tx = AffineTransform.getTranslateInstance(x, y);
	}

	// This MUST be public so Frame.java can see it!
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		tx.setToTranslation(x, y);
		tx.scale(scaleX, scaleY);
		g2.drawImage(img, tx, null);
	}

	private Image getImage(String path) {
		try {
			URL imageURL = ChessBoard.class.getResource(path);
			return Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			return null;
		}
	}
}