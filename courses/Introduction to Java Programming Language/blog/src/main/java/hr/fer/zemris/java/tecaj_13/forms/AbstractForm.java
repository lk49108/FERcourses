/**
 * 
 */
package hr.fer.zemris.java.tecaj_13.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Abstract form. All forms extends this class.
 * @author Leonardo Kokot
 * @version 1.0
 */
public abstract class AbstractForm {

	/**
	 * Map holding all mistakes.
	 */
	Map<String, String> mistakes = new HashMap<>();
	
	/**
	 * Checks if there is any mistake in mistakes map.
	 * @return True if statement is fulfilled, false otherwise.
	 */
	public boolean hasMistakes(){
		return !mistakes.isEmpty();
	}
	
	/**
	 * Checks if there is a mistake with provided name in mistakes map.
	 * @return True if mistake with provided key is held in mistakes map. False is returned otherwise.
	 */
	public boolean hasMistake(String name){
		return mistakes.containsKey(name);
	}
	
	/**
	 * Returns appropriate mistake from mistakes map, or null if there is no such mistake.
	 * @return Mistake descriptor obtained from mistakes map.
	 */
	public String getMistake(String name){
		return mistakes.get(name);
	}
	
	/**
	 * Prepares value.
	 * @param value Value to be prepared.
	 * @return Appropriate String.
	 */
	 String prepare(String value){
		if(value == null) return "";
		return value.trim();
	}

	/**
	 * @return the mistakes
	 */
	public Map<String, String> getMistakes() {
		return mistakes;
	}
	
	/**
	 * Fills this form from http request.
	 * @param req Http request.
	 */
	public abstract void fillFromHttpRequest(HttpServletRequest req);
	
	/**
	 * Validates form.
	 */
	public abstract void validate();
	
}
