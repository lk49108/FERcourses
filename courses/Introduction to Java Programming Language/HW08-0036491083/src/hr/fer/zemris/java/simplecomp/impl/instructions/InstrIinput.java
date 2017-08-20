/**
 * 
 */
package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class implements CPU's input 
 * instruction.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class InstrIinput implements Instruction {

	/**
	 * This instance variable holds reader which reads user input.
	 */
	BufferedReader reader = new BufferedReader(
			new InputStreamReader(System.in)
			);
	
	/**
	 * This instance variable holds index of location in memory to which
	 * this method will store Integer from user input.
	 */
	private int location;
	
	/**
	 * This constructor creates instance of CPU's jump
	 * instruction based on given argument List.
	 * @param arguments List of arguments describing this 
	 * instruction.
	 * @throws IllegalArgumentException if illegal parameters are provided.
	 */
	public InstrIinput(List<InstructionArgument> arguments) {
		if(arguments.size() != 1){
			throw new IllegalArgumentException("One argument expected.");
		}
		if(!arguments.get(0).isNumber()){
			throw new IllegalArgumentException("Type mismatch for argument.");
		}
		this.location = (int)arguments.get(0).getValue();
	}
	
	@Override
	public boolean execute(Computer computer){
		try{
			String readed = reader.readLine().trim();
			Integer number = Integer.parseInt(readed);
			computer.getMemory().setLocation(location, number);
		} catch(IOException ex){
			System.err.println("Problem with reading from input.");
			computer.getRegisters().setFlag(false);
			return false;
		} catch(NumberFormatException ex){
			computer.getRegisters().setFlag(false);
			return false;
		}
		computer.getRegisters().setFlag(true);
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
