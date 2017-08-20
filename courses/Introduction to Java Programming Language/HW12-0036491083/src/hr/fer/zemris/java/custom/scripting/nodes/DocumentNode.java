package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node representing an entire document.
 * It is always situated on top of the program/document structure tree.
 * It inherits from Node class.
 * It is an empty class. All it contains is inherited from Node class.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class DocumentNode extends Node {

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}

	
}
