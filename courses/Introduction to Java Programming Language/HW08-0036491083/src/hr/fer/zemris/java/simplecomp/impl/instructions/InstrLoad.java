package hr.fer.zemris.java.simplecomp.impl.instructions;


import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class implements load CPU instruction.
 * It also implements {@link Instruction} interface.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class InstrLoad implements Instruction{

	/**
	 * Index of register to which value is to be loaded.
	 */
	private int indexReg;
	
	/**
	 * Index of memory location from which
	 * value is being loaded. 
	 */
	private int indexMem;
	
	
	/**
	 * This constructor creates instance of this instruction.
	 * It initializes instance variables.
	 * @param arguments Arguments which determine this instruction.
	 * @throws IllegalArgumentException if illegal parameters are provided.
	 */
	public InstrLoad(List<InstructionArgument> arguments){
		if(arguments.size() != 2){
			throw new IllegalArgumentException("Excpected 2 arguments.");
		}
		if(!arguments.get(0).isRegister() 
				|| RegisterUtil.isIndirect((Integer)arguments.get(0).getValue())){
			throw new IllegalArgumentException("Type mismatch for argument 1");
		}
		if(!arguments.get(1).isNumber()){
			throw new IllegalArgumentException("Type mismatch for argument 2.");
		}
		this.indexReg = RegisterUtil.getRegisterIndex((Integer)arguments.get(0).getValue());
		this.indexMem = (Integer)arguments.get(1).getValue();
	}

	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setRegisterValue(this.indexReg, 
				computer.getMemory().getLocation(this.indexMem));
		return false;
	}
}
