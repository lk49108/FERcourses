package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * This class implements ShellCommand interface and 
 * represents Shell command 'hexdump'.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class HexdumpShellCommand implements ShellCommand {

	/**
	 * This instance variable holds informations about this Shell command.
	 */
	private List<String> descriptionList;
	
	/**
	 * This is a constructor which creates instance of this class.
	 */
	public HexdumpShellCommand() {
		descriptionList = new ArrayList<>();
		descriptionList.add("hexdump command expects a single argument: file name.");
		descriptionList.add("It produces hex-output of a given file.");
		descriptionList.add("At the end of each line "
				+ "only a standard subset of characters is shown; for all other characters a"
				+ "'.' is printed instead (i.e. all bytes whose value is less than 32 or "
				+ "greater than 127 are replaced with '.').");
	}
	
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.indexOf('\"') != -1){
			arguments = arguments.substring(arguments.indexOf('\"') + 1, 
					arguments.lastIndexOf('\"'));
		}
		Path path = Paths.get(arguments);
		if(!Files.exists(path)) {
			try {
				env.writeln("Given path do not exist.");
			} catch (IOException e) {}
			return ShellStatus.CONTINUE;
		}
		if(Files.isDirectory(path)){
			try {
				env.writeln("Given path represents directory.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ShellStatus.CONTINUE;
		}
		byte[] buffer = new byte[8];
		byte[] buffer2 = null;
		int readedLength = 0;
		String text = null;
		char[] arrayOfChars = null;
		int step = 0;
		try(InputStream input = new BufferedInputStream(
					new FileInputStream(
						new File(arguments)))) {
			String readed;
			while(true){
				readedLength = input.read(buffer);
				if(readedLength < 1) break;
				buffer2 = new byte[readedLength];
				System.arraycopy(buffer, 0, buffer2, 0, readedLength);
				readed = byteToHex(buffer2).toUpperCase();
				if(step % 2 == 0){
					env.write(String.format("%8s", 
							Integer.toHexString(step * 8)).replace(' ', '0')
					+ String.format("  %-24s", readed) + "|");
					step++;
					text = new String(buffer2);
				} else {
					text = text.concat(new String(buffer2));
					env.write(String.format(" %-24s", readed) + "| ");
					step++;
					arrayOfChars = text.toCharArray();
					int i = 0;
					for(char sign : arrayOfChars){
						if(sign < 32 || sign > 127){
							arrayOfChars[i] = '.';
						}
						i++;
					}
					env.writeln(new String(arrayOfChars));
				}
			}
		} catch(Exception ex){
			try {
				env.write("Eroor occured.");
			} catch (IOException unhaandled) {}
		}
		if(step % 2 == 1){
			try {
				env.write(String.format("  %24s ", "|"));
			} catch (IOException e1) {}
			arrayOfChars = text.toCharArray();
			int i = 0;
			for(char sign : arrayOfChars){
				if(sign < 32 || sign > 127){
					arrayOfChars[i] = '.';
				}
				i++;
			}
			try {
				env.writeln(new String(arrayOfChars));
			} catch (IOException e) {
				System.out.println("Error occured.");
			}
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * This method is used for turning bytes into hexadecimal
	 * format.
	 * @param bites byte array which is processed.
	 * @return String representing given byte array as a hexadecimal number.
	 */
	private static String byteToHex(byte[] bites){
		StringBuilder builder = new StringBuilder();
		for(byte b : bites){
			builder.append(String.format("%02X ", b));
		}
		return builder.toString();
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		return descriptionList;
	}

}
