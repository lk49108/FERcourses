package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class inherits <code>Element</code> class.
 * It contains single read only property name.
 * Instance of this class represents a particular function (mostly math related).
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ElementFunction extends Element {

	/**
	 * Single read-only String property. Represents
	 * the name of function.
	 */
	private String name;

	/**
	 * Constructor which assigns value to
	 * name instance variable.
	 * @param name String type parameter which value is being assigned.
	 */
	public ElementFunction(String name) {
		this.name = name;
	}

	/**
     * Getter for name instance variable.
     * @Override
     * @return name instance variable.
     */
	public String asText() {
		return name;
	}

	/**
	 * Getter for {@link #name}.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
