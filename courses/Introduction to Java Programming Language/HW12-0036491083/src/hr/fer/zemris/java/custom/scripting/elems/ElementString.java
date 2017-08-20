package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class inherits <code>Element</code> class.
 * It contains single read only property value.
 * Instance of this class represents a particular String.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ElementString extends Element {

	/**
	 * Single read-only String property. Represents
	 * the text contained in particular String.
	 */
	private String value;

	/**
	 * Getter for {@link #value}.
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Constructor which assigns value to
	 * value instance variable.
	 * @param value String type parameter which value is being assigned. 
	 */
	public ElementString(String value) {
		this.value = value;
	}

	 /**
     * Getter for value instance variable.
     * @Override
     * @return value instance variable.
     */
	public String asText() {
		return value;
	}

}
