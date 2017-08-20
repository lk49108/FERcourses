package hr.fer.zemris.java.custom.tree.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * This program accepts a file name(as a single 
 * command line argument). It opens that file(which should be 
 * smart script), parses it into a tree and reproduces
 * its(aproximate) original form onto standard output. 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class TreeWriter {

	public static void main(String[] args) throws IOException {
		if(args.length != 1){
			System.out.println("There should be one command line argument"
					+ " holding path to smartScript file provided.");
			System.exit(1);
		}
		
		Path filePath = Paths.get(args[0]);
		
		byte[] fileBytes = Files.readAllBytes(filePath);

		SmartScriptParser parser = new SmartScriptParser(new String(fileBytes));
		
		parser.getDocumentNode().accept(new WriterVisitor());
	}
	
	private static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.getText());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			StringBuilder bd = new StringBuilder();
			
			bd.append("{$ FOR ");
			bd.append(node.getVariable().asText());
			bd.append(" " + node.getStartExpression().asText());
			bd.append(" " + node.getEndExpression().asText());
			
			if(node.getStepExpression() != null){
				bd.append(" " + node.getStepExpression().asText());
			}
			
			bd.append(" $}");
			
			System.out.print(bd.toString());
			
			for(int i = 0; i < node.numberOfChildren(); i++){
				node.getChild(i).accept(this);
			}
			
			System.out.print("{ END }");
		}

		@Override
		public void visitEchoNode(EchoNode node) {			
			StringBuilder bd = new StringBuilder();
			
			bd.append("{ ");
			
			for(Element element : node.getElements()){
				bd.append(element.asText() + " ");
			}
			
			bd.append(" }");
			
			System.out.print(bd.toString());
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i = 0; i < node.numberOfChildren(); i++){
				node.getChild(i).accept(this);
			}
		}
		
	}
	
}
