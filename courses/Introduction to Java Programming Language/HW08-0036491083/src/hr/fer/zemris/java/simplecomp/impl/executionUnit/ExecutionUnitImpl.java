package hr.fer.zemris.java.simplecomp.impl.executionUnit;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.Instruction;


/**
 * This class implements {@link ExecutionUnit} interface.
 * It is used for processing programs. It
 * increments progCounter etc.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class ExecutionUnitImpl implements ExecutionUnit {
	
	@Override
	public boolean go(Computer computer) {
		Instruction instruction;
		try {
			int progCounter;
			while(true){
				progCounter = computer.getRegisters().getProgramCounter();
				instruction = (Instruction)computer.getMemory().getLocation(progCounter);
				computer.getRegisters().setProgramCounter(++progCounter);
				if(instruction.execute(computer)){
					break;
				}
			}
			return true;
		} catch (Exception ex){
			return false;
		}
	}

}
