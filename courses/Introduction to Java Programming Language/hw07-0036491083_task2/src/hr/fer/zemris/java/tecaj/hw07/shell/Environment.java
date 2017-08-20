package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;
import java.util.Iterator;

/**
 * This interface is used for
 * defining methods which has to be implemented by
 * class which implements it. Those methods are used
 * for communication between user and OS itself.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public interface Environment {
	
	/**
	 * This method is used for providing user ability to 
	 * write command in Shell. 
	 * @return String, input provided by user.
	 * @throws IOException 
	 */
	String readLine() throws IOException;
	
	/**
	 * This method is used for writing feedback,
	 * answer to user. Answer can be printed to screen, or
	 * file, etc.
	 * @param text String that will be printed out.
	 * @throws IOException
	 */
	void write(String text) throws IOException;
	
	/**
	 * Same as write, with difference that this method puts 
	 * line termination sign to the end of the last one.
	 * @param text String that will be printed out.
	 * @throws IOException 
	 */
	void writeln(String text) throws IOException;
	
	/**
	 * This method is used for returning iterator which
	 * iterates through all commands available for use.
	 * @return Iterator 
	 */
	Iterator<ShellCommand> commands();
	
	/**
	 * This method is used as a getter for multiline symbol.
	 * @return Character representing multiline symbol.
	 */
	Character getMultilineSymbol();
	
	/**
	 * This is a setter for multiline symbol. 
	 * @param symbol Symbol to which
	 * multiline symbol will be set to.
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * This is a getter for prompt symbol.
	 * @return Character containing prommpt symbol.
	 */
	Character getPromptSymbol();
	
	/**
	 * This is a setter for prompt symbol.
	 * @param symbol Character to which symbol will be set to.
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * This is a getter for morelines symbol.
	 * @return Morelines character.
	 */
	Character getMorelinesSymbol();
	
	/**
	 * This is a setter for morelines symbol.
	 * @param symbol Character to which morelines symbol will be set to.
	 */
	void setMorelinesSymbol(Character symbol);
	
	/**
	 * Getter for specific command.
	 * @param commandName String which determines
	 * which command will be returned.
	 * @return Command determined by commandName parameter.
	 */
	ShellCommand getCommand(String commandName);
}
