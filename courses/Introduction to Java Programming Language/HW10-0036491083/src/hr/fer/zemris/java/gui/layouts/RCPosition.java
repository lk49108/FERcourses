/**
 * 
 */
package hr.fer.zemris.java.gui.layouts;

/**
 * This class instance represents position in
 * some 2D matrix. Position is defined with
 * instance variables(read-only properties) {@link #row} and 
 * {@link #column} with legal values greater 
 * than zero. Index of left, topmost 
 * cell in this 2D matrix is denoted with
 * row = 1 and column = 1. 
 * @author Leonardo Kokot
 *
 */
public class RCPosition {

	/**
	 * Instance variable holding row index.
	 */
	private int row;
	
	/**
	 * Instance variable holding column index.
	 */
	private int column;
	
	/**
	 * Getter for {@link #row}.
	 * @return
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Getter for {@link #column}.
	 * @return
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Constructor which creates instance of {@link RCPosition}
	 * and initializes instance variables to appropriate value
	 * given by parameters.
	 * 
	 * @param row Index of cell's row which this {@link RCPosition} instance 
	 * represents.
	 * @param column Index of cell's column which this {@link RCPosition} instance 
	 * represents.
	 * @throws IllegalArgumentException if row < 1 || column < 1.
	 */
	public RCPosition(int row, int column){
		if(row < 1 || column < 1){
			throw new IllegalArgumentException("Wrong indexes provided."
					+ " Provided row and column values ("
					+ row + ", " + column + ") should both be greater or equal than 1.");
		}
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Default constructor which delegates its job to other constructor
	 * with row and column parameters set to 1.
	 */
	public RCPosition() {
		this(1, 1);
	}
	
}
