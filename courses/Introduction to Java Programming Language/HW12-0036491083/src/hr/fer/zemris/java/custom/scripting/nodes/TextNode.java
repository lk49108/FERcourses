package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node representing a piece of textual data.
 * It inherits from Node class. It contains
 * only plain text, i.e. text which is outside of tags.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class TextNode extends Node {

	/**
	 * This instance variable represents text contained in this 
	 * TextNode type of node.
	 */
	private String text;

	/**
	 * Constructor which creates an instance of this class. It initializes
	 * text instance variable.
	 * @param text
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * Getter for text instance variable.
	 * Returns text instance variable.
	 * @return text instance variable.
	 */
	public String getText() {
		return text;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
}
