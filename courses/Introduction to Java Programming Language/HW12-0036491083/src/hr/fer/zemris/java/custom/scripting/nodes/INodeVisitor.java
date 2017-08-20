/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Visitor for nodes, used in 
 * Visitor design pattern.
 * @author Leonardo Kokot
 * @version 1.0
 */
public interface INodeVisitor {
	
	/**
	 * @param node
	 */
	public void visitTextNode(TextNode node);
	
	/**
	 * @param node
	 */
	public void visitForLoopNode(ForLoopNode node);
	
	/**
	 * @param node
	 */
	public void visitEchoNode(EchoNode node);
	
	/**
	 * @param node
	 */
	public void visitDocumentNode(DocumentNode node);
}
