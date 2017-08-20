package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class inherits <code>Element</code> class.
 * It contains single read only property name.
 * Instance of this class represents a particular variable.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ElementVariable extends Element {

	/**
	 * Single read-only String property. Represents
	 * the name of variable.
	 */
	private String name;

	/**
	 * Getter for {@link #name}.
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * Constructor which assigns value to
	 * name instance variable.
	 * @param name String type parameter which value is being assigned.
	 */
	public ElementVariable(String name) {
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

}
