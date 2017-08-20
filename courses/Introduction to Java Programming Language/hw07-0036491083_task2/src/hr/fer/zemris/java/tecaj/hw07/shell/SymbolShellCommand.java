package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * This class implements ShellCommand interface and 
 * represents Shell command 'symbol'.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class SymbolShellCommand implements ShellCommand {

	/**
	 * This instance variable holds informations about this Shell command.
	 */
	private List<String> descriptionList;
	
	/**
	 * This is a constructor which creates instance of this class.
	 */
	public SymbolShellCommand(){
		descriptionList = new ArrayList<>();
		descriptionList.add("Symbol command can be used with three different parameters"
				+ " (each of those three have 2 subtypes).");
		descriptionList.add("First: with parameter \"prompt\", prints out current prompt sign.");
		descriptionList.add("Second: with parameter \"multiline\", prints out current multiline sign.");
		descriptionList.add("Third: with parameter \"morelines\", prints out current morelines sign.");
		descriptionList.add("If any of these three type of command holds aditional character"
				+ ", specific sign is changed to this new sign");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.toLowerCase().equals("prompt")){
			try {
				env.writeln("Symbol for PROMPT is: '" + env.getPromptSymbol().toString()
						+ "'");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ShellStatus.CONTINUE;
		}
		if(arguments.toLowerCase().equals("morelines")){
			try {
				env.writeln("Symbol for MORELINE is: " + "'" 
			+ env.getMorelinesSymbol().toString() + "'");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ShellStatus.CONTINUE;
		}
		if(arguments.toLowerCase().equals("multiline")){
			try {
				env.writeln("Symbol for MULTILINE is: " 
			+ "'" + env.getMultilineSymbol().toString() + "'");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ShellStatus.CONTINUE;
		}
		
		char sign = ' ';
		
		if(arguments.indexOf(' ') != -1 && arguments.indexOf(' ') == arguments.length() - 2){
			sign = arguments.charAt(arguments.length() - 1);
		}
		
		arguments = arguments.substring(0, arguments.indexOf(' '));
		
		if(arguments.toLowerCase().equals("prompt")){
			try {
				env.writeln("Symbol for PROMPT changed from: '" + env.getPromptSymbol().toString()
						+ "' to '" + sign + "'");
				env.setPromptSymbol(sign);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ShellStatus.CONTINUE;
		}
		if(arguments.toLowerCase().equals("morelines")){
			try {
				env.writeln("Symbol for MORELINES changed from: '" + env.getPromptSymbol().toString()
						+ "' to '" + sign + "'");
				env.setPromptSymbol(sign);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ShellStatus.CONTINUE;
		}
		if(arguments.toLowerCase().equals("multiline")){
			try {
				env.writeln("Symbol for MULTILINE changed from: '" + env.getPromptSymbol().toString()
						+ "' to '" + sign + "'");
				env.setPromptSymbol(sign);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		return descriptionList;
	}

}
