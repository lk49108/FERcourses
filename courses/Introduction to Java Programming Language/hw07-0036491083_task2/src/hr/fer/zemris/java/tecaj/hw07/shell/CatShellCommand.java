package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * This class implements ShellCommand interface and 
 * represents Shell command 'cat'.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class CatShellCommand implements ShellCommand{

	/**
	 * This instance variable represents DEFAULT_CHARSET on running OS.
	 */
	private final String DEFAULT_CHARSET = Charset.defaultCharset().toString();

	/**
	 * This instance variable holds informations about this Shell command.
	 */
	private List<String> descriptionList;
	
	/**
	 * This is a constructor which creates instance of this class.
	 */
	public CatShellCommand() {
		descriptionList = new ArrayList<>();
		descriptionList.add("This command opens given file and writes its content to console.");
		descriptionList.add("Command cat takes one or two arguments.");
		descriptionList.add("The first argument is path to some file and is mandatory.");
		descriptionList.add("The second argument is charset name that should be used to"
				+ " interpret chars from bytes. If not provided, a default platform charset is used.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(arguments == null){
			try {
				env.writeln("Wrong format of " + getCommandName() + " command.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ShellStatus.CONTINUE;
		} 
		String path = null;
		String charset = DEFAULT_CHARSET;
		try {
			if(arguments.indexOf('\"') != -1 
					&& arguments.indexOf(' ') != -1
					&& arguments.lastIndexOf(' ') > arguments.lastIndexOf('\"')){
				path = arguments.substring(arguments.indexOf('\"') + 1, arguments.lastIndexOf('\"'));
				charset = arguments.substring(arguments.lastIndexOf(' ')).trim();
			} else if(arguments.indexOf('\"') != -1 
					&& arguments.lastIndexOf(' ') == arguments.indexOf(' ')) {
				path = arguments.substring(arguments.indexOf('\"') + 1, arguments.lastIndexOf('\"'));
			}
			else if(arguments.indexOf(' ') != -1){
				path = arguments.substring(0, arguments.indexOf(' '));
				charset = arguments.substring(arguments.indexOf(' ')).trim();
			} else {
				path = arguments;
			}
			Path file = Paths.get(path);
			if(!Files.isReadable(file)){
				env.writeln("Given path file is not readable.");
				return ShellStatus.CONTINUE;
			}
			if(!file.toFile().exists()){
				env.writeln("Given file does not exist.");
				return ShellStatus.CONTINUE;
			}
			if(!Charset.isSupported(charset)){
				env.writeln("Given charset is not supported.");
				return ShellStatus.CONTINUE;
			}
			writeData(file, charset, env);
		} catch (Exception ex) {
			try {
				env.writeln(ex.getStackTrace().toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ShellStatus.CONTINUE;
	}

	private void writeData(Path file, String charset, Environment environment) {
		try(InputStream reader = new BufferedInputStream(
				new FileInputStream(
						file.toFile()))){
			byte[] buffer = new byte[1024];
			int size;
			while(true){
				size = reader.read(buffer);
				if(size < 1) break;
				String output = new String(buffer, 0, size, Charset.defaultCharset());
				environment.writeln(output);
			}
		} catch (IOException ex) {
			try{
			environment.writeln("Error occured.");
			} catch (IOException notHandledOne) {
				
			}
		}
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		return descriptionList;
	}

}
