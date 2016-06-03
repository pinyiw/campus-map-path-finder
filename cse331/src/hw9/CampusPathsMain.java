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
		JComboBox<Object> first = new JComboBox<Object>();
		JComboBox<Object> second = new JComboBox<Object>();
		first.addItem("Select Buildings...");
		second.addItem("Select Buildings...");
		
		UWMap panel = new UWMap(first, second);

		JButton search = new JButton("Find Path");
		search.setActionCommand("search");
		search.addActionListener(panel);
		
		JButton reset = new JButton("Reset");
		reset.setActionCommand("reset");
		reset.addActionListener(panel);
		
		JPanel control = new JPanel();
		control.setBackground(Color.gray);
		control.add(first);
		control.add(second);
		control.add(search);
		control.add(reset);
		control.setLayout(new BoxLayout(control, BoxLayout.Y_AXIS));
		
		frame.add(panel, BorderLayout.CENTER);
		frame.add(control, BorderLayout.EAST);
	}
}