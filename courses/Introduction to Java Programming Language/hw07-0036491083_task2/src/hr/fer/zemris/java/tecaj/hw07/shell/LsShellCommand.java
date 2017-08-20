package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * This class implements ShellCommand interface and 
 * represents Shell command 'ls'.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class LsShellCommand implements ShellCommand {

	/**
	 * This instance variable holds informations about this Shell command.
	 */
	private List<String> descriptionList;
	
	/**
	 * This is a constructor which creates instance of this class.
	 */
	public LsShellCommand(){
		descriptionList = new ArrayList<>();
		descriptionList.add("Command ls takes a single argument –"
				+ " directory – and writes a directory listing (not recursive).");
		descriptionList.add("The output consists of 4 columns.");
		descriptionList.add("First column indicates if current object is directory (d), readable (r),"
				+ "writable (w) and executable (x).");
		descriptionList.add("Second column contains object size in bytes that is right aligned and"
				+ " occupies 10 characters.");
		descriptionList.add("Third collumn contains file creation date/time.");
		descriptionList.add("Fourth column contains file name.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments == null){
			try {
				env.writeln("Directory path argument should be provided.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ShellStatus.CONTINUE;
		}
		if(arguments.indexOf('\"') == -1 && arguments.indexOf(' ') != -1){
			try {
				env.writeln("If path consists of white space characters, "
						+ "path should be written inside of quotes.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ShellStatus.CONTINUE;
		}
		if(arguments.indexOf('\"') != -1){
			arguments = arguments.substring(1, arguments.lastIndexOf('\"'));
		}
		Path path = Paths.get(arguments);
		if(!Files.exists(path)) {
			try {
				env.writeln("Provided directory does not exist.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ShellStatus.CONTINUE;
		}
		if(!Files.isDirectory(path) ) {
			try {
				env.writeln("Provided argument does not represent directory.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ShellStatus.CONTINUE;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Files.list(path)
			.forEach(p -> System.out.println(outputSetter(p, sdf)));
			return ShellStatus.CONTINUE;
		} catch (IOException ex){
			System.out.println(ex.getStackTrace());
			return ShellStatus.CONTINUE;
		}
	}

	private String outputSetter(Path path, SimpleDateFormat dateFormat){
		StringBuilder builder = new StringBuilder();
		if(Files.isDirectory(path)){
			builder.append('d');
		} else {
			builder.append('-');
		}
		if(Files.isReadable(path)){
			builder.append('r');
		} else {
			builder.append('-');
		}
		if(Files.isWritable(path)){
			builder.append('w');
		} else {
			builder.append('-');
		}
		if(Files.isExecutable(path)){
			builder.append('x');
		} else {
			builder.append('-');
		}
		try{
			builder.append(String.format("%10d", Files.size(path))).append(' ');
			BasicFileAttributeView faView = Files.getFileAttributeView(
					path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
					);
			BasicFileAttributes attributes = faView.readAttributes();
			FileTime fileTime = attributes.creationTime();
			String formattedDateTime = dateFormat.format(new Date(fileTime.toMillis()));
			builder.append(formattedDateTime).append(' ');
			return builder.append(path.toFile().getName()).toString();
		} catch (IOException ex){
			return "Error occured during setting the output for " + path.toString();
		}
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		return descriptionList;
	}

}
