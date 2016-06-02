package hw9;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class UWMap extends JPanel {
	private BufferedImage image = null;
	private double scale;
	private boolean highlight;
	private int startX;
	private int startY;
	private int destX;
	private int destY;
	private int minX;
	private int minY;
	private int maxX;
	private int maxY;
	
	public UWMap(BufferedImage image, double scale) {
		this.image = image;
		this.scale = scale;
		int width = image.getWidth();
		int height = image.getHeight();
		minX = 0;
		minY = 0;
		maxX = (int)(width / scale);
		maxY = (int)(height / scale);
		highlight = false;
	}
	
	private void paintLocation(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if (highlight) {
			int ovalSize = 20;
			g2.setColor(Color.yellow);
			g2.setStroke(new BasicStroke(5));
//			System.out.println(translate(startX, 10, minX) + " " + (int)(startY / scale - diffY));
//			System.out.println((int)(destX / scale - diffX) + " " + (int)(destY / scale - diffY));
			g2.drawOval(translate(startX, 10, minX), translate(startY, 10, minY), ovalSize, ovalSize);
			g2.drawOval(translate(destX, 10, minX), translate(destY, 10, minY), ovalSize, ovalSize);
		}
	}
	
	private int translate(int x, int diff, int min) {
		return (int)(x / scale - diff + min);
	}
	
	public void setPaintLocation(int startX, int startY, int destX, int destY) {
		this.startX = startX;
		this.startY = startY;
		this.destX = destX;
		this.destY = destY;
//		System.out.println("(" + startX + ", " + startY + "), (" + destX + ", " + destY + ")");
		int width = image.getWidth();
		int height = image.getHeight();
		minX = - Math.min(startX, destX) - 50;
		minY = - Math.min(startY, destY) - 50;
		maxX = Math.max(startX, destX) + 50 + minX;
		maxY = Math.max(startY, destY) + 50 + minY;
		double scaleX = ((maxX - minX) / ((width / 4.0) - minX));
		double scaleY = ((maxY - minY) / ((height / 4.0) - minY));
		
//		System.out.println("minX: " + minX + " minY: " + minY);
//		System.out.println("maxX: " + maxX + " maxY: " + maxY);
		
		scale = Math.max(scaleX, scaleY);
		maxX = (int)(width / 4.0 / scale);
		maxY = (int)(height / 4.0 / scale);
//		System.out.println("scaleX: " + scaleX + " scaleY: " + scaleY);
//		System.out.println("maxX: " + maxX + " maxY: " + maxY);
		highlight = true;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		
		g2.drawImage(image, minX, minY, maxX, maxY, null);
		paintLocation(g2);
	}
}
