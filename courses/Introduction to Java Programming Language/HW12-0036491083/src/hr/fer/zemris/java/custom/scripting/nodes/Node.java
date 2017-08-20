package hr.fer.zemris.java.custom.scripting.nodes;



import hr.fer.zemris.java.custom.scripting.collections.ArrayIndexedCollection;

/**
 * Base class for all graph nodes.
 * Graph represents a structure of some kind of
 * a program. It is contained of Nodes (subclasses of this one).
 * @author Leonardo Kokot
 * @version 1.0
 */
public abstract class Node {
	
	/**
	 * Method used in visitor design pattern.
	 * @param visitor {@link INodeVisitor} instance.
	 */
	public abstract void accept(INodeVisitor visitor);
	
	/**
	 * {@link ArrayIndexedCollection} instance variable.
	 * It is used here as an internally managed collection of children.
	 * In other words, nodes can have children nodes(e.c. ForLoopNode).
	 * Those children are stored in this collection in order they are being added.
	 * This variable is initialized at the first call of addChildNode method in this class.
	 */
	ArrayIndexedCollection childs = null;

	/**
	 * Returns number of children contained in this node.
	 * @return number of children.
	 */
	public int numberOfChildren() {
		return childs.size();
	}

	/**
	 * Returns child node specified with index parameter.
	 * For example, if index is 2, (2. child will be returned).
	 * @param index Ordinal number of a child that is being returned.
	 * @return Child node specified with index parameter.
	 */
	public Node getChild(int index) {
		if (index >= numberOfChildren() || index < 0) {
			throw new IllegalArgumentException("Child with selected index do not exist.");
		}
		return (Node) childs.get(index);
	}

	/**
	 * Adds given child to an internally managed collection of children.
	 * It also initializes childs instance variable.
	 * @param child
	 */
	public void addChildNode(Node child) {
		if (childs == null) {
			childs = new ArrayIndexedCollection();
		}
		childs.add(child);
	}
}
