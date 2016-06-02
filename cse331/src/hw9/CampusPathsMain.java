package hw9;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import hw7.GraphNodePath;
import hw8.DataParser.MalformedDataException;
import hw8.Location;
import hw8.Pair;
import hw8.UWCampusPaths;

public class CampusPathsMain {
	
	private static JFrame frame;
	private static UWCampusPaths uwcp;
	
	public static void main(String[] args) {
		frame = new JFrame("UW Campus Map");
		init();

		frame.pack();
		frame.setSize(new Dimension(1480, 768));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private static void init() {
		try {
			uwcp = new UWCampusPaths("src/hw8/data/campus_buildings.dat",
												"src/hw8/data/campus_paths.dat");
		} catch (MalformedDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Location> buildings = uwcp.getBuildingsInfo();
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("src/hw8/data/campus_map.jpg"));
		} catch (Exception e) {
			System.out.println("invalid file path");
		}
		
		Double scale = 4.0;
		UWMap panel = new UWMap(image, scale);
//		int width = (int)(image.getWidth() / 1);
//		int height = (int)(image.getHeight() / 1);
//		System.out.println("Width: " + width + " Height: " + height);
		// 1082 741
//		panel.setPreferredSize(new Dimension((int)(width / scale), (int)(height / scale)));
		
		String[] builds = new String[buildings.keySet().size()];
		int count = 0;
		for (String b: buildings.keySet()) {
			Location l = buildings.get(b);
			builds[count] = l.shortName() + ": " + l.longName();
			count++;
		}
		
		JComboBox<Object> first = new JComboBox<Object>(builds);
		JComboBox<Object> second = new JComboBox<Object>(builds);
		
		JButton search = new JButton("Find Path");
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] start = ((String)first.getSelectedItem()).split(": ");
				String[] dest = ((String)second.getSelectedItem()).split(": ");
				Pair<String, String> startXY = buildings.get(start[0]).xy();
				Pair<String, String> destXY = buildings.get(dest[0]).xy();
				GraphNodePath<Pair<String, String>> gnp = 
										uwcp.findPath(startXY, destXY);
				int startX = (int)(Double.parseDouble(startXY.getKey()) / scale);
				int startY = (int)(Double.parseDouble(startXY.getValue()) / scale);
				int destX = (int)(Double.parseDouble(destXY.getKey()) / scale);
				int destY = (int)(Double.parseDouble(destXY.getValue()) / scale);
				panel.setPaintLocation(startX, startY, destX, destY);
				frame.repaint();
			}
		});
		
		JPanel control = new JPanel();
		control.setBackground(Color.gray);
		control.add(first);
		control.add(second);
		control.add(search);
		control.setLayout(new BoxLayout(control, BoxLayout.Y_AXIS));
		
		frame.add(panel, BorderLayout.CENTER);
		frame.add(control, BorderLayout.EAST);
	}
}