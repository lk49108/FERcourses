/**
 * 
 */
package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

/**
 * This class implements {@link LayoutManager2} interface
 * and is used as a layout manager.
 * Constraints with which this class works with are
 * instances of {@link RCPosition} instances.
 * For placing components {@link CalcLayout} works
 * with 2D matrix with dimensions defined by {@link #DEFAULT_DIMENSION}
 * static variable. Component added to {@link Container} with
 * {@link RCPosition} constraint set to (1,1) covers {@link #WIDTH_OF_FIRST_ELEMENT}
 * 2D matrix cells starting from position (1,1) and spreading 
 * to cell defined with (1, {@link #WIDTH_OF_FIRST_ELEMENT}) (included).
 * Consequently all {@link RCPosition} constraints 
 * set to (1, x) where x is integer between 2 and 
 * {@link #WIDTH_OF_FIRST_ELEMENT} (included)
 * are not allowed.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class CalcLayout implements LayoutManager2 {
	
	
	/**
	 * Defines width (in columns) of first element in 
	 * {@link Container} layout using {@link CalcLayout}.
	 * Should be less than {@link #DEFAULT_DIMENSION}
	 * width + 1 and greater than 1.
	 */
	private final static int WIDTH_OF_FIRST_ELEMENT = 5;
	
	/**
	 * Dimensions of a 2D matrix with which this {@link CalcLayout}
	 * layout manager works with.
	 */
	private final static Dimension DEFAULT_DIMENSION = new Dimension(7, 5);
	
	/**
	 * Instance variable used for mapping {@link JComponent} instances
	 * with their position in matrix.
	 */
	Map<Component, RCPosition> map;
	
	/**
	 * Represents vertical gaps between two {@link JComponent} instances
	 * added to specific {@link Container}.
	 */
	private int verGap;
	
	/**
	 * Represents horizontal gaps between two {@link JComponent} instances
	 * added to specific {@link Container}.
	 */
	private int horGap;
	
	/**
	 * Constructor which creates instance of this class
	 * and initializes {@link #horGap} and {@link #verGap}
	 * instance variables to value specified by corresponding 
	 * parameters. It also initializes {@link #map} instance variable.
	 * @param verGap Parameter which defines how many pixels will
	 * be inserted between two {@link JComponent} instances (vertically) in Container
	 * layout.
	 * @param horGap Parameter which defines how many pixels will
	 * be inserted between two {@link JComponent} instances (horizontally) in Container
	 * layout.
	 * @throws IllegalArgumentException If any of provided gaps is less than zero.
	 */
	public CalcLayout(int verGap, int horGap) {
		if(verGap < 0 || horGap < 0){
			throw new IllegalArgumentException("Gaps can not be values < 0");
		}
		this.verGap = verGap;
		this.horGap = horGap;
		this.map = new HashMap<Component, RCPosition>();
	}
	
	/**
	 * Constructor which creates instance of this class and initializes
	 * {@link #horGap} and {@link #verGap} to equal integer got
	 * as a parameter.
	 * @param verAndHorGap Value to which {@link #verGap} and {@link #horGap} are
	 * set to.
	 */
	public CalcLayout(int verAndHorGap){
		this(verAndHorGap, verAndHorGap);
	}
	
	/**
	 * Default constructor which delegates its job to
	 * other constructor with parameters set to 0, 
	 * which means there will be no gaps between components in {@link Container}
	 * layout.
	 */
	public CalcLayout() {
		this(0, 0);
	}

	@Override
	public void addLayoutComponent(String string, Component comp) {
		//Does nothing cause constraints are obligatory.
	}

	@Override
	public void layoutContainer(Container parent) {
		
		//I did not add gaps between edges of container and elements also...Was
		//not sure if we must add them that or not, but I guess that
		//both solutions are ok.
		
		Insets insets = parent.getInsets();
		
		int numOfComponents = parent.getComponentCount();
		
		if(numOfComponents == 0) return;
		
		Dimension size = parent.getSize();
		
		int totalWidth = size.width - insets.left - insets.right;
		int totalHeight = size.height - insets.top - insets.bottom;
		
		//Cell dimensions without padding
		int cellWidth = (totalWidth - (DEFAULT_DIMENSION.width - 1) * horGap)
				/ DEFAULT_DIMENSION.width;
		int cellHeight = (totalHeight - (DEFAULT_DIMENSION.height - 1) * verGap)
				/ DEFAULT_DIMENSION.height;
		
		for(int i = 0; i < numOfComponents; i++){
			Component comp = parent.getComponent(i);
			RCPosition position = map.get(comp);
			
			int x = insets.left + (map.get(comp).getColumn() - 1) * horGap 
					+ (map.get(comp).getColumn() - 1) * cellWidth;
			
			int y = insets.top + (map.get(comp).getRow() - 1) * verGap 
					+ (map.get(comp).getRow() - 1) * cellHeight;
			int width = 0;
			
			if(position.getRow() == 1 && position.getColumn() == 1){
				width = (WIDTH_OF_FIRST_ELEMENT - 1) * horGap + cellWidth * WIDTH_OF_FIRST_ELEMENT;
			} else {
				width = cellWidth;
			}
			
			int height = cellHeight;
			
			comp.setBounds(x, y, width, height);
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getLayoutSize(parent, "min");
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getLayoutSize(parent, "def");
	}
	
	@Override
	public Dimension maximumLayoutSize(Container parent) {
		return getLayoutSize(parent, "max");
	}
	
	/**
	 * Calculates {@link #preferredLayoutSize(Container)}
	 * if given parameter preferred equals true and
	 * {@link #minimumLayoutSize(Container)} 
	 * if given parameter preferred equals false.
	 * @param parent Container which layout size is being calculated.
	 * @param type Parameter which defines if this method calculates 
	 * preferred layout size, minimum layout size or maximum layout size.
	 * @return Dimension instance defining dimensions of {@link Container},
	 * given by parameter parent, layout.
	 */
	private Dimension getLayoutSize(Container parent, String type) {
		Dimension largestOrSmallestCell = getLargestOrSmallestCellSize(parent, type);
		Insets insets = parent.getInsets();
		Dimension layoutDimension = new Dimension(
				insets.left + insets.right 
				+ largestOrSmallestCell.width * DEFAULT_DIMENSION.width
				+ this.horGap * (DEFAULT_DIMENSION.width - 1)
				, insets.top + insets.bottom 
				+ largestOrSmallestCell.height * DEFAULT_DIMENSION.height
				+ this.verGap * (DEFAULT_DIMENSION.height - 1)
				);
		return layoutDimension;
	}

	/**
	 * Method which calculates 
	 * dimensions of largest(or smallest) component in {@link Container} given
	 * by parameter parent.
	 * @param parent {@link Container} instance for which dimensions of largest component 
	 * are calculated (preferred or minimum, depends on parameter preferred). 
	 * @param type Parameter which defines if this method calculates 
	 * maximum of (preferred size or minimum size) or minimum (of maximum size) of components in given parent.
	 * @return Maximum(minimum or preferred) or minimum(if maximum size is calculated) dimension of components in {@link Container} parent.
	 */
	private Dimension getLargestOrSmallestCellSize(Container parent, String type) {
		
		Dimension extremeCellSize = null;
		
		if(type.equals("max")){
			extremeCellSize = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
		} else {
			extremeCellSize = new Dimension(0, 0);
		}
			
		int numOfElem = parent.getComponentCount();
		
		for(int i = 0; i < numOfElem; i++) {
			Component component = parent.getComponent(i);
		
			if(component != null) {
				RCPosition position = map.get(component);
			
				if(position != null){
					Dimension componentSize;
				
					if(type.equals("pref")) {
						componentSize = component.getPreferredSize();
					} else if (type.equals("min")){
						componentSize = component.getMinimumSize();
					} else {
						componentSize = component.getMaximumSize();
					}
					
					if(componentSize == null) continue;
					
					if(position.getColumn() == 1 && position.getRow() == 1){
						//First element...
						if(type.equals("max")){
							extremeCellSize.width = Math.min(extremeCellSize.width,
									componentSize.width / WIDTH_OF_FIRST_ELEMENT);
							extremeCellSize.height = Math.min(extremeCellSize.height,
									componentSize.height);
						} else {
							extremeCellSize.width = Math.max(extremeCellSize.width,
									componentSize.width / WIDTH_OF_FIRST_ELEMENT);
							extremeCellSize.height = Math.max(extremeCellSize.height,
									componentSize.height);
						}
					} else {
						if(type.equals("max")){
							extremeCellSize.width = Math.min(extremeCellSize.width,
									componentSize.width);
							extremeCellSize.height = Math.min(extremeCellSize.height,
									componentSize.height);
						} else {
							extremeCellSize.width = Math.max(extremeCellSize.width,
									componentSize.width);
							extremeCellSize.height = Math.max(extremeCellSize.height,
									componentSize.height);
						}
					}
				}
			}
		}
		
		return extremeCellSize;
}

	@Override
	public void removeLayoutComponent(Component comp) {
		this.map.remove(comp);
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraint) {
		
		if(constraint == null) {
			throw new IllegalArgumentException("Constraing can not be null reference.");
		}

		RCPosition position = null;
		if(constraint instanceof String){
			position = calculatePosition_String((String)constraint);
		} else if(constraint instanceof RCPosition){
			position = (RCPosition)constraint;
		} else {
			throw new IllegalArgumentException("Constraint should be either"
					+ " String or RCPosition instance.");
		}
		
		map.put(comp, position);
	}

	/**
	 * Calculates {@link RCPosition} constraint from given {@link String} 
	 * constraint.
	 * @param constraint String representation of constraint.
	 * @return {@link RCPosition} instance constraint calculated
	 * from given {@link String} constraint.
	 */
	private RCPosition calculatePosition_String(String constraint) {

		String[] rowAndColumn = constraint.trim().split(",");
		
		if(rowAndColumn.length != 2) {
			throw new IllegalArgumentException("Wrong format of given string"
					+ " constraint.");
		}
		
		int row = 0;
		int column = 0;
		
		try {
			row = Integer.parseInt(rowAndColumn[0].trim());
			column = Integer.parseInt(rowAndColumn[1].trim());
			if(row < 1 || row > DEFAULT_DIMENSION.getHeight()
					|| column < 1 || column > DEFAULT_DIMENSION.getWidth()) {
				throw new IllegalArgumentException("Wrong row and "
						+ "column indexes. -> (" + row + ", " + column + ")");
			}
			for(int i = 2; i <= WIDTH_OF_FIRST_ELEMENT; i++){
				if(column == i && row == 1) {
					throw new IllegalArgumentException("Index of element"
							+ " column should not be from number 2 to"
							+ " number " + WIDTH_OF_FIRST_ELEMENT);
				}
			}
		} catch(NumberFormatException ex) {
			throw new IllegalArgumentException("Given row " + rowAndColumn[0]
					+ " and given column " + rowAndColumn[1] + " should be integers.");
		}
		
		return new RCPosition(row, column);
	}

	@Override
	public float getLayoutAlignmentX(Container arg0) {
		return 0.5f;
	}

	@Override
	public float getLayoutAlignmentY(Container arg0) {
		return 0.5f;
	}

	@Override
	public void invalidateLayout(Container arg0) {
		//Does nothing
	}

}
