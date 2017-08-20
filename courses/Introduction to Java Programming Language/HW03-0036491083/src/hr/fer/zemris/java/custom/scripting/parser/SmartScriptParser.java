package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.scripting.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * This is a class of a parser.
 * It is used for parsing a text/program. It decodes structure of a program and creates a tree 
 * representation of a text/program. It is using Lexer for a production of Tokens from which then 
 * structure of a program is constructed (and it content is reconstructed). 
 * Actual parsing is delegated to separate method (in this class).
 * @author Leonardo Kokot
 * @version 1.0
 */
public class SmartScriptParser {

	/**
	 * Stack used for a production of a tree representation of a program.
	 * It is used for adding children to right node.
	 */
	ObjectStack treeBuilder;
	
	/**
	 * Instance of a lexer used for a production of tokens.
	 * It is initialized in a constructor of this class.
	 */
	Lexer lex;

	/**
	 * This is a reference to a root node of a program tree. 
	 * It is of {@link DocumentNode} type.
	 */
	DocumentNode treeDocumentNode;

	/**
	 * Constructor which accepts a string that contains document body.
	 * Instance of a lexer is created in this constructor. It is also initialized with a given
	 * parameter. This lexer is used for a production of tokens. Actual parsing is then delegated to separate method-
	 * @param documentBody String that is to be parsed.
	 */
	public SmartScriptParser(String documentBody) {
		if(documentBody.isEmpty()){
			System.err.println("Empty string as an input.");
			System.exit(-1);
		}
		lex = new Lexer(documentBody);
		parse(lex);
	}

	/**
	 * Method used for parsing.
	 * @param lex Reference to a Lexer instance which creates token.
	 * Those tokens are used in this method for a production of Nodes of tree representation of 
	 * program and it content.
	 */
	private void parse(Lexer lex) {
		//This local variable is used for not letting a production of next 
		//token if current one was not used properly yet.
		int counter = 0;
		Token returnedToken = null;
		treeBuilder = new ObjectStack();
		treeDocumentNode = new DocumentNode();
		treeBuilder.push(treeDocumentNode);
		if (counter == 0) {
			returnedToken = lex.nextToken();
			counter++;
		}
		while (!(returnedToken.getType() == TokenType.EOF)) {
			if (returnedToken.getType() == TokenType.ECHO) {
				counter--;
				ArrayIndexedCollection echoData = new ArrayIndexedCollection();
				echoData.add(typeOfElementInEcho(returnedToken));
				if (counter == 0) {
					returnedToken = lex.nextToken();
					counter++;
				}
				while (legalTagsAfterEcho(returnedToken)) {

					// Adding onto ArrayIndexedCollection element inside of echo
					// tag.
					echoData.add(typeOfElementInEcho(returnedToken));
					counter--;
					if (counter == 0) {
						returnedToken = lex.nextToken();
						counter++;
					}
					if (returnedToken.getType() == TokenType.EOF) {
						break;
					}
				}

				// Finally adding echo node to the tree.
				EchoNode freshman = new EchoNode(echoData);
				Node poped = (Node) treeBuilder.pop();
				poped.addChildNode(freshman);
				treeBuilder.push(poped);
			}

			else if (returnedToken.getType() == TokenType.FOR) {
				counter--;
				ArrayIndexedCollection forData = new ArrayIndexedCollection();
				int numberParameters = 1;
				if (counter == 0) {
					returnedToken = lex.nextToken();
					counter++;
				}
				while (legalTagsAfterFor(returnedToken)) {
					if (numberParameters > 4) {
						throw new SmartScriptParserException("For tag error.");
					}

					// Adding onto ArrayIndexedCollection element inside of for
					// tag.
					forData.add(typeOfElementInFor(returnedToken, numberParameters));
					numberParameters++;
					counter--;
					if (counter == 0) {
						returnedToken = lex.nextToken();
						counter++;
					}
					if (returnedToken.getType() == TokenType.EOF) {
						break;
					}
				}
				if(numberParameters < 3){
					throw new SmartScriptParserException("For tag error.");
				}
				// Finally adding for node to the tree.
				ForLoopNode freshman = new ForLoopNode(forData);
				Node poped = (Node) treeBuilder.pop();
				poped.addChildNode(freshman);
				treeBuilder.push(poped);
				treeBuilder.push(freshman);
			}

			else if (returnedToken.getType() == TokenType.END) {
				treeBuilder.pop();
				if (treeBuilder.isEmpty()) {
					throw new SmartScriptParserException("Wrong syntax in text. ENDs tags are not correctly placed.");
				}
				counter--;
			}

			else if (returnedToken.getType() == TokenType.TEXT) {
				addTextNode(returnedToken);
				counter--;
			}
			
			else {
				throw new SmartScriptParserException("Wrong input syntax.");
			}
			if (counter == 0) {
				returnedToken = lex.nextToken();
				counter++;
			}

		}

		if (treeBuilder.size() != 1) {
			throw new SmartScriptParserException("Error.");
		}

		return;
	}

