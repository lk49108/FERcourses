/**
 * 
 */
package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class implements CPU's move instruction.
 * This method is used for setting value stored
 * in specific register to specific value.
 * It also implements {@link Instruction} interface.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class InstrMove implements Instruction {

	/**
	 * This instance variable holds index of register
	 * which value will be changed.
	 */
	private int indexReg1;
	
	/**
	 * This instance variable holds value
	 * which will be moved to 
	 * index determined by indexReg1, or
	 * index of register from which value will be moved to
	 * this second register.
	 * 
	 */
	private int numb2;

	/**
	 * This instance value determines if 
	 * addressing used in this command(first argument) is 
	 * direct or indirect.
	 */
	private boolean inDirectAdr;
	
	/**
	 * This instance value determines 
	 * if addressing used in this command(second argument) is 
	 * direct or indirect.
	 */
	private boolean inDirectAdr2;
	
	/**
	 * This instance variable holds information whether second argument
	 * is number.
	 */
	private boolean isNumber;
	
	/**
	 * This instance variable holds appropriate value if first argument 
	 * uses indirect addressing.
	 */
	private int indirectValue1;
	
	/**
	 * This instance variable holds appropriate value if second argument 
	 * uses indirect addressing.
	 */
	private int indirectValue2;
	
	/**
	 * This constructor creates instance of CPU's jump
	 * instruction based on given argument List.
	 * @param arguments List of arguments describing this 
	 * instruction.
	 * @throws IllegalArgumentException if illegal parameters are provided.
	 */
	public InstrMove(List<InstructionArgument> arguments) {
		if(arguments.size() != 2){
			throw new IllegalArgumentException("Two arguments excpected.");
		}
		if(!arguments.get(0).isRegister()){
			throw new IllegalArgumentException("Type mismatch for argument one.");
		}
		if(arguments.get(1).isString()){
			throw new IllegalArgumentException("Type mismatch for argument two.");
		}
		doArgument1(arguments.get(0));
		doArgument2(arguments.get(1));
	}
	
	/**
	 * This method determines value which will be moved to 
	 * register specified by indexReg1 instance variable.
	 * @param instructionArgument  Argument 
	 * based on which this method determines 
	 * value which will be moved to register specified by
	 * indexReg1 instance variable.
	 */
	private void doArgument2(InstructionArgument instructionArgument) {
		if(instructionArgument.isRegister()){
			if(RegisterUtil.isIndirect((Integer)instructionArgument.getValue())){
				this.indirectValue2 = RegisterUtil.getRegisterOffset((Integer)instructionArgument.getValue());
				this.inDirectAdr2 = true;
			}
			this.numb2 = RegisterUtil.getRegisterIndex((Integer)instructionArgument.getValue());
		} else {
			this.numb2 = (Integer)instructionArgument.getValue();
			this.isNumber = true;
		}
	}

	/**
	 * This method determines index of register to
	 * which specific value will be moved.
	 * @param instructionArgument Argument 
	 * based on which this method determines 
	 * index of register to which specific value will be moved. 
	 */
	private void doArgument1(InstructionArgument instructionArgument) {
		if(RegisterUtil.isIndirect((Integer)instructionArgument.getValue())){
			this.indirectValue1 = RegisterUtil.getRegisterOffset(
							(Integer)instructionArgument.getValue());
			this.inDirectAdr = true;
		}
		this.indexReg1 = RegisterUtil.getRegisterIndex(
				(Integer)instructionArgument.getValue());
	}

	@Override
	public boolean execute(Computer computer) {
		if(!this.inDirectAdr){
			if(this.isNumber){
				computer.getRegisters().setRegisterValue(this.indexReg1, this.numb2);
			} else if(!this.inDirectAdr2) {
				computer.getRegisters().setRegisterValue(this.indexReg1, computer.getRegisters().getRegisterValue(this.numb2));
			} else {
				computer.getRegisters().setRegisterValue(this.indexReg1, computer.getMemory().getLocation((int)computer.getRegisters().getRegisterValue(this.numb2)
						+ this.indirectValue2));
			}
		} else {
			int locationMemory = (int)computer.getRegisters().getRegisterValue(this.indexReg1);
			if(this.isNumber){
				computer.getMemory().setLocation(locationMemory + this.indirectValue1, this.numb2);
			} else if(!this.inDirectAdr2) {
				computer.getMemory().setLocation(locationMemory + this.indirectValue1, computer.getRegisters().getRegisterValue(this.numb2));
			} else {
				computer.getMemory().setLocation(locationMemory + this.indirectValue1, computer.getMemory().getLocation((int)computer.getRegisters().getRegisterValue(this.numb2)
						+ this.indirectValue2));
			}
		}
		return false;
	}

}
