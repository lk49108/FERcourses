package hr.fer.zemris.java.graphics.shapes;

/**
 * This class implements squares.
 * Each rectangle is specified by its x, y coordinates
 * of its top-left corner and by its size.
 * Squares of size equal
 * to zero are not allowed (and they would be invisible).
 * @author Leonardo Kokot
 * @version 1.0
 */
public class Square extends Rectangle{

	/**
	 * This constructor creates an instance of a
	 * Square. 
	 * @param x x coordinate of top-left corner of this square.
	 * @param y y coordinate of top-left corner of this square.
	 * @param size Size of a rectangle.
	 * @throws IllegalArgumentException if size provided is less than 1.
	 */
	public Square(int x, int y, int size){
		super(x, y, size, size);
	}
	
	@Override
	public void setWidth(int width) {
		if(width < 1){
			throw new IllegalArgumentException("Size of square needs to be positive integer.");
		}
		this.width = width;
		this.height = width;
	}

	@Override
	public void setHeight(int height) {
		if(height < 1){
			throw new IllegalArgumentException("Size of square needs to be positive integer.");
		}
		this.height = height;
		this.width = height;
	}
}
