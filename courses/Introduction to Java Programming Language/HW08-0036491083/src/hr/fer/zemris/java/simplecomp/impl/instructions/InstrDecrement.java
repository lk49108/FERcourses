package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class implements CPU's dec instruction.
 * It is consisted of one argument(register) which value is then
 * decremented for 1.
 * It also implements {@link Instruction} interface.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class InstrDecrement implements Instruction {

	/**
	 * This instance variable holds index of register taking 
	 * part in this instruction.
	 */
	private int indexReg;
	
	/**
	 * This constructor creates instance of CPU's add 
	 * instruction based on given arguments List.
	 * @param arguments
	 * @throws IllegalArgumentException if illegal parameters are provided.
	 */
	public InstrDecrement(List<InstructionArgument> arguments) {
		if(arguments.size() != 1){
			throw new IllegalArgumentException("One argument excpected.");
		}
		if(!arguments.get(0).isRegister() 
				|| RegisterUtil.isIndirect((Integer)arguments.get(0).getValue())){
			throw new IllegalArgumentException("Type mismatch for argument.");
		}
		this.indexReg = RegisterUtil.getRegisterIndex((Integer)arguments.get(0).getValue());
	}
	
	@Override
	public boolean execute(Computer computer) {
		Object value = computer.getRegisters().getRegisterValue(indexReg);
		computer.getRegisters().setRegisterValue(indexReg, (Integer)value - 1);
		return false;
	}

}
