package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for-loop construct.
 * It inherits Node class. It contains four read-only properties.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ForLoopNode extends Node {

	/**
	 * Read only property representing a variable in For-loop construct.
	 * It is of {@link ElementVariable} type.
	 */
	private ElementVariable variable;

	/**
	 * Read only property representing a start expression in For-loop construct.
	 * It is of {@link Element} type.
	 */
	private Element startExpression;

	/**
	 * Read only property representing a end expression in For-loop construct.
	 * It is of {@link Element} type.
	 */
	private Element endExpression;

	/**
	 * Read only property representing a step expression in For-loop construct.
	 * It is of {@link Element} type.
	 * It can be null.
	 */
	private Element stepExpression;

	/**
	 * This public constructor creates a For-loop construct. It initializes 
	 * instance variables. Only stepExpression can stay uninitialized.
	 * @param array {@link ArrayIndexedCollection} type parameter. From this collection
	 * values for instance variables are taken.
	 */
	public ForLoopNode(ArrayIndexedCollection array) {
		// First argument in for tag must be ElementVariable type.
		variable = (ElementVariable) array.get(0);

		// Second instance variable.
		if (array.get(1) instanceof ElementConstantInteger) {
			startExpression = (ElementConstantInteger) array.get(1);
		} else if (array.get(1) instanceof ElementConstantDouble) {
			startExpression = (ElementConstantDouble) array.get(1);
		} else {
			startExpression = (ElementString) array.get(1);
		}

		// Third instance variable.
		if (array.get(2) instanceof ElementConstantInteger) {
			endExpression = (ElementConstantInteger) array.get(2);
		} else if (array.get(2) instanceof ElementConstantDouble) {
			endExpression = (ElementConstantDouble) array.get(2);
		} else {
			endExpression = (ElementString) array.get(2);
		}

		// Fourth instance variable.
		if (array.size() == 3) {
			stepExpression = null;
		}
		else{
			if (array.get(3) instanceof ElementConstantInteger) {
				stepExpression = (ElementConstantInteger) array.get(3);
			} else if (array.get(3) instanceof ElementConstantDouble) {
				stepExpression = (ElementConstantDouble) array.get(3);
			} else {
				stepExpression = (ElementString) array.get(3);
			}
		}
	}

	/**
	 * Getter for variable instance variable.
	 * @return variable instance variable.
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Getter for startExpression instance variable.
	 * @return startExpression instance variable.
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Getter for endExpression instance variable. 
	 * @return endExpression instance variable.
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Getter for stepExpression instance variable. 
	 * @return stepExpression instance variable.
	 */
	public Element getStepExpression() {
		if(stepExpression == null){
			throw new IllegalAccessError("stepExpression instance variable is not initialized.");
		}
		return stepExpression;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
	

}
