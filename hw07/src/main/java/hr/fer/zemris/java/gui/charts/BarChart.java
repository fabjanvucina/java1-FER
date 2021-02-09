package hr.fer.zemris.java.gui.charts;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A class which models a bar chart.
 * 
 * @author fabjanvucina
 */
public class BarChart {
	
	/**
	 * Values which represent bars in the bar chart.
	 */
	private List<XYValue> values;
	
	/**
	 * Description of the horizontal axis.
	 */
	private String abscissaDescription;
	
	/**
	 * Description of the vertical axis.
	 */
	private String ordinateDescription;
	
	/**
	 * Minimal value on the vertical axis.
	 */
	private int minOrdinateValue;
	
	/**
	 * Maximal value on the vertical axis.
	 */
	private int maxOrdinateValue;
	
	/**
	 * Gap between values on the vertical axis.
	 */
	private int ordinateGap;

	/**
	 * Public constructor.
	 * @param values
	 * @param abscissaDescription
	 * @param ordinateDescription
	 * @param minOrdinateValue
	 * @param maxOrdinateValue
	 * @param ordinateGap
	 * @throws NullPointerException if the values array or one of the descriptions is <code>null</code>.
	 * @throws IllegalArgumentException if minimal ordinate value is negative,
	 * 									if the maximal ordinate value is smaller than the minimal or
	 * 									if one of the ordinate values in the values array is smaller than the minimal ordinate value.
	 */
	public BarChart(List<XYValue> values, String abscissaDescription, String ordinateDescription, int minOrdinateValue, 
					int maxOrdinateValue, int ordinateGap) {
		
		if(values == null) throw new NullPointerException("You passed a null reference for bar values.");
		if(abscissaDescription == null) throw new NullPointerException("You passed a null reference for the abscissaDescription.");
		if(ordinateDescription == null) throw new NullPointerException("You passed a null reference for the ordinateDescription.");
		
		if(minOrdinateValue < 0) 
			throw new IllegalArgumentException("Minimal ordinate value should not be negative.");
		if(maxOrdinateValue < minOrdinateValue) 
			throw new IllegalArgumentException("Maximal ordinate value should not be smaller than the minimal value.");
		
		
		//all y-components of values have to be bigger than minOrdinateValue
		checkOrdinatesOfValues(minOrdinateValue, values);
		
		//sort incoming array by x value
		values = values.stream()
					   .sorted((v1,v2) -> Integer.valueOf(v1.getX()).compareTo(Integer.valueOf(v2.getX())))
					   .collect(Collectors.toList());
		this.values = values;		
		
		
		this.abscissaDescription = abscissaDescription;
		this.ordinateDescription = ordinateDescription;
		this.minOrdinateValue = minOrdinateValue;
		this.maxOrdinateValue = maxOrdinateValue;

		this.ordinateGap = (maxOrdinateValue - minOrdinateValue) < ordinateGap ? maxOrdinateValue - minOrdinateValue : ordinateGap;
	}

	/**
	 * Private method which checks if any of the values in the array has the ordinate value smaller than the <code>minOrdinateValue</code>.
	 * @param minOrdinateValue
	 * @param values
	 * @throws IllegalArgumentException if minimal ordinate value is negative,
	 * 									if the maximal ordinate value is smaller than the minimal or 
	 * 									if one of the ordinate values in the values array is smaller than the minimal ordinate value.
	 */
	private void checkOrdinatesOfValues(int minOrdinateValue, List<XYValue> values) {
		for(XYValue value : values) {
			if(value.getY() < minOrdinateValue) 
				throw new IllegalArgumentException(value + " has the ordinate value smaller than the minimal value.");
		}	
	}

	/**
	 * @return bar values list
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * @return abscissaDescription
	 */
	public String getAbscissaDescription() {
		return abscissaDescription;
	}

	/**
	 * @return ordinateDescription
	 */
	public String getOrdinateDescription() {
		return ordinateDescription;
	}

	/**
	 * @return minOrdinateValue
	 */
	public int getMinOrdinateValue() {
		return minOrdinateValue;
	}

	/**
	 * @return maxOrdinateValue
	 */
	public int getMaxOrdinateValue() {
		return maxOrdinateValue;
	}

	/**
	 * @return ordinateGap
	 */
	public int getOrdinateGap() {
		return ordinateGap;
	}
}
