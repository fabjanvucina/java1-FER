package hr.fer.zemris.java.gui.charts;

/**
 * Bar class which contains the <code>abscissa</code> axis value and the <code>ordinate</code> axis value.
 * 
 * @author fabjanvucina
 */
public class XYValue {

	/**
	 * Horizontal value.
	 */
	private int x;
	
	/**
	 * Vertical value
	 */
	private int y;
	
	/**
	 * Public constructor.
	 * @param x
	 * @param y
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return y
	 */
	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "XYValue [x=" + x + ", y=" + y + "]";
	}
}
