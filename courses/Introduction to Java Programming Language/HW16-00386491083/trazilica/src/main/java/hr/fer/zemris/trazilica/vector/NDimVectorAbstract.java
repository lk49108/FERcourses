/**
 * 
 */
package hr.fer.zemris.trazilica.vector;

/**
 * Abstract class, used as a root class for multidimensional vectors classes.
 * @author Leonardo Kokot
 * @version 1.0
 */
public abstract class NDimVectorAbstract {

	/**
	 * Array of n-dimensional values for this vector.
	 */
	protected double[] dimensions;
		
	/**
	 * Calculate scalar provided between two provided vectors.
	 * @param vector1 Vector 1
	 * @param vector2 Vector 2
	 * @return Scalar product.
	 */
	public static double scalarProduct(NDimVectorAbstract vector1, NDimVectorAbstract vector2){
		if(vector1.size() != vector2.size()){
			throw new IllegalArgumentException("Vectors are not of same dimensions.");
		}
		
		double numerator = 0;
		double denominator1 = 0;
		double denominator2 = 0;
		
		
		for(int i = 0; i < vector1.size(); i++){
			numerator += vector1.getDimension(i) * vector2.getDimension(i);
			denominator1 += vector1.getDimension(i) * vector1.getDimension(i);
			denominator2 += vector2.getDimension(i) * vector2.getDimension(i);
		}
		
		denominator1 = Math.sqrt(denominator1);
		denominator2 = Math.sqrt(denominator2);

		return numerator / (denominator1 * denominator2);
	}
	
	/**
	 * Returns n-th dimension of this vector.
	 * @param n Dimension which is being obtained.
	 * @throws IllegalArgumentException if n is not integer from 0 to 
	 * dimension of this vector - 1.
	 * @return n-th dimension of this vector.
	 */
	public double getDimension(int n){
		if(n < 0 || n >= this.dimensions.length){
			throw new IllegalArgumentException("Wrong n, it should be value between 0 and " + (dimensions.length - 1) + " included.");
		}
		return this.dimensions[n];
	}
	
	/**
	 * @return the dimensions
	 */
	public double[] getDimensions() {
		return dimensions;
	}

	/**
	 * @param dimensions the dimensions to set
	 */
	public void setDimensions(double[] dimensions) {
		this.dimensions = dimensions;
	}
	
	/**
	 * Returns size of this vector, i.e. its dimension.
	 * @return Size of this vector.
	 */
	public int size(){
		return this.dimensions.length;
	}

	
	
}
