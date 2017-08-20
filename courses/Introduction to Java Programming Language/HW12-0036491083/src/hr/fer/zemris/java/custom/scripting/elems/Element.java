package hr.fer.zemris.java.custom.scripting.elems;

import java.awt.Window.Type;

/**
 * This is a base class of element hierarchy.
 * Subclasses of this class represent various types of data.
 * This class contains single method.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class Element {

	/**
	 * This is the only one method of this class. 
	 * @return An empty String.
	 */
	public String asText() {
		return new String("");
	}
	
}