package hr.fer.zemris.java.tecaj.hw07.shell;

/**
 * This enumeration holds two constants which are used
 * for defining if Shell will terminate itself or
 * it will continue getting commands from user.
 * @author Leonardo Kokot
 * @version 1.0
 */
public enum ShellStatus {
	
	/**
	 * If this enumeration constant is returned, 
	 * another command will be requested from user.
	 */
	CONTINUE, 
	
	/**
	 * If this enumeration constant is returned, Shell will terminate itself.
	 */
	TERMINATE;
}
