package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;

import javax.xml.transform.Templates;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.exec.paramGet.PParam;
import hr.fer.zemris.java.custom.scripting.exec.paramGet.Param;
import hr.fer.zemris.java.custom.scripting.exec.paramGet.TParam;
/**
 * Program which job is to execute provided
 * smart script tree created by parsing particular file.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class SmartScriptEngine {

	/**
	 * Document(root) node of parsed tree.
	 */
	private DocumentNode documentNode;
	
	/**
	 * {@link RequestContext} instance, used for producing http request.
	 */
	private RequestContext requestContext;
	
	/**
	 * {@link ObjectMultistack} instance.
	 */
	private ObjectMultistack multistack = new ObjectMultistack();
	
	/**
	 * {@link INodeVisitor} anonymous implementation instance.
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		/**
		 * Static, final, String variable which is used as a key for all values pushed onto temporary stack in
		 * echo node processing.
		 */
		private final static String TEMPORARYSTACKKEY = "key";
		
		@Override
		public void visitTextNode(TextNode node){
			try {
				requestContext.write(node.getText());
			} catch (IOException ex) {
				System.out.println("Error while writing text node content occured.");
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String variable = node.getVariable().asText();
			Integer startValue = ((ElementConstantInteger) node.getStartExpression()).getValue();
			
			ValueWrapper variableValue = new ValueWrapper(startValue);
			
			multistack.push(variable, variableValue);
			
			int endValue = ((ElementConstantInteger) node.getEndExpression()).getValue();
			int stepValue = ((ElementConstantInteger) node.getStepExpression()).getValue();
			
			while(multistack.peek(variable).numCompare(endValue) <= 0) {
				for(int i = 0; i < node.numberOfChildren(); i++){
					node.getChild(i).accept(visitor);
				}
				
				multistack.pop(variable).increment(stepValue);
				multistack.push(variable, variableValue);
			}
			
			//Removing pushed variable
			multistack.pop(variable);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			ObjectMultistack temporaryStack = new ObjectMultistack();
			
			int i = 0;
			for(Element element : node.getElements()){
				if(i == 0){
					i++;
					continue;
				}
				if(isConstant(element, temporaryStack)){
					continue;
				}
				if(isVariable(element)){
					String varName = ((ElementVariable)element).getName();
					ValueWrapper varWrapper = new ValueWrapper(((ValueWrapper)multistack.peek(varName)).getValue());
					temporaryStack.push(TEMPORARYSTACKKEY, varWrapper);
				} else if(isOperator(element)) {
					String operator = ((ElementOperator)element).getSymbol();
					doOperation(temporaryStack, operator);
				} else {
					calcFunction(temporaryStack, element.asText());
				}
			}
			try {
				outputWhatIsLeft(temporaryStack);
			} catch(IOException ex){
					System.out.println("Error happened while output of stack residue was in progress.");
			}
		}

		/**
		 * Outputs what is left on stack.
		 * @param temporaryStack {@link ObjectMultistack} instance, i.e. stack.
		 * @throws IOException
		 */
		private void outputWhatIsLeft(ObjectMultistack temporaryStack) throws IOException {
			if(temporaryStack.isEmpty(TEMPORARYSTACKKEY)) return;
			ValueWrapper value = temporaryStack.pop(TEMPORARYSTACKKEY);
			outputWhatIsLeft(temporaryStack);
			if(value.getValue() instanceof Integer){
				requestContext.write(Integer.toString((Integer)value.getValue()));
			} else if (value.getValue() instanceof Double){
				requestContext.write(Double.toString((Double)value.getValue()));
			} else {
				requestContext.write((String)value.getValue());
			}
		}

		private void calcFunction(ObjectMultistack temporaryStack, String function) {
			if(function.equals("@sin")){
				FunctionsUtil.sin(temporaryStack, TEMPORARYSTACKKEY);
			} else if (function.equals("@decfmt")){
				FunctionsUtil.decimalFormat(temporaryStack, TEMPORARYSTACKKEY);
			} else if(function.equals("@dup")){
				FunctionsUtil.dup(temporaryStack, TEMPORARYSTACKKEY);
			} else if(function.equals("@swap")){
				FunctionsUtil.swap(temporaryStack, TEMPORARYSTACKKEY);
			} else if(function.equals("@setMimeType")){
				FunctionsUtil.setMimeType(temporaryStack, TEMPORARYSTACKKEY, requestContext);
			} else if(function.equals("@paramGet")){
				FunctionsUtil.paramGet(temporaryStack, TEMPORARYSTACKKEY, requestContext, new Param());
			} else if(function.equals("@pparamGet")){
				FunctionsUtil.paramGet(temporaryStack, TEMPORARYSTACKKEY, requestContext, new PParam());
			} else if(function.equals("@pparamSet")){
				FunctionsUtil.paramSet(temporaryStack, TEMPORARYSTACKKEY, requestContext, new PParam());
			} else if(function.equals("@pparamDel")){
				FunctionsUtil.paramDel(temporaryStack, TEMPORARYSTACKKEY, requestContext, new PParam());
			} else if(function.equals("@tparamGet")){
				FunctionsUtil.paramGet(temporaryStack, TEMPORARYSTACKKEY, requestContext, new TParam());
			} else if(function.equals("@tparamSet")){
				FunctionsUtil.paramSet(temporaryStack, TEMPORARYSTACKKEY, requestContext, new TParam());
			} else if(function.equals("@tparamDel")){
				FunctionsUtil.paramDel(temporaryStack, TEMPORARYSTACKKEY, requestContext, new TParam());
			}
		}

		/**
		 * Method which does specific operation on two topmost temporaryStack
		 * values. Operation which is executed is provided as a String parameter
		 * operation.
		 * @param temporaryStack temporaryStack reference.
		 * @param operator Operation which is executed.
		 */
		private void doOperation(ObjectMultistack temporaryStack, String operation) {
			ValueWrapper firstOperand = temporaryStack.pop(TEMPORARYSTACKKEY);
			Object secondOperand = temporaryStack.pop(TEMPORARYSTACKKEY).getValue();
			if(operation.equals("+")){
				firstOperand.increment(secondOperand);
			} else if (operation.equals("-")){
				firstOperand.decrement(secondOperand);
			} else if (operation.equals("*")){
				firstOperand.multiply(secondOperand);
			} else if (operation.equals("/")){
				firstOperand.divide(secondOperand);
			}
			temporaryStack.push(TEMPORARYSTACKKEY, firstOperand);
		}

		/**
		 * Checks if provided {@link Element} instance is {@link ElementOperator}
		 * instance.
		 * @param element {@link Element} instance which is checked for being {@link ElementOperator}
		 * instance.
		 * @return true if provided parameter is instance of {@link ElementOperator}, false
		 * is returned otherwise.
		 */
		private boolean isOperator(Element element) {
			if(element instanceof ElementOperator) return true;
			return false;
		}

		/**
		 * Checks if given element is variable.
		 * @param element {@link Element} instance which is being checked for
		 * being instance of {@link ElementVariable}.
		 * @return True if above explained statement is true, false is returned otherwise.
		 */
		private boolean isVariable(Element element) {
			if(element instanceof ElementVariable) return true;
			return false;
		}

		/**
		 * Checks if provided echo element is constant. If it is
		 * value is pushed onto provided stack and true is returned.
		 * If element provided is not a constant, false is returned instead.
		 * @param element {@link Element} instance variable for which it is checked
		 * for being 
		 * @param stack
		 * @return
		 */
		private boolean isConstant(Element element, ObjectMultistack stack) {
			if(element instanceof ElementConstantDouble){
				stack.push(TEMPORARYSTACKKEY, 
						new ValueWrapper(((ElementConstantDouble)element).getValue()));
				return true;
			}
			if(element instanceof ElementConstantInteger){
				stack.push(TEMPORARYSTACKKEY, 
						new ValueWrapper(((ElementConstantInteger)element).getValue()));
				return true;
			}
			if(element instanceof ElementString){
				stack.push(TEMPORARYSTACKKEY, 
						new ValueWrapper(((ElementString)element).getValue()));
				return true;
			}
			return false;
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i = 0; i < node.numberOfChildren(); i++){
				node.getChild(i).accept(this);
			}
		}
		};
		
		
		/**
		 * Constructor which initializes {@link #documentNode} and
		 * {@link #requestContext} instance variables.
		 * @param documentNode {@link DocumentNode} instance.
		 * @param requestContext {@link RequestContext} instance.
		 * @throws IllegalArgumentException if any of documentNode or requestContext
		 * parameters are null references.
		 */
		public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext){
			if(documentNode == null){
				throw new IllegalArgumentException("DocumentNode parameter must not be null reference.");
			}
			if(requestContext == null){
				throw new IllegalArgumentException("RequestContext parameter must not be null reference.");
			}
			this.documentNode = documentNode;
			this.requestContext = requestContext;
		}
		
		/**
		 * Starts execution of parsed tree, i.e. program which
		 * it represents.
		 */
		public void execute() {
			documentNode.accept(visitor);
		}
		
}
