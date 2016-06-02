package hw9;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

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
		
		
		
		JPanel panel = new UWMap("src/hw8/data/campus_map.jpg", 4.0);
		
		String[] builds = new String[buildings.keySet().size()];
		int count = 0;
		for (String b: buildings.keySet()) {
			Location l = buildings.get(b);
			builds[count] = l.shortName() + ": " + l.longName();
			count++;
		}
		
		JComboBox first = new JComboBox(builds);
		JComboBox second = new JComboBox(builds);
		
		JButton search = new JButton("Find Path");
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] start = ((String)first.getSelectedItem()).split(": ");
				String[] dest = ((String)second.getSelectedItem()).split(": ");
				Pair<String, String> startXY = buildings.get(start[0]).xy();
				Pair<String, String> destXY = buildings.get(dest[0]).xy();
				GraphNodePath<Pair<String, String>> gnp = 
					uwcp.findPath(startXY, destXY);
				System.out.println(start[0] + " " + dest[0]);
				Graphics2D g2 = (Graphics2D) panel.getGraphics();
				g2.setColor(Color.yellow);
				System.out.println((int)Double.parseDouble(startXY.getKey()) + " " +
						(int)Double.parseDouble(startXY.getValue()));
				int startX = (int)(Double.parseDouble(startXY.getKey()) / 4.0);
				int startY = (int)(Double.parseDouble(startXY.getValue()) / 4.0);
				g2.fillOval((int)Double.parseDouble(startXY.getKey()), 
						(int)Double.parseDouble(startXY.getValue()), 100, 100);
				frame.repaint();
			}
		});
		
		JPanel control = new JPanel();
		control.setSize(new Dimension(276, 768));
		control.setBackground(Color.gray);
		control.add(first);
		control.add(second);
		control.add(search);
		control.setLayout(new BoxLayout(control, BoxLayout.Y_AXIS));
		
		frame.add(panel, BorderLayout.CENTER);
		frame.add(control, BorderLayout.EAST);
	}
}