	/**
	 * This method is used for checking what type of element(token) in a tag is the one we are currently 
	 * trying to add to EchoNode.  It is used for a production of Elements.
	 * If type of a token is not as it should be, exception is thrown.
	 * @param token Token that is being checked for its type.
	 * @return Element type reference representing a type of element in tag.
	 */
	private Element typeOfElementInEcho(Token token) {
		//I assume that tag name can be saved as a ElementVariable. Because, it 
		//is the only way it sounds logicaly to me.
		if (token.getType() == TokenType.ECHO) {
			return new ElementVariable((String) token.getValue());
		}
		if (token.getType() == TokenType.FUNCTION) {
			return new ElementFunction((String) token.getValue());
		}
		if (token.getType() == TokenType.NUMBER_DOUBLE) {
			return new ElementConstantDouble(Double.parseDouble((String) token.getValue()));
		}
		if (token.getType() == TokenType.NUMBER_INTEGER) {
			return new ElementConstantInteger(Integer.parseInt((String) token.getValue()));
		}
		if (token.getType() == TokenType.OPERATOR) {
			return new ElementOperator((String) token.getValue());
		}
		if (token.getType() == TokenType.STRING) {
			return new ElementString((String) token.getValue());
		}
		if (token.getType() == TokenType.VARIABLE) {
			return new ElementVariable((String) token.getValue());
		}
		throw new SmartScriptParserException("Error.");
	}

	/**
	 * This method is similar to typeOfElementInEcho one. It is used for 
	 * checking what type of element/token is one being situated in a FOR-loop construct.
	 * It is used for a production o Elements. If type of a token is not right, exception is thrown. 
	 * @param token Token that is being checked for its type.
	 * @param parameterOrdinal This number represents a place in which is token
	 * being situated in For-loop node. It is necessary because at different spots, defferent type of tokens
	 * are allowed.
	 * @return
	 */
	private Element typeOfElementInFor(Token token, int parameterOrdinal) {
		if (parameterOrdinal == 1) {
			if (token.getType() == TokenType.VARIABLE) {
				return new ElementVariable((String) token.getValue());
			}
			throw new SmartScriptParserException("Error.");
		}

		// Elements in for tag (other than the first one), must be of one of the
		// following type. If not Exception is thrown.
		if (token.getType() == TokenType.NUMBER_DOUBLE) {
			return new ElementConstantDouble(Double.parseDouble((String) token.getValue()));
		}
		if (token.getType() == TokenType.NUMBER_INTEGER) {
			return new ElementConstantInteger(Integer.parseInt((String) token.getValue()));
		}
		if (token.getType() == TokenType.STRING) {
			return new ElementString((String) token.getValue());
		}
		throw new SmartScriptParserException("Error.");
	}

	/**
	 * This method is being used for adding a text node to
	 * a document tree.
	 * @param token Token which value is being added to the newly made TextNode.
	 */
	private void addTextNode(Token token) {
		if (token.getType() == TokenType.TEXT) {
			TextNode freshman = new TextNode((String) token.getValue());
			Node poped = (Node) treeBuilder.pop();
			poped.addChildNode(freshman);
			treeBuilder.push(poped);
		}
	}

	/**
	 * This method is being used for checking how long legal tags, after an ECHO token was returned, are being returned.
	 * Legal in way that they are content of a tag.
	 * @param token Token which type is being checked for being legal. 
	 * @return True of token is a part of tag, false otherwise.
	 */
	private boolean legalTagsAfterEcho(Token token) {
		if (token.getType() == TokenType.VARIABLE || token.getType() == TokenType.FUNCTION
				|| token.getType() == TokenType.NUMBER_DOUBLE || token.getType() == TokenType.NUMBER_INTEGER
				|| token.getType() == TokenType.OPERATOR || token.getType() == TokenType.STRING) {
			return true;
		}
		return false;
	}

	/**
	 * This method is similar to legalTagsAfterEcho one. It is used for checking 
	 * how long are tokens which are returned from lexer legal as being part of a For-loop statement.
	 * @param token Token which is checked for being legal.
	 * @return True if returned token is legal. False is being returned otherwise.
	 */
	private boolean legalTagsAfterFor(Token token) {
		if (token.getType() == TokenType.VARIABLE || token.getType() == TokenType.NUMBER_DOUBLE
				|| token.getType() == TokenType.NUMBER_INTEGER || token.getType() == TokenType.STRING) {
			return true;
		}
		return false;
	}

	/**
	 * This is a method which is being used for getting a DocumentNode(root node of tree representation of a program) outside of
	 * this class. 
	 * @return treeDocumentNode instance variable.
	 */
	public DocumentNode getDocumentNode() {
		return treeDocumentNode;
	}
	
	 
}
