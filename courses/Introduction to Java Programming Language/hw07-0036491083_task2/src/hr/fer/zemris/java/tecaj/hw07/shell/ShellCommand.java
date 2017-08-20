package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;
import java.util.List;

/**
 * This interface is used as a root for each method which
 * is used in Shell.
 * @author Leonardo Kokot
 * @version 1.0
 */
public interface ShellCommand {

	/**
	 * This method is used for execution of specific command.
	 * @param env Environment in which this command is executed.
	 * @param arguments Arguments which defines more specific what
	 * what command is supposed to do.
	 * @return ShellStatus, CONTINUE or TERMINATE.
	 * What will be returned depends on implementation.
	 * If TERMINATE is returned, Shell stops itself from
	 * execution. 
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * This method is used for getting command name.
	 * @return Command name String.
	 */
	String getCommandName();
	
	/**
	 * This method is used for getting commands description.
	 * @return List<String> containing description of this specific command.
	 */
	List<String> getCommandDescription();
}
