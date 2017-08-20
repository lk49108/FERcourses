package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class inherits <code>Element</code> class.
 * It contains single read only property symbol.
 * Instance of this class represents a particular operator.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ElementOperator extends Element {

	/**
	 * Single read-only String property. Represents
	 * the sign which represents this particular operator.
	 */
	private String symbol;

	/**
	 * Constructor which assigns value to
	 * symbol instance variable.
	 * @param symbol String type parameter which value is being assigned.
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	/**
     * Getter for symbol instance variable.
     * @Override
     * @return symbol instance variable.
     */
	public String asText() {
		return symbol;
	}

	/**
	 * Getter for {@link #symbol}.
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

}
