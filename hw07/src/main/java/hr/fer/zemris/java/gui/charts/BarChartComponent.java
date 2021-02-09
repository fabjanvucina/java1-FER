package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComponent;

/**
 * A Swing class which draws a bar chart from a configuration file. The path to the configuration file should be passed as a command-line argument.
 * 
 * @author fabjanvucina
 */
public class BarChartComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Mathematical model for the chart.
	 */
	private BarChart model;
	
	/**
	 * Number of bars in the chart.
	 */
	private int numOfBars;
	
	/**
	 * Path to the chart configuration file
	 */
	private String path;

	/**
	 * Public constructor.
	 * @param model
	 */
	public BarChartComponent(BarChart model, String path) {
		this.model = model;
		this.numOfBars = model.getValues().size();
		this.path = path;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		//encapsulating the Graphics object into a Graphics2D object which has more fuctionalities
		Graphics2D g2D = (Graphics2D)g;
		
		//remeber default graphics values
		Font defaultFont = g.getFont();
		AffineTransform defaultTransform = g2D.getTransform();
		Stroke defaultStroke = g2D.getStroke();
		
		//define the FontMetrics object necessary for getting font attributes
		FontMetrics fm = g.getFontMetrics();
		
		//getting the maximum width necessary to display the ordinate coordinates
		int ordinateValuesWidth = fm.stringWidth(String.valueOf(model.getMaxOrdinateValue()));
		
		
		//we are going to iteratively reduce the dimensions of the drawn bar chart
		//those dimensions are represented by the Rectangle object r
		//subtract 2px from width for every bar for spacing purposes
		//subtract 40px for padding purposes on the right bound
		Insets ins = getInsets();
		Dimension dim = getSize();
		Rectangle r = new Rectangle(ins.left, 
									ins.top, 
									dim.width - ins.left - ins.right - 2 * (numOfBars - 1) - 40, 
									dim.height - ins.top - ins.bottom);
		
		//paint frame background
		if(isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(r.x, r.y, r.width + 2 * (numOfBars - 1) + 40, r.height);
		}
		
		
		drawPathLabel(g, r, fm);
		
		drawAxesDescriptionLabels(g, g2D, r, fm, defaultTransform, ordinateValuesWidth);
		
		int ordinateGapHeight = (int)(r.height * 1.0 * model.getOrdinateGap() / model.getMaxOrdinateValue());
		drawAxesCoordinates(g, r, fm, ordinateGapHeight, defaultFont);

		drawAxes(g, g2D, r, fm, defaultStroke);

		drawAxesArrows(g2D, r);	
		
		drawBars(g, r, fm, ordinateGapHeight);

	}
	
	
	/**
	 * A private method which draws the path string at the top of the component.
	 * @param g
	 * @param r 
	 * @param fm 
	 */
	private void drawPathLabel(Graphics g, Rectangle r, FontMetrics fm) {
		g.setColor(Color.BLACK);
		// x: + 20px becuase of right padding of frame
		g.drawString(path, (r.x + r.width)/2 - fm.stringWidth(path)/2  + 20, r.y + 5 + fm.getHeight());
		
		//available space after drawing path
		//top bound: 5px for padding, fm.getHeight() for font height and 30px again for padding
		updateBarsArea(r, r.x, r.y + 5 + fm.getHeight() + 30, r.width, r.height - 5 - fm.getHeight() - 30);
				
	}
	
	
	/**
	 * Private method which draws the descriptions for axes.
	 * @param g
	 * @param g2D
	 * @param r
	 * @param fm
	 * @param defaultTransform
	 * @param ordinateValuesWidth
	 */
	private void drawAxesDescriptionLabels(Graphics g, Graphics2D g2D, Rectangle r, FontMetrics fm, 
										   AffineTransform defaultTransform, int ordinateValuesWidth) {
		
		/*
		remove axes description area from the rectangle
		left Bound: 25px for padding, fm.getHeight() for "width" of rotated description, 25px for min padding and
					ordinateValuesWidth for padding(maximum width of ordinate coordinate)
		shorten height: 30px for padding, fm.getHeight() for description height and 30px for padding
		*/
		updateBarsArea(r, 
					   r.x + 25 + fm.getHeight() + 25 + ordinateValuesWidth,
				       r.y, 
				       r.width - (25 + fm.getHeight() + 25 + ordinateValuesWidth), 
				       r.height - 30 - fm.getHeight() - 30);
		
		
		//draw abscissa description
		//start x for string: middle of bar chart - half of description width
		//start y for string: abscissa y - 30 - fm.getHeight()
		g.drawString(model.getAbscissaDescription(), 
				     (r.x + r.width - fm.stringWidth(model.getOrdinateDescription())) / 2, 
				     r.y + r.height + 30 + fm.getHeight());
		
		
		//draw ordinate description
		//set larger font to match abscissa description and rotate description
		g2D.setFont( new Font( "Helvetica", Font.PLAIN, 18 ));
		AffineTransform at = AffineTransform.getQuadrantRotateInstance(3);
		g2D.setTransform(at);
		
	
		g2D.drawString(model.getOrdinateDescription(), 
					   -(r.y + r.height) + fm.stringWidth(model.getOrdinateDescription()) / 2, 
					   r.x - 25 - ordinateValuesWidth + fm.getHeight());	
		g2D.setTransform(defaultTransform);
	}
	
	
	/**
	 * Private method which draws the coordinates on the axes.
	 * @param g
	 * @param r
	 * @param fm
	 * @param ordinateGapHeight
	 * @param defaultFont
	 */
	private void drawAxesCoordinates(Graphics g, Rectangle r, FontMetrics fm, int ordinateGapHeight, Font defaultFont) {
		
		g.setFont(defaultFont);
		
		//draw ordinate coordinates
		//i is displayed coordinate
		//j is the gap counter
		for(int i = model.getMinOrdinateValue(), j = 0; 
			i <= model.getMaxOrdinateValue(); 
			i += model.getOrdinateGap(), j++) 
		{
			
			//start x for string is ordinate x - coordiante width - 4 for padding
			//start y for string makes sure that the middle of teh coordinate matches the bar height for that value 
			g.drawString(String.valueOf(i) + "-", 
						 r.x - fm.stringWidth(String.valueOf(i)) - 4, 
						 r.y + r.height - j * ordinateGapHeight + fm.getAscent() / 2);
		}
		
		//draw abscissa coordinates
		int barWidth = (int)(r.width / numOfBars);
		List<Integer> abscissaCoordinatees = model.getValues().stream()
															  .map(XYValue::getX)
															  .collect(Collectors.toList());
		
		//taking into account the borders between bars
		//start y for string: takes into account the number height and 5px for padding
		for(int i = 0; i < numOfBars; i++) {
			g.drawString(String.valueOf(abscissaCoordinatees.get(i)), 
						 r.x + (barWidth+2) / 2 + i * (barWidth+2), 
						 r.y + r.height + fm.getHeight() + 5);
		}
	}
	
	/**
	 * Private method which draws the axes for the bar chart.
	 * @param g
	 * @param g2D
	 * @param r
	 * @param fm
	 * @param defaultStroke
	 */
	private void drawAxes(Graphics g, Graphics2D g2D, Rectangle r, FontMetrics fm, Stroke defaultStroke) {
		g2D.setColor(Color.LIGHT_GRAY);
		g2D.setStroke(new BasicStroke(2));
		
		//draw the ordinate
		g.drawLine(r.x, r.y, r.x, r.y + r.height + 5);
		
		//draw the abscissa
		//end x: 10 padding for at the end for arrow and 2*numOfBars takes into accound borders between bars
		g.drawLine(r.x, r.y + r.height + 5, r.x + r.width + 2*numOfBars + 5, r.y + r.height + 5);
		
		g2D.setStroke(defaultStroke);
	}
	
	
	/**
	 * Private method which draws arrows on ends of the axes.
	 * @param g2D
	 * @param r
	 */
	private void drawAxesArrows(Graphics2D g2D, Rectangle r) {
		
		//draw ordinate arrow
		g2D.fillPolygon(new int[] {r.x - 4, r.x + 4, r.x},
						new int[] {r.y, r.y, r.y - 8}, 
						3);
		
		//draw ordinate arrow
		g2D.fillPolygon(new int[] {r.x + r.width + numOfBars + 10, r.x + r.width + numOfBars + 10, r.x + r.width + numOfBars + 10 + 5},
						new int[] {r.y + r.height + 5 - 5, r.y + r.height + 5 + 5, r.y + r.height + 5}, 
						3);
		
		//available space after drawing axes and arrows
		updateBarsArea(r, r.x + 2, r.y, r.width - 2, r.height + 4);		
	}
	
	/**
	 * Private method which draws bars in the bar chart.
	 * @param g
	 * @param r
	 * @param fm
	 * @param ordinateGapHeight
	 */
	private void drawBars(Graphics g, Rectangle r, FontMetrics fm, int ordinateGapHeight) {

		g.setColor(getForeground());
		
		//calculate bar width
		int barWidth = (int)(r.width / numOfBars);
		
		//get values marked on the ordinate
		List<Integer> gapValues = getGapValues(model.getMinOrdinateValue(), 
											   model.getMaxOrdinateValue(), 
											   model.getOrdinateGap());
		
		//draw the bars
		for(int i = 0; i < numOfBars; i++) {
			int leftBound = r.x + i * (barWidth + 2);
			
			//get bar height
			int barHeight = (int)(r.height * 1.0 * model.getValues().get(i).getY() / model.getMaxOrdinateValue());
			
			//match bar height to one of the gaps if it matches
			if(barHasGapValue(gapValues, model.getValues().get(i).getY())) {
				barHeight = model.getValues().get(i).getY() / model.getOrdinateGap() * ordinateGapHeight;
			}			
			
			int topBound = r.y + (r.height - barHeight);
			
			g.setColor(getForeground());
			g.fillRect(leftBound, topBound, barWidth, barHeight);
		}
	}
	
	
	/**
	 * Private method which updates the area allocated for the bars.
	 * @param r
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	private void updateBarsArea(Rectangle r, int x, int y, int width, int height) {
		r.setBounds(x, y, width, height);
	}
	
	
	/**
	 * Private method which returns all values marked on the ordinate.
	 * @param minOrdinateValue
	 * @param maxOrdinateValue
	 * @param ordinateGap
	 * @return
	 */
	private List<Integer> getGapValues(int minOrdinateValue, int maxOrdinateValue, int ordinateGap) {
		List<Integer> list = new ArrayList<>();
		
		for(int i = minOrdinateValue; i <= maxOrdinateValue; i += ordinateGap) {
			list.add(i);
		}
		
		return list;
	}
	
	
	/**
	 * Private method which checks if the passed value is marked on the ordinate.
	 * @param listOfGapsValues
	 * @param barOrdinate
	 * @return
	 */
	private boolean barHasGapValue(List<Integer> listOfGapsValues, int barOrdinate) {
		for(Integer gapValue : listOfGapsValues) {
			if(gapValue.intValue() == barOrdinate) {
				return true;
			}
		}
		
		return false;
	}
}
