package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * A Swing frame which displays two view components listening to the same data model.
 * 
 * @author fabjanvucina
 */
public class PrimDemo extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Public constructor
	 */
	public PrimDemo() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("PrimDemo");
		setSize(500, 200);
		setLocationRelativeTo(null);
		initGUI();
	}
	
	/**
	 * A private method which initializes the components inside the frame.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		//instantiate the data model
		PrimModelList listModel = new PrimModelList();
		
		//create 2 view components and merge their selection modes
		//pass the data model to each view component
		JList<Integer> list1 = new JList<>(listModel);
		JList<Integer> list2 = new JList<>(listModel);
		list2.setSelectionModel(list1.getSelectionModel());
		
		//pass the view components to 2 scrollable panels
		JPanel p = new JPanel(new GridLayout(1, 0));
		p.add(new JScrollPane(list1));
		p.add(new JScrollPane(list2));
		cp.add(p, BorderLayout.CENTER);
		
		//create a button for adding prime numbers
		JButton addPrimeNumber = new JButton("Add next prime number!");
		cp.add(addPrimeNumber, BorderLayout.PAGE_END);
		
		addPrimeNumber.addActionListener(e->{
			listModel.next();
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new PrimDemo().setVisible(true);
		});
	}
}
