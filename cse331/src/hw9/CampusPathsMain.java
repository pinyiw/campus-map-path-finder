package hw9;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.*;

public class CampusPathsMain {
	
	private static JFrame frame;
	private static int x;
	private static int y;
	
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
		panel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent ev) {
				x = ev.getX();
				y = ev.getY();
			}
		});
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				int x1 = e.getXOnScreen() - x;
				int y1 = e.getYOnScreen() - y;
				panel.setLocation(x1, y1);
			}
		});
		
		JButton search = new JButton("Find Path");
		search.setActionCommand("search");
		search.addActionListener(panel);
		
		JButton reset = new JButton("Reset");
		reset.setActionCommand("reset");
		reset.addActionListener(panel);
		
		JLabel top = new JLabel("Where do you want to go?");
		JLabel label1 = new JLabel("From:");
		JLabel label2 = new JLabel("To:");
		top.setFont(new Font("", Font.BOLD, 20));
		label1.setFont(new Font("", Font.BOLD, 20));
		label2.setFont(new Font("", Font.BOLD, 20));
		
		top.setAlignmentX(Component.CENTER_ALIGNMENT);
		label1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		label2.setAlignmentX(Component.RIGHT_ALIGNMENT);
		search.setAlignmentX(Component.CENTER_ALIGNMENT);
		reset.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel control = new JPanel();
		control.setLayout(new BoxLayout(control, BoxLayout.Y_AXIS));
		control.add(top);
		control.add(label1);
		control.add(first);
		control.add(label2);
		control.add(second);
		control.add(search);
		control.add(reset);
		
		frame.add(panel, BorderLayout.CENTER);
		frame.add(control, BorderLayout.EAST);
	}
}