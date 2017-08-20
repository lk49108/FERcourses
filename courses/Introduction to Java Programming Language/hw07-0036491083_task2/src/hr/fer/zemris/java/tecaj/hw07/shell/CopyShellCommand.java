package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * This class implements ShellCommand interface and 
 * represents Shell command 'copy'.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class CopyShellCommand implements ShellCommand {

	/**
	 * This instance variable holds informations about this Shell command.
	 */
	private List<String> descriptionList;
	
	/**
	 * This is a constructor which creates instance of this class.
	 */
	public CopyShellCommand() {
		descriptionList = new ArrayList<>();
		descriptionList.add("The copy command expects two arguments: source file name and destination file name (i.e. paths and"
				+ "names).");
		descriptionList.add("If destination file exists, user is asked is it allowed to overwrite it.");
		descriptionList.add("Ii works only with files.(no directories)");
		descriptionList.add("If the second argument is directory, it is assumed that user wants"
				+ " to copy the original file into that directory using the original file name.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments == null){
			try {
				env.writeln("Wrong format of " + getCommandName() + " command.");
				return ShellStatus.CONTINUE;
			} catch(IOException unhandled){
			}
		} 
		String path1 = null;
		String path2 = null;
		if(arguments.indexOf('\"') == -1){
			path1 = arguments.substring(0, arguments.indexOf(' '));
			path2 = arguments.substring(arguments.indexOf(' ')).trim();
		}
		else if(arguments.indexOf('\"') != -1 && 
				arguments.indexOf(' ') < arguments.indexOf('\"')){
			//Means that first parameter is not in quotes.
			path1 = arguments.substring(0, 
					arguments.indexOf(' '));
			path2 = arguments.substring(arguments.indexOf(' ')).trim();
		} else if(arguments.indexOf('\"') != -1 && 
				arguments.lastIndexOf(' ') > arguments.lastIndexOf('\"')
				){
			//Means that just first parameter is in quotes.
			path1 = arguments.substring(arguments.indexOf('\"') + 1, arguments.indexOf('\"', arguments.indexOf('\"') + 1));
			path2 = arguments.substring(arguments.lastIndexOf(' ')).trim();
		} else {
			//Means that both parameters are under quotes.
			path1 = arguments.substring(arguments.indexOf('\"') + 1, arguments.indexOf('\"', arguments.indexOf('\"') + 1));
			int secondQuote = arguments.indexOf('\"', arguments.indexOf('\"') + 1);
			path1 = arguments.substring(arguments.indexOf('\"') + 1, secondQuote);
			arguments = arguments.substring(secondQuote + 1).trim();
			path2 = arguments.substring(arguments.indexOf('\"') + 1, arguments.indexOf('\"', arguments.indexOf('\"') + 1)).trim();
		} 
		Path file1 = Paths.get(path1);
		Path file2 = Paths.get(path2);
		if(!(Files.exists(file1))){
			try {
				env.writeln("Given file does not exist.");
				return ShellStatus.CONTINUE;
			} catch(IOException unhandled){
				
			}
		}
		if(Files.isDirectory(file1)){
			try {
				env.writeln("File(not directory) should be provided.");
				return ShellStatus.CONTINUE;
			} catch(IOException unhandled){
				
			}
		}
		if(Files.isDirectory(file2)){
			file2 = Paths.get(path2.concat(path1.substring(path1.lastIndexOf('\\'))));
		}
		if(Files.exists(file2)){
			try {
				env.write("Provided file " + path2 + "exist.\n" 
						+ "Overwrite existing file? ");
				String readedLine = env.readLine();
				if(readedLine.toLowerCase().equals("no")) {
					return ShellStatus.CONTINUE;
				}
			} catch(IOException unhandled) {
				
			}
		}
		byte[] buffer = new byte[1024];
		try(InputStream input = new BufferedInputStream(
					new FileInputStream(
						file1.toFile()));
			OutputStream output = new BufferedOutputStream(
					new FileOutputStream(
						file2.toFile()))){
				int readedCount = 0;
				while(true) {
					readedCount = input.read(buffer);
					if(readedCount < 1) break;
					output.write(buffer, 0, readedCount);
				}
				return ShellStatus.CONTINUE;
			} catch (IOException ex){}
			return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		return descriptionList;
	}

}
