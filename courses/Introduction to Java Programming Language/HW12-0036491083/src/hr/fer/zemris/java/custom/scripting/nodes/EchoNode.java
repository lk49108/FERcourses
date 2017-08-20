package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * A node representing a command which generates some textual output
 * dynamically.
 * It inherits from Node class.
 * It represents a tag.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class EchoNode extends Node {

	/**
	 * {@link Element} array type instance variable(reference to it).
	 * This array contains all elements of this particular tag.
	 */
	private Element[] elements;

	/**
	 * This is a constructor which creates an instance of this class.
	 * Elements of this particular tag are inserted in instance variable array.
	 * @param array {@link ArrayIndexedCollection} type parameter. Elements contained in
	 * this collection are inserted into instance variable array.
	 */
	public EchoNode(ArrayIndexedCollection array) {
		elements = new Element[array.size()];
		for (int i = 0; i < elements.length; i++) {
			if (array.get(i) instanceof ElementVariable) {
				elements[i] = (ElementVariable) array.get(i);
			} else if (array.get(i) instanceof ElementConstantInteger) {
				elements[i] = (ElementConstantInteger) array.get(i);
			} else if (array.get(i) instanceof ElementConstantDouble) {
				elements[i] = (ElementConstantDouble) array.get(i);
			} else if (array.get(i) instanceof ElementString) {
				elements[i] = (ElementString) array.get(i);
			} else if (array.get(i) instanceof ElementFunction) {
				elements[i] = (ElementFunction) array.get(i);
			} else if (array.get(i) instanceof ElementOperator) {
				elements[i] = (ElementOperator) array.get(i);
			} else {
				throw new SmartScriptParserException("Echo tag error occured.");
			}
		}
	}

	/**
	 * Getter for elements instance variable. i.e. reference to an array containing 
	 * tag elements is returned.
	 * @return elements instance variable.
	 */
	public Element[] getElements() {
		return elements;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
}
