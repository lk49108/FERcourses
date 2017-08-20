package hr.fer.zemris.java.tecaj.hw07.shell;

import java.util.ArrayList;
import java.util.List;


/**
 * This class implements ShellCommand interface and 
 * represents Shell command 'exit'.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ExitShellCommand implements ShellCommand {

	/**
	 * This instance variable holds informations about this Shell command.
	 */
	private List<String> descriptionList;
	
	/**
	 * This is a constructor which creates instance of this class.
	 */
	public ExitShellCommand() {
		descriptionList = new ArrayList<>();
		descriptionList.add("This method is used for exiting Shell.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		return descriptionList;
	}

}
