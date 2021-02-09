package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class DemoFrame2 extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public DemoFrame2() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		setSize(202 + 18 + 14, 200 + 12 + 37);
		setLocationRelativeTo(null);
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		
		cp.add(l("1,1"), new RCPosition(1,1));
		cp.add(l("1,6"), new RCPosition(1,6));
		cp.add(l("1,7"), new RCPosition(1,7));
		
		for(int i = 2; i < 6; i++) {
			for(int j = 1; j < 8; j++) {
				cp.add(l(i + "," + j), new RCPosition(i,j));
			}
		}
	}
	
	private JLabel l(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		
		return l;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> {
			new DemoFrame2().setVisible(true);
		});
	}

}
