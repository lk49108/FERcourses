package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used as a Map(but special one).
 * While Map allows you only to store for each key a single value, 
 * ObjectMultistack allows the user to store multiple values for same
 * key. It also provides a stack-like abstraction. Keys for  
 * ObjectMultistack are instances of the class String.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ObjectMultistack {

	/**
	 * This map is used for storing pairs (String, root of a single linked list (stack))
	 * in itself. It always returns root of a single linked list which corresponds to
	 * provided String.
	 */
	private Map<String, MultistackEntry> mapped; 
	
	/**
	 * This method is used for pushing pair(String, value) 
	 * to a stack corresponding to given key(String).
	 * @param name Key for a given value.
	 * @param valueWrapper Value given, it is of ValueWrapper type.
	 * @throws IllegalArgumentException if any of given parameters is null.
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		if(name == null || valueWrapper == null){
			throw new IllegalArgumentException("Values pushed onto stack are not allowed to be null.");
		}
		if(mapped == null){
			mapped = new HashMap<>();
		}
		if(mapped.get(name) == null){
			mapped.put(name, new MultistackEntry(valueWrapper));
			return;
		}
		MultistackEntry help = mapped.get(name);
		while(help.next != null){
			help = help.next;
		}
		help.next = new MultistackEntry(valueWrapper);
	}
	
	/**
	 * This method is used to pop(String, value) pairs of
	 * the corresponding stack.
	 * @param name Key for which corresponding ValueWrapper object will be popped  down from the stack.
	 * @throws IllegalArgumentException If there is not any value stored that corresponds to 
	 * given name.
	 */
	public ValueWrapper pop(String name) {
		try{
			return popAndPeekMethod(name, true);
		} catch(IllegalArgumentException ex) {
			throw ex;
		}
	}
	
	/**
	 * This method is used to peek(String, value) pairs of
	 * the corresponding stack.
	 * @param name Key for which corresponding ValueWrapper object will be peeked from the stack.
	 * @throws IllegalArgumentException If there is not any value stored that corresponds to 
	 * given name.
	 */
	public ValueWrapper peek(String name) {
		try{
			return popAndPeekMethod(name, false);
		} catch(IllegalArgumentException ex) {
			throw ex;
		}	
	}
	
	/**
	 * This method is used as a private helper method for peek and
	 * pop methods. 
	 * @param name Key for which corresponding value is searched.
	 * @param checkIfPop Boolean type parameter which indicates if actual method
	 * which is performing is pop or peek. If pop is the one that performs
	 * checkIgPop is true. If peek is a method that currently executes, checkIfPop 
	 * is set to false.
	 * @return ValueWrapper type object which represents ValueWrapper for actual value
	 * stored in this multiStack collection. 
	 * @throws IllegalArgumentException if there is no value that corresponds to a given
	 * name argument.
	 */
	private ValueWrapper popAndPeekMethod(String name, boolean checkIfPop){
		if(mapped.get(name) == null){
			throw new IllegalArgumentException("There is no value with corresponding"
					+ "String value equal to " + name + ".");
		}
		MultistackEntry searcher = mapped.get(name);
		MultistackEntry searcherBefore = null;
		if(searcher.next == null && checkIfPop){
			mapped.remove(name);
			return searcher.value;
		}
		while(searcher.next != null){
			searcherBefore = searcher;
			searcher = searcher.next;
		}
		ValueWrapper returnWrapper = searcher.value;
		if(checkIfPop){
			searcherBefore.next = null;
		}
		return returnWrapper;
	}
	
	/**
	 * This method checks if stack for given key is empty, i.e. 
	 * it does not exist actually.
	 * @param name Key for which it is checked if there is any value that
	 * corresponds to it.
	 * @return False if stack for given name is empty. True is returned otherwise. 
	 */
	public boolean isEmpty(String name){
		if(mapped == null){
			return true;
		}
		if(mapped.get(name) == null){
			return true;
		}
		return false;
	}
	
	/**
	 * Instance of this, private, static, nested class
	 * is used as a node in a single linked list.
	 * It holds reference to next node and {@link MultistackEntry} reference
	 * to value stored in specific node.
	 * @author Leonardo Kokot
	 * @version 1.0
	 *
	 */
	private static class MultistackEntry{
		
		/**
		 * This instance variable is a reference to next node of 
		 * a single linked list. 
		 */
		private MultistackEntry next;
		
		/**
		 * This instance variable holds 
		 * value stored in this specific node of a single linked list.
		 */
		private ValueWrapper value;

		/**
		 * This constructor creates an instance of this class which is used as 
		 * a node in a single linked list. 
		 * @param value ValueWrapper type reference which is assigned to value instance variable.
		 * It represents data stored in node of a single linked list.
		 */
		public MultistackEntry(ValueWrapper value) {
			this.value = value;
		}
		
		
	}
}
