package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demo program which generates a bar chart from the configuration details specified in a configuration file. 
 * The path to the configuration text document has to be passed as a command line argument.
 * There are 2 demonstration configuration files in this project at <code>./src/main/resources</code>.
 * 
 * @author fabjanvucina
 */
public class BarChartDemo extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Mathematical model for the chart.
	 */
	private BarChart model;
	
	/**
	 * Path to the chart configuration file
	 */
	private String path;
	
	/**
	 * Public constructor.
	 * @param model
	 * @param path
	 */
	public BarChartDemo(BarChart model, String path) {
		this.model = model;
		this.path = path;
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("[" + model.getAbscissaDescription() + "/" + model.getOrdinateDescription() + "] Bar Chart");
		setSize(500, 500);
		setLocationRelativeTo(null);
		initGUI();
	}
	
	/**
	 * A private method which initializes the components inside the calculator frame.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		BarChartComponent c = new BarChartComponent(model, path);
		c.setOpaque(true);
		c.setBackground(Color.WHITE);
		c.setForeground(Color.PINK);
		
		cp.add(c);
	}

	public static void main(String[] args) throws IOException {
		
		// wrong number of command line arguments
		if(args.length != 1) {
			throw new IllegalArgumentException("You have to pass one config file path as a command line argument.");
		}
		
		//read configuration from the file
		String configText = Files.readString(Paths.get(args[0].trim()));
		Scanner sc = new Scanner(configText);
		
		//first line -> abscissa description
		checkHasNext(sc);
		String abscissaDescription = sc.nextLine().trim();
		
		//second line -> ordinate description
		checkHasNext(sc);
		String ordinateDescription = sc.nextLine().trim();
		
		//third line -> list of XYValue objects
		checkHasNext(sc);
		String[] valuesArray = sc.nextLine().trim().split(" ");
		List<XYValue> valuesList = new ArrayList<>();
		for(String value : valuesArray) {
			String[] valueCoordinates = value.split(",");
			if(valueCoordinates.length != 2) {
				throw new IllegalArgumentException(value + " can't be parsed into a XYValue object.");
			}
			valuesList.add(new XYValue(Integer.parseInt(valueCoordinates[0]), Integer.parseInt(valueCoordinates[1])));
		}
		
		//fourth line -> minimal ordinate value
		checkHasNext(sc);
		int minOrdinateValue = Integer.parseInt(sc.nextLine().trim());
		
		//fifth line -> maximal ordinate value
		checkHasNext(sc);
		int maxOrdinateValue = Integer.parseInt(sc.nextLine().trim());
		
		//sixth line -> ordinate gap
		checkHasNext(sc);
		int ordinateGap = Integer.parseInt(sc.nextLine().trim());
		
		//create model
		BarChart model = new BarChart(valuesList, 
									  abscissaDescription, 
									  ordinateDescription,
									  minOrdinateValue,
									  maxOrdinateValue,
									  ordinateGap);			  
		
		//invke EDT to start the frame
		SwingUtilities.invokeLater(()-> {
			new BarChartDemo(model, args[0].trim()).setVisible(true);
		});
		
	}
	
	/**
	 * Private method which throws an exception if the passed scanner can't read a new value.
	 * @param sc Scanner
	 * @throws IllegalArgumentException if <code>sc.hasNext() == false</code>
	 */
	private static void checkHasNext(Scanner sc) {
		if(!sc.hasNext()) {
			throw new IllegalArgumentException("Config file has to have 6 lines.");
		}
	}
}
