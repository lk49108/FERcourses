package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class implements CPU's echo instruction.
 * It also implements {@link Instruction} interface.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class InstrEcho implements Instruction {

	/**
	 * This instance variable holds index offset of register
	 * which pointer data is about to be displayed on screen, i.e. printed out.
	 */
	private int indexRegOffset;
	
	/**
	 * This instance variable holds information about index of register from which
	 * data is about to be displayed(or its pointer). 
	 */
	private int indexReg;
	
	/**
	 * This instance variable determines if addressing is indirect or direct. 
	 */
	private boolean indirect;
	
	/**
	 * This constructor creates instance of this echo command.
	 * @param arguments Arguments recognized from .txt file organized in list.
	 * @throws IllegalArgumentException if illegal parameters are provided.
	 */
	public InstrEcho(List<InstructionArgument> arguments) {
		if(arguments.size() != 1){
			throw new IllegalArgumentException("Expected 1 argument.");
		}
		if(!arguments.get(0).isRegister()){
			throw new IllegalArgumentException("Type mismatch for argument.");
		}
		Integer registerDescription = (Integer)arguments.get(0).getValue();
		//There is offset
		if(RegisterUtil.isIndirect(registerDescription)){
			this.indexRegOffset = RegisterUtil.getRegisterOffset(registerDescription);
			this.indirect = true;
		}
		this.indexReg = RegisterUtil.getRegisterIndex(registerDescription);
	}
	
	@Override
	public boolean execute(Computer computer) {
		if(this.indirect){
			System.out.print(computer.getMemory().getLocation(
					(int)computer.getRegisters().getRegisterValue(this.indexReg) + this.indexRegOffset));
		} else {
			Object printOutValue = computer.getRegisters().getRegisterValue(this.indexReg);
			System.out.print(printOutValue);
		}
		return false;
	}

}
