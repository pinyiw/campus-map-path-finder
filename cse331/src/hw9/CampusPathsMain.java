package hw9;

import java.awt.*;

import javax.swing.*;

public class CampusPathsMain {
	public static void main(String[] args) {
		JFrame frame = new JFrame("A simple window");
		JPanel panel = new SimplePainting();
		panel.setPreferredSize((new Dimension(300, 200)));
		JLabel label = new JLabel("Smile!");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		frame.add(panel, BorderLayout.CENTER);
		frame.add(label, BorderLayout.SOUTH);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

class SimplePainting extends JPanel {

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.green);
		g2.fillOval(40, 30, 120, 100);
		g2.setColor(Color.red);
		g2.drawRect(60, 50, 60, 60);
	}
}