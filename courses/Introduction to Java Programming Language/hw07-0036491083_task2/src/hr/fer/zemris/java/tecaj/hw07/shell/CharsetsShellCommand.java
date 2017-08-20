package hr.fer.zemris.java.tecaj.hw07.shell;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


/**
 * This class implements ShellCommand interface and 
 * represents Shell command 'charsets'.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class CharsetsShellCommand implements ShellCommand {

	/**
	 * This instance variable holds informations about this Shell command.
	 */
	private List<String> descriptionList;
	
	/**
	 * This is a constructor which creates instance of this class.
	 */
	public CharsetsShellCommand() {
		descriptionList = new ArrayList<>();
		descriptionList.add("Command charsets takes no arguments and lists names "
				+ "of supported charsets for your Java platform");
		descriptionList.add("A single charset name is written per line.");
	}
	
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments != null){
			System.out.println("Wrong format of " + getCommandName() + " command.");
			return ShellStatus.CONTINUE;
		}
		Charset.availableCharsets().values().stream()
		.forEach(p -> System.out.println(p));
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		return descriptionList;
	}

}
