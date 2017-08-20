package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements ShellCommand interface and 
 * represents Shell command 'tree'.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class TreeShellCommand implements ShellCommand {

	/**
	 * This instance variable holds informations about this Shell command.
	 */
	private List<String> descriptionList;
	
	/**
	 * This is a constructor which creates instance of this class.
	 */
	public TreeShellCommand(){
		descriptionList = new ArrayList<>();
		descriptionList.add("Tree command excpects a single argument, directory name.");
		descriptionList.add("Prints a tree of data structure(starting from a given"
				+ "directory.");
		descriptionList.add("Each directory level shifts output two characters to the right.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Path path = Paths.get(arguments);
		if(!Files.exists(path)){
			try {
				env.writeln("Provided argument does not "
						+ "match any file/directory.");
			} catch (IOException e) {
				e.getMessage();
			}
			return ShellStatus.CONTINUE;
		}
		if(!Files.isDirectory(path)){
			try {
				env.writeln("Provided argument does not "
						+ "match any directory.");
			} catch (IOException e) {
				e.getMessage();
			}
			return ShellStatus.CONTINUE;
		}
		printRecursively(path.toFile(), 0, env);
		return ShellStatus.CONTINUE;
	}

	/**
	 * This method is used for printing out all files contained in this specific Directory.
	 * @param file Directory which "child's" will be printed out.
	 * @param level Level in directory structure in which this specific file is situated in.
	 */
	private void printRecursively(File file, int level, Environment env) {
		print(file, level, env);
		File[] children = file.listFiles();
		if(children == null){
			return;
		}
		
	
		for(File f : children){
			if(f.isFile()){
				print(f, level + 1, env);
			} else if(f.isDirectory()){
				printRecursively(f, level + 1, env);
			}
		}
	}

	/**
	 * Method used for printing out specific file situated in directory structure.
	 * @param file File whose name is printed out.
	 * @param level Level which this file holds in data structure.
	 * @param env Shell environment through which user communicates with OS.
	 */
	private void print(File file, int level, Environment env) {
		try {
			env.writeln(String.format("%"+(2*level)+"s%s%n", "", file.getName()));
		} catch (IOException e) {
			e.getMessage();
		}
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		return descriptionList;
	}

}
