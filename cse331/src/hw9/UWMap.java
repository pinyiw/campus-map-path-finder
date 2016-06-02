package hw9;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class UWMap extends JPanel {
	BufferedImage image = null;
	double scale;
	
	public UWMap(String file, double scale) {
		try {
			image = ImageIO.read(new File(file));
		} catch (Exception e) {
			System.out.println("invalid file path");
		}
		this.scale = scale;
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		int width = image.getWidth();
		int height = image.getHeight();
		g2.drawImage(image, 0, 0, (int)(width / scale), (int)(height / scale), null);
	}
}
