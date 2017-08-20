package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class inherits <code>Element</code> class.
 * It contains single read only property value.
 * Instance of this class represents a particular integer.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ElementConstantInteger extends Element {

	/**
	 * Single read-only int property. Represents
	 * the value of this particular integer..
	 */
	private int value;

	/**
	 * Constructor which assigns value to
	 * value instance variable.
	 * @param value Int type parameter which value is being assigned.
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	/**
     * Getter for value instance variable as text/String.
     * @Override
     * @return value instance variable as a String.
     */
	public String asText() {
		return Integer.toString(value);
	}

}
