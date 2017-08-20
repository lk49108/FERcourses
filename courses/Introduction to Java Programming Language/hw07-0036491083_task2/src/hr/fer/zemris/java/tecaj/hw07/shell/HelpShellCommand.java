package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * This class implements ShellCommand interface and 
 * represents Shell command 'help'.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class HelpShellCommand implements ShellCommand {

	/**
	 * This instance variable holds informations about this Shell command.
	 */
	private List<String> descriptionList;
	
	/**
	 * This is a constructor which creates instance of this class.
	 */
	public HelpShellCommand() {
		descriptionList = new ArrayList<>();
		descriptionList.add("If started with no arguments, it lists names of all supported commands.");
		descriptionList.add("If started with single argument, it "
				+ "prints name and the description of selected command");
	}
	
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments == null) {
			Iterator<ShellCommand> iterator = env.commands();
			try {
				env.writeln("Available commands:");
				while(iterator.hasNext()){
					env.writeln("  " + iterator.next().getCommandName());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ShellStatus.CONTINUE;
		}
		try {
			ShellCommand command = env.getCommand(arguments.toLowerCase());
			env.writeln("Name: " + command.getCommandName());
			for(String description : command.getCommandDescription()){
				env.writeln(description);
			}
			return ShellStatus.CONTINUE;
		} catch(IndexOutOfBoundsException | IOException ex){
			try {
				env.writeln("Wrong input.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ShellStatus.CONTINUE;
		}
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		return descriptionList;
	}

}
