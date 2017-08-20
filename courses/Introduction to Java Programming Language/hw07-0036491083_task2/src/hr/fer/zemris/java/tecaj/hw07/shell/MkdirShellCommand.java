package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * This class implements ShellCommand interface and 
 * represents Shell command 'mkdir'.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class MkdirShellCommand implements ShellCommand {

	/**
	 * This instance variable holds informations about this Shell command.
	 */
	private List<String> descriptionList;
	
	/**
	 * This is a constructor which creates instance of this class.
	 */
	public MkdirShellCommand(){
		descriptionList = new ArrayList<>();
		descriptionList.add("The mkdir command takes a "
				+ "single argument: directory name, and creates "
				+ "the appropriate directory structure.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Path path = Paths.get(arguments);
		try {
			Files.createDirectories(path);
		} catch (Exception ex) {
			ex.getMessage();
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		return descriptionList;
	}

}
