package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class implements CPU's add instruction.
 * It also implements {@link Instruction} interface.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class InstrAdd implements Instruction {

	/**
	 * This instance variable holds index of first register which take 
	 * part in this instruction.
	 */
	private int indexReg1;
	
	/**
	 * This instance variable holds index of second register which take 
	 * part in this instruction.
	 */
	private int indexReg2;
	
	/**
	 * This instance variable holds index of third register which take 
	 * part in this instruction.
	 */
	private int indexReg3;
	
	/**
	 * This constructor creates instance of CPU's add 
	 * instruction based on given argument List.
	 * @param arguments List of arguments describing this instruction.
	 * @throws IllegalArgumentException if illegal parameters are provided.
	 */
	public InstrAdd(List<InstructionArgument> arguments) {
		if(arguments.size() != 3){
			throw new IllegalArgumentException("Three arguments were expected.");
		}
		for(int i = 0; i < 3; i++){
			if(!arguments.get(i).isRegister() || 
					RegisterUtil.isIndirect((Integer)arguments.get(i).getValue())){
				throw new IllegalArgumentException("Type mismatch for"
						+ " argument " + (i + 1) + ".");
			}
		}
		this.indexReg1 = RegisterUtil.getRegisterIndex((Integer)arguments.get(0).getValue());
		this.indexReg2 = RegisterUtil.getRegisterIndex((Integer)arguments.get(1).getValue());
		this.indexReg3 = RegisterUtil.getRegisterIndex((Integer)arguments.get(2).getValue());
	}
	
	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(indexReg1);
		Object value2 = computer.getRegisters().getRegisterValue(indexReg2);
		computer.getRegisters().setRegisterValue(indexReg3, (Integer)value1 + (Integer)value2);
		return false;
	}

}
