package hr.fer.zemris.java.tecaj.hw07.shell; 

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * This program ("MyShell") is used 
 * as shell in unix OS, or cmd in
 * windows. It is used for working with files and directories 
 * on users Operating System.
 * It provides main functions used when working
 * with files and directories.
 * To get a list of all commands supported by this shell type 
 * in "help". To get more specific information about 
 * each of them separately, type in command "help name_of_function".
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class MyShell {

	/**
	 * This is the main method from which program starts its execution.
	 * @param args Command line arguments which are not used in this program.
	 */
	public static void main(String[] args) {
		Environment environment = new EnvImplementation(); 
		{
			System.out.println("Welcome to MyShell v 1.0");
		}
		try{
			while(true){
				String input = environment.readLine();
				String commandName = extractCommName(input);
				String arguments = extractArgs(input);
				ShellCommand command = environment.getCommand(commandName.toLowerCase());
				if(command == null){
					System.out.println("No such command.");
					continue;
				}
				ShellStatus status = command.executeCommand(environment, arguments);
				if(status.equals(ShellStatus.TERMINATE))
					break;
			}
		} catch (IOException ex){
			System.out.println(ex.getStackTrace());
			System.exit(1);
		}
	}

	/**
	 * This method is used for extraction
	 * of arguments from command which was provided
	 * by user through standard input.
	 * @param input String representing command.
	 * @return String in which only argument part of command is contained.
	 */
	private static String extractArgs(String input) {
		if(input.indexOf(" ") == -1 || input.indexOf(' ', 
				input.indexOf(input.indexOf('\"') + 1, '\"')) == -1){
			return null;
		}
		return input.substring(input.indexOf(" ")).trim();
	}

	/**
	 * This method is used for extraction of
	 * command name out of the command.
	 * @param input String representing whole command.
	 * @return String in which is contained only name of 
	 * provided command.
	 */
	private static String extractCommName(String input){
		if(input.indexOf(" ") == -1){
			return input;
		}
		return input.substring(0, input.indexOf(" "));
	}
	
	
}
