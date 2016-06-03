package hw9;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import hw5.GraphNode;
import hw7.GraphNodePath;
import hw8.Location;
import hw8.Pair;
import hw8.UWCampusPaths;
import hw8.DataParser.MalformedDataException;

public class UWMap extends JPanel implements ActionListener {
	
	private static final double SCALE = 4.0;
	private BufferedImage image = null;
	private GraphNodePath<Pair<String, String>> gnp = null;
	private JComboBox<Object> first;
	private JComboBox<Object> second;
	private UWCampusPaths uwcp;
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
	
	public UWMap(JComboBox<Object> first, JComboBox<Object> second) {
		try {
			uwcp = new UWCampusPaths("src/hw8/data/campus_buildings.dat",
					"src/hw8/data/campus_paths.dat");
		} catch (MalformedDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Location> buildings = uwcp.getBuildingsInfo();
		try {
			image = ImageIO.read(new File("src/hw8/data/campus_map.jpg"));
		} catch (Exception e) {
			System.out.println("invalid file path");
		}
		
		this.first = first;
		this.second = second;
		for (String b: buildings.keySet()) {
			Location l = buildings.get(b);
			first.addItem(l.shortName() + ": " + l.longName());
			second.addItem(l.shortName() + ": " + l.longName());
		}
		this.scale = SCALE;
		minX = 0;
		minY = 0;
		maxX = (int)(image.getWidth() / SCALE);
		maxY = (int)(image.getHeight() / SCALE);
		highlight = false;
	}
	
	private void paintLocation(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if (highlight) {
			g2.setStroke(new BasicStroke(5));
			g.setColor(Color.red);
			List<GraphNode<Pair<String, String>>> path = gnp.getPath();
			Pair<String, String> cur = path.get(0).getName();
			for (int i = 1; i < path.size(); i++) {
				Pair<String, String> next = path.get(i).getName();
				Double curX = Double.parseDouble(cur.getKey()) / SCALE;
				Double curY = Double.parseDouble(cur.getValue()) / SCALE;
				Double nextX = Double.parseDouble(next.getKey()) / SCALE;
				Double nextY = Double.parseDouble(next.getValue()) / SCALE;
				g2.drawLine(translate(curX, 0, minX), translate(curY, 0, minY), 
						translate(nextX, 0, minX), translate(nextY, 0, minY));
				cur = next;
			}
			
			int ovalSize = 20;
			g2.setColor(Color.yellow);
//			System.out.println(translate(startX, 10, minX) + " " + (int)(startY / scale - diffY));
//			System.out.println((int)(destX / scale - diffX) + " " + (int)(destY / scale - diffY));
			g2.drawOval(translate(startX, 10, minX), translate(startY, 10, minY), ovalSize, ovalSize);
			g2.drawOval(translate(destX, 10, minX), translate(destY, 10, minY), ovalSize, ovalSize);
			
		}
	}
	
	private int translate(double x, int diff, int min) {
		return (int)(x / scale - diff + min);
	}
	
	public void setPaintLocation(int startX, int startY, int destX, int destY,
						GraphNodePath<Pair<String, String>> gnp) {
		this.gnp = gnp;
		this.startX = startX;
		this.startY = startY;
		this.destX = destX;
		this.destY = destY;
//		System.out.println("(" + startX + ", " + startY + "), (" + destX + ", " + destY + ")");
		int width = image.getWidth();
		int height = image.getHeight();
		minX = - Math.min(startX, destX) - 100;
		minY = - Math.min(startY, destY) - 100;
		maxX = Math.max(startX, destX) + 50 + minX;
		maxY = Math.max(startY, destY) + 50 + minY;
		double scaleX = ((maxX - minX) / ((width / SCALE) - minX));
		double scaleY = ((maxY - minY) / ((height / SCALE) - minY));
		
//		System.out.println("minX: " + minX + " minY: " + minY);
//		System.out.println("maxX: " + maxX + " maxY: " + maxY);
		
		scale = Math.max(scaleX, scaleY);
		maxX = (int)(width / SCALE / scale);
		maxY = (int)(height / SCALE / scale);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("search")) {
			String start = ((String)first.getSelectedItem()).split(": ")[0];
			String dest = ((String)second.getSelectedItem()).split(": ")[0];
			String def = "Select Buildings...";
			if (!start.equals(def) || !dest.equals(def)) {
				if (start.equals(def)) {
					start = dest;
				} else if (dest.equals(def)) {
					dest = start;
				}
				Map<String, Location> buildings = uwcp.getBuildingsInfo();
				Pair<String, String> startXY = buildings.get(start).xy();
				Pair<String, String> destXY = buildings.get(dest).xy();
				GraphNodePath<Pair<String, String>> gnp = 
						uwcp.findPath(startXY, destXY);
				int startX = (int)(Double.parseDouble(startXY.getKey()) / SCALE);
				int startY = (int)(Double.parseDouble(startXY.getValue()) / SCALE);
				int destX = (int)(Double.parseDouble(destXY.getKey()) / SCALE);
				int destY = (int)(Double.parseDouble(destXY.getValue()) / SCALE);
				setPaintLocation(startX, startY, destX, destY, gnp);
			}
		} else if (e.getActionCommand().equals("reset")) {
			this.scale = SCALE;
			minX = 0;
			minY = 0;
			maxX = (int)(image.getWidth() / SCALE);
			maxY = (int)(image.getHeight() / SCALE);
			first.setSelectedIndex(0);
			second.setSelectedIndex(0);
			highlight = false;
		}
		repaint();
	}
}