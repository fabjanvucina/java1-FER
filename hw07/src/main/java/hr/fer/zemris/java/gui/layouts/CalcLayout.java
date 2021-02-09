package hr.fer.zemris.java.gui.layouts;

import static java.lang.Math.max; 

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * A layout manager appropriate for a calculator interface.
 * 
 * @author fabjanvucina
 */
public class CalcLayout implements LayoutManager2 {
	
	/**
	 * User defined gap between rows and columns in pixels.
	 */
	private int gap;
	
	/**
	 * Map which maps added constraints(positions) to its assigned components.
	 */
	private Map<Component, RCPosition> componentsMap;
	
	/**
	 * Public constructor with default gap set to 0.
	 */
	public CalcLayout() {
		this(0);
	}
	
	/**
	 * Public constructor.
	 * @param gap between rows and columns
	 * @throws <code>IllegalArgumentException</code> if <code>gap</code> is smaller than 0.
	 */
	public CalcLayout(int gap) {
		if (gap < 0) throw new IllegalArgumentException("Gap can't be smaller than zero.");
		
		this.gap = gap;
		this.componentsMap = new HashMap<>();
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
		
	}
	
	//by "installing" the layout manager on a container(cp.setLayout(...)), calling cp.add will call lm.addLayoutComponent
	//overriding this method is only necessary if we are working with constraints
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(constraints == null) throw new NullPointerException("You passed a null reference to contraints.");
		if(comp == null) throw new NullPointerException("You passed a null reference to comp.");
		
		if(!(constraints instanceof RCPosition) && !(constraints instanceof String)) {
			throw new IllegalArgumentException("Constraints has to be instance of String or RCPosition.");
		}
		
		
		//determine new position
		RCPosition newPos = constraints instanceof String ? RCPosition.parse((String)constraints) : (RCPosition) constraints;
		
