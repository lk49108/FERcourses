package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class inherits <code>Element</code> class.
 * It contains single read only property value.
 * Instance of this class represents a particular real number.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ElementConstantDouble extends Element {

	/**
	 * Single read-only double property. Represents
	 * the value of this real number.
	 */
	private double value;

	/**
	 * Constructor which assigns value to
	 * value instance variable.
	 * @param value Double type parameter which value is being assigned.
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	/**
     * Getter for value instance variable as text/String.
     * @Override
     * @return value instance variable as a String.
     */
	public String asText() {
		return Double.toString(value);
	}

}
