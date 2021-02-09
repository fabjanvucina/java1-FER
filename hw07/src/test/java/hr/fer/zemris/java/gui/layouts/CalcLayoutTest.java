package hr.fer.zemris.java.gui.layouts;

import org.junit.jupiter.api.Test; 
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class CalcLayoutTest {
	
	@Test
	public void addConstraintTest() {
		JPanel p = new JPanel(new CalcLayout(0));
		p.add(new JLabel("x"), new RCPosition(1,1));
		
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), new RCPosition(0,2)));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), new RCPosition(6,2)));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), new RCPosition(2,0)));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), new RCPosition(2,8)));
	
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), new RCPosition(1,3)));
		
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), new RCPosition(1,1)));
		
		assertThrows(NullPointerException.class, () -> p.add(new JLabel("x"), null));
		assertThrows(NullPointerException.class, () -> p.add(null, new RCPosition(2,3)));
		
		assertThrows(IllegalArgumentException.class, () -> p.add(new JLabel("x"), new Object()));
	}
	
	@Test
	public void getPreferedSizeTest() {
		JPanel p1 = new JPanel(new CalcLayout(2));
		JLabel l11 = new JLabel(""); 
		l11.setPreferredSize(new Dimension(10,30));
		JLabel l12 = new JLabel(""); 
		l12.setPreferredSize(new Dimension(20,15));
		p1.add(l11, new RCPosition(2,2));
		p1.add(l12, new RCPosition(3,3));
		Dimension dim1 = p1.getPreferredSize();
		
		JPanel p2 = new JPanel(new CalcLayout(2));
		JLabel l21 = new JLabel(""); 
		l21.setPreferredSize(new Dimension(108,15));
		JLabel l22 = new JLabel(""); 
		l22.setPreferredSize(new Dimension(16,30));
		p2.add(l21, new RCPosition(1,1));
		p2.add(l22, new RCPosition(3,3));
		Dimension dim2 = p2.getPreferredSize();
		
		assertEquals(152, dim1.width);
		assertEquals(158, dim1.height);
		
		assertEquals(152, dim2.width);
		assertEquals(158, dim2.height);
	}
}
