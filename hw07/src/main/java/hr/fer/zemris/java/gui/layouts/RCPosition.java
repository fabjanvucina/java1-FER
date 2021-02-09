package hr.fer.zemris.java.gui.layouts;

/**
 * Row and column constraint class for <code>CalcLayout</code> layout manager.
 * 
 * @author fabjanvucina
 */
public class RCPosition {
		
	/**
	 * Read-only row property (starts at 1)
	 */
	private int row;
	
	/**
	 * Read-only column property (starts at 1)
	 */
	private int column;
	
	/**
	 * Public constructor.
	 * @param row
	 * @param column
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Factory method which parses a string into a RCPosition object. <br>
	 * The correct text format is "row, column".
	 * @param text
	 * @return new <code>RCPosition</code> object
	 * @throws <code>NullPointerException</code> if <code>text</code> is <code>null</code>.
	 * @throws <code>IllegalArgumentException</code> if <code>text</code> is not parsable.
	 */
	public static RCPosition parse(String text) {
		if(text == null) throw new NullPointerException("You passed a null reference for a RCPosition string.");
		
		String[] properties = text.split(",");
		if(properties.length != 2) throw new IllegalArgumentException(text + " is not parsable into a RCPosition object.");
		
		try {
			return new RCPosition(Integer.parseInt(properties[0].trim()), Integer.parseInt(properties[1].trim()));
		}
		catch (NumberFormatException ex) {
			throw new IllegalArgumentException(text + " is not parsable into a RCPosition object.");
		}
		
	}
	
	/**
	 * @return row 
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * @return column 
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public String toString() {
		return "RCPosition [row=" + row + ", column=" + column + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof RCPosition))
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
}
