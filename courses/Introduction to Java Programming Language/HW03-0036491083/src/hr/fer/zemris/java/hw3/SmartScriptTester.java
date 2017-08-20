package hr.fer.zemris.java.hw3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * This is a tester for our parser.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class SmartScriptTester {

	/**
	 * @param args Command line arguments.
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		if(args.length != 1){
			System.err.println("Command lines arguments are faulty.");
			System.exit(1);
		}
		String docBody = new String(
				Files.readAllBytes(Paths.get(args[0])),
				StandardCharsets.UTF_8
				);
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); // should write something like
													// original
		// content of docBody

	}

	/**
	 * This method creates original document body.
	 * @param document Reference to document(main) node in program structure.
	 * @return String representation of original content of focBody.
	 */
	private static String createOriginalDocumentBody(Node document) {
		String returnString = new String();
		if (document instanceof ForLoopNode) {
			try {
			returnString = returnString + "{$ FOR " + ((ForLoopNode) document).getVariable().asText() + " "
						+ ((ForLoopNode) document).getStartExpression().asText() + " "
						+ ((ForLoopNode) document).getEndExpression().asText() + " " + 
						((ForLoopNode) document).getStepExpression().asText() + " $}";
			} catch (IllegalAccessError ex ){
			returnString = returnString + "{$ FOR " + ((ForLoopNode) document).getVariable().asText() + " "
					+ ((ForLoopNode) document).getStartExpression().asText() + " "
					+ ((ForLoopNode) document).getEndExpression().asText() + " "
					+ " $}";
			}
		}
		int numOfSubNodes = -1;
		try{
			numOfSubNodes = document.numberOfChildren();
		} catch (NullPointerException ex){
			if (document instanceof TextNode) {
				return ((TextNode) document).getText();
			}
			if (document instanceof EchoNode) {
				String attacher = new String();
				Element[] echoElem = ((EchoNode) document).getElements();
				for (int i = 0; i < echoElem.length; i++) {
					attacher = attacher + " " + echoElem[i].asText() + " ";
				}
				return "{$ " + attacher + " $}";
			}
		}
		for (int i = 0; i < numOfSubNodes; i++) {
			returnString = returnString + createOriginalDocumentBody(document.getChild(i));
		}
		if (document instanceof ForLoopNode) {
			returnString = returnString + "{$ END $}";
		}
		return returnString;
	}

}