		//check if constraint already exists
		if(componentsMap.containsValue(newPos)) {
			throw new CalcLayoutException(newPos + " is already in use.");
		}
		
		
		//check if constraint has invalid values
		int r = newPos.getRow();
		int c = newPos.getColumn();
		if((r == 1 && (c == 2 || c == 3 || c == 4 || c == 5)) || 
		    r < 1 || r > 5 || c < 1 || c > 7) {
			throw new CalcLayoutException(newPos + " is an invalid constraint. Rows: [1, 5], Columns: [1, 7]."
												 + "Also (1,2), (1,3), (1,4), (1,5) are invalid.");
		}
		
		
		//add component with its constraint to the layout
		componentsMap.put(comp, newPos);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		componentsMap.remove(comp);
	}
	
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getDimension(parent, Component::getPreferredSize);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getDimension(parent, Component::getMinimumSize);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getDimension(target, Component::getMaximumSize);
	}
	
	/**
	 * A functional interface which strategizes getting preferred, minimum and maximum dimensions for the parent container.
	 * @author fabjanvucina
	 *
	 */
	public interface ISizeGetter {
		Dimension getSize(Component c);
	}
	
	/**
	 * Private method which strategizes calculating a size for a parent container.
	 * @param parent
	 * @param sg ISizeGetter functional interface for {<code>prefered</code>, <code>maximum</code>, <code>minimum</code>} size
	 * @return
	 */
	private Dimension getDimension(Container parent, ISizeGetter sg) {
		int rowHeight = 0;
		int columnWidth = 0;
		
		for(Component c : parent.getComponents()) {
			
			//get necessary dimension
			Dimension compDim = sg.getSize(c);
			
			//calculate maximum row height and column width if dimension is defined
			if(compDim != null) {
				
				//divide width of the 1,1 - 1,5 component by 5 taking into account the "eaten up" gaps
				if(componentsMap.get(c).equals(new RCPosition(1,1))) {
					columnWidth = max(columnWidth, (compDim.width - 4 * gap) / 5);
				}
				else {
					columnWidth = max(columnWidth, compDim.width);
				}
			
				rowHeight = max(rowHeight, compDim.height);
			}
		}
		
		//calculate final layout dimension
		int layoutWidth = columnWidth * 7 + 6 * gap;
		int layoutHeight = rowHeight * 5 + 4 * gap;
		
		return new Dimension(layoutWidth, layoutHeight);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}
	
	@Override
	public void layoutContainer(Container parent) {
		Dimension parentDim = parent.getSize();
		Insets ins = parent.getInsets();
		
		//useable space
		int width = parentDim.width - ins.left - ins.right;
		int height = parentDim.height - ins.top - ins.bottom;
		
		//cell dimensions
		int columnWidth = (width-6*gap) / 7;
		int rowHeight = (height-4*gap) / 5;
		
		//calculate extra width and height due to rounding up
		int extraWidth = width - (7*columnWidth + 6*gap);
		int extraHeight = height - (5*rowHeight + 4*gap);
		
		//determine rows and columns that get an extra pixel
		Set<Integer> widerColumns = getUniformSubsetOfColumns(extraWidth);
		Set<Integer> tallerRows = getUniformSubsetOfRows(extraHeight);
		
		
		//set bounds for every component
		for(Component comp : parent.getComponents()) {
			
			//get component constraint
			RCPosition compConstraint = componentsMap.get(comp);
			int c = compConstraint.getColumn();
			int r = compConstraint.getRow();
			
			//determine actual width and height for component taking into account extra pixels
			int compWidth = widerColumns.contains(c) ? columnWidth + 1 : columnWidth;
			int compHeight = tallerRows.contains(r) ? rowHeight + 1 : rowHeight;
			
			
			//special case of 1,1 - 1,5 cell
			if(compConstraint.equals(new RCPosition(1, 1))) {
				comp.setBounds(ins.left, ins.top, 
							   columnWidth*5 + 4*gap + numberOfSmallerElementsInSet(widerColumns, 6), compHeight);
			}
			
			//e.g. (3,2)
			else {	
				
				//ins.left + 1*default_cell_width + 1*gap + potential extra pixels
				int leftBound = ins.left + max(c-1, 0) * columnWidth + max(c-1, 0) * gap + 
								numberOfSmallerElementsInSet(widerColumns, c);
				
				//ins.top + 2*default_cell_height + 2*gap + potential extra pixels
				int topBound = ins.top + max(r-1, 0) * rowHeight + max(r-1, 0) * gap +
							   numberOfSmallerElementsInSet(tallerRows, r);
				
				comp.setBounds(leftBound, topBound, compWidth, compHeight);
			}
		}
		
	}
	
	/**
	 * Private method which allocates extra width to columns in an uniform way.
	 * @param extraWidth
	 * @return set of columns that get 1 extra pixel in width
	 */
	private Set<Integer> getUniformSubsetOfColumns(int extraWidth) {
		Set<Integer> subset = getUniformSubsetOfRows(extraWidth);
		
		if(extraWidth == 1) {
			subset.add(4);
		}
		
		else if(extraWidth == 2) {
			subset.add(3);
			subset.add(5);
		}
		
		else if(extraWidth == 3) {
			subset.add(2);
			subset.add(4);
			subset.add(6);
		}
		
		else if(extraWidth == 4) {	
			subset.add(1);
			subset.add(3);
			subset.add(5);
			subset.add(7);
		}
		
		else if(extraWidth == 5) {
			subset.add(1);
			subset.add(2);
			subset.add(4);
			subset.add(6);
			subset.add(7);
		}
		
		else if(extraWidth == 6) {
			subset.add(1);
			subset.add(2);
			subset.add(3);
			subset.add(5);
			subset.add(6);
			subset.add(7);
		}
		
		return subset;
	}
	
	/**
	 * Private method which allocates extra height to rows in an uniform way.
	 * @param extraHeight
	 * @return set of rows that get 1 extra pixel in height
	 */
	private Set<Integer> getUniformSubsetOfRows(int extraHeight) {
		Set<Integer> subset = new TreeSet<>();
		
		if(extraHeight == 1) {
			subset.add(3);
		}
		
		else if(extraHeight == 2) {
			subset.add(2);
			subset.add(4);
		}
		
		else if(extraHeight == 3) {
			subset.add(1);
			subset.add(3);
			subset.add(5);
		}
		
		else if(extraHeight == 4) {	
			subset.add(1);
			subset.add(2);
			subset.add(4);
			subset.add(5);
		}
		
		return subset;
	}
	
	/**
	 * @param set
	 * @param element
	 * @return number of elements in the set smaller than specified element
	 */
	private int numberOfSmallerElementsInSet(Set<Integer> set, int element) {
		int counter = 0;
		
		for(Integer e : set) {
			if(e < element) {
				counter++;
			}
		}
		
		return counter;
	}
	
	@Override
	public void invalidateLayout(Container target) {
		//do nothing
	}	
}
