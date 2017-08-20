package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class implements Environment interface.
 * It allows user to communicate with OS through standard input
 * and output, i.e. command line.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class EnvImplementation implements Environment{

	/**
	 * This instance variable contains prompt symbol, i.e.
	 * symbol printed out to denote that user can input next command.
	 */
	private Character promtSign;
	
	/**
	 * Instance variable containing moreLines symbol.
	 */
	private Character moreLines;
	
	/**
	 * Instance variable containing multiline symbol.
	 */
	private Character multiLineSymb;
	
	/**
	 * Instance variable, reference to Map which maps Shell commands 
	 * with a key containing command name.
	 */
	Map<String, ShellCommand> commandMap = new LinkedHashMap<>();
	
	/**
	 * This instance variable is a reader which is used for reading users input
	 * given through standard input.
	 */
	BufferedReader reader = new BufferedReader(
			new InputStreamReader(new BufferedInputStream(System.in))
				);
	
	/**
	 * Getter for promptSign instance variable.
	 * @return prompt sign char.
	 */
	public char getPromtSign() {
		return promtSign;
	}
	
	/**
	 * Setter for prompt sign instance variable.
	 * @param promtSign Character to which prompt sign is set to.
	 */
	public void setPromtSign(char promtSign) {
		this.promtSign = promtSign;
	}

	/**
	 * Getter for morelines symbol.
	 * @return morelines char.
	 */
	public char getMoreLines() {
		return moreLines;
	}

	/**
	 * Setter for morelines symbol.
	 * @param moreLines Character to which morelines symbol is set to.
	 */
	public void setMoreLines(char moreLines) {
		this.moreLines = moreLines;
	}

	/**
	 * Default constructor which delegates 
	 * its job to other constructor with 
	 * default symbols as parameters.
	 */
	public EnvImplementation(){
		this('>', '\\', '|');
	}
	
	/**
	 * Getter for specific command determined by commandName parameter.
	 */
	public ShellCommand getCommand(String commandName) {
		return commandMap.get(commandName);
	}
	
	/**
	 * Constructor which creates instance of this class.
	 * @param promtSign Character to which promptsSign instance variable will be set to.
	 * @param moreLines Character to which moreLines instance variable will be set to.
	 * @param multiLineSymb Character to which multiLineSymb instance variable will be set to.
	 */
	public EnvImplementation(char promtSign, char moreLines, char multiLineSymb) {
		super();
		this.promtSign = promtSign;
		this.moreLines = moreLines;
		this.multiLineSymb = '|';
		buildEnvironment(commandMap);
	}
	
	/**
	 * Method which is used for building environment, i.e. map of supported commands in Shell.
	 * @param commandMap Map in which commands and its corresponding keys will be stored.
	 */
	private static void buildEnvironment(Map<String, ShellCommand> commandMap) {
		commandMap.put("exit", new ExitShellCommand());
		commandMap.put("ls", new LsShellCommand());
		commandMap.put("help", new HelpShellCommand());
		commandMap.put("charsets", new CharsetsShellCommand());
		commandMap.put("cat", new CatShellCommand());
		commandMap.put("tree", new TreeShellCommand());
		commandMap.put("copy", new CopyShellCommand());
		commandMap.put("mkdir", new MkdirShellCommand());
		commandMap.put("hexdump", new HexdumpShellCommand());
		commandMap.put("symbol", new SymbolShellCommand());
	}

	@Override
	public String readLine() throws IOException {
		StringBuilder bd = new StringBuilder();
		String readedLine;
		char sign = this.promtSign;
		while(true){
			this.write("" + sign + " ");
			readedLine = reader.readLine().trim();
			if(readedLine.indexOf(this.moreLines) == readedLine.length() - 1 
					&& readedLine.lastIndexOf(' ') == readedLine.length() - 2){
				bd.append(readedLine.substring(0, readedLine.length() - 1).trim()).append(' ');
				if(sign != this.multiLineSymb){
					sign = this.multiLineSymb;
				}
				continue;
			} 
			bd.append(readedLine);
			break;
		}
		return bd.toString();
	}

	@Override
	public void write(String text) {
		System.out.print(text);
	}

	@Override
	public void writeln(String text) {
		System.out.println(text);
	}

	@Override
	public Iterator<ShellCommand> commands() {
		return  this.commandMap.values().iterator();
	}

	@Override
	public Character getMultilineSymbol() {
		return multiLineSymb;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		this.multiLineSymb = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return this.promtSign;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		this.promtSign = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return this.moreLines;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.moreLines = symbol;
	}
	
}
