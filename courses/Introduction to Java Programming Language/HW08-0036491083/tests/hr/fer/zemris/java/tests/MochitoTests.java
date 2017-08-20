/**
 * 
 */
package hr.fer.zemris.java.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrCall;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrLoad;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrMove;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrPop;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrPush;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrRet;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

/**
 * This class holds tests for CPU's instructions 
 * load, move, push, pop, call and ret.
 * It also holds one test for checking 
 * how register addresing works.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MochitoTests {
	
	@Mock
	private Memory memory;
	
	@Mock
	private Registers registers;
	
	@Mock
	private Computer computer;
	
	@Test
	public void TestLoadConstructor(){
		List<InstructionArgument> arguments = (List<InstructionArgument>)mock(List.class);
		InstructionArgument argument1 = (InstructionArgument)mock(InstructionArgument.class);
		InstructionArgument argument2 = (InstructionArgument)mock(InstructionArgument.class);
		when(arguments.size()).thenReturn(2);
		when(argument1.isRegister()).thenReturn(true);
		when(argument2.isNumber()).thenReturn(true);
		when(arguments.get(0)).thenReturn(argument1);
		when(arguments.get(1)).thenReturn(argument2);
		int value = 0x00000003;
		int address = 10;
		when(argument2.getValue()).thenReturn(address);
		when(argument1.getValue()).thenReturn(value);
		arguments.add(argument1);
		arguments.add(argument2);
		InstrLoad load = new InstrLoad(arguments);
		verify(argument1, times(1)).isRegister();
		verify(arguments, times(1)).size();
		verify(argument2, times(1)).isNumber();
		verify(arguments, times(3)).get(0);
		verify(arguments, times(2)).get(1);
		verify(argument1, times(2)).getValue();
		verify(argument2, times(1)).getValue();
	}
	
	@Test
	public void TestLoadExecute(){
		InstrLoad load = (InstrLoad)mock(InstrLoad.class);
		Memory memory = (Memory)mock(Memory.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getMemory()).thenReturn(memory);
		String string ="Hej";
		int index = 2;
		when(memory.getLocation(0)).thenReturn(string);
		when(load.execute(computer)).thenCallRealMethod();
		load.execute(computer);
		verify(computer, times(1)).getRegisters();
		verify(computer, times(1)).getMemory();
		verify(memory, times(1)).getLocation(0);
		verify(load, times(1)).execute(computer);
		assertEquals("Hej", memory.getLocation(0));
	}
	
	@Test
	public void TestMoveConstructor(){
		when(computer.getRegisters()).thenReturn(registers);
		when(registers.getRegisterValue(3)).thenReturn(7);
		List<InstructionArgument> arguments = (List<InstructionArgument>)mock(List.class);
		InstructionArgument argument1 = (InstructionArgument)mock(InstructionArgument.class);
		InstructionArgument argument2 = (InstructionArgument)mock(InstructionArgument.class);
		when(arguments.size()).thenReturn(2);
		when(argument1.isRegister()).thenReturn(true);
		when(argument2.isString()).thenReturn(false);
		when(arguments.get(0)).thenReturn(argument1);
		when(arguments.get(1)).thenReturn(argument2);
		arguments.add(argument1);
		arguments.add(argument2);
		int value = 0x00000005;
		int value2 = 0x00000003;
		when(argument1.getValue()).thenReturn(value);
		when(argument2.isRegister()).thenReturn(true);
		when(argument2.getValue()).thenReturn(value2);
		InstrMove move = new InstrMove(arguments);
		verify(argument1, times(1)).isRegister();
		verify(arguments, times(1)).size();
		verify(argument2, times(1)).isString();
		verify(arguments, times(2)).get(0);
		verify(arguments, times(2)).get(1);
		move.execute(computer);
		assertEquals(7, computer.getRegisters().getRegisterValue(3));
	}
	
	@Test
	public void TestMoveExecute(){
		InstrMove move = (InstrMove)mock(InstrMove.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getMemory()).thenReturn(memory);
		int number = 3;
		int index = 2;
		when(memory.getLocation(0)).thenReturn(number);
		when(move.execute(computer)).thenCallRealMethod();
		move.execute(computer);
		verify(computer, times(2)).getRegisters();
		verify(computer, times(0)).getMemory();
		verify(move, times(1)).execute(computer);
		assertEquals(3, memory.getLocation(0));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void TestMoveConstructorWithException(){
		List<InstructionArgument> arguments = (List<InstructionArgument>)mock(List.class);
		InstructionArgument argument1 = (InstructionArgument)mock(InstructionArgument.class);
		InstructionArgument argument2 = (InstructionArgument)mock(InstructionArgument.class);
		when(arguments.size()).thenReturn(2);
		when(argument1.isRegister()).thenReturn(false);
		when(argument2.isString()).thenReturn(false);
		when(arguments.get(0)).thenReturn(argument1);
		when(arguments.get(1)).thenReturn(argument2);
		arguments.add(argument1);
		arguments.add(argument2);
		InstrMove move = new InstrMove(arguments);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void TestPushConstructorWithWrongNumberOfArguments(){
		List<InstructionArgument> arguments = (List<InstructionArgument>)mock(List.class);
		InstructionArgument argument1 = (InstructionArgument)mock(InstructionArgument.class);
		InstructionArgument argument2 = (InstructionArgument)mock(InstructionArgument.class);
		when(arguments.size()).thenReturn(2);
		arguments.add(argument1);
		arguments.add(argument2);
		InstrPush load = new InstrPush(arguments);
	}
	
	@Test
	public void TestPushConstructor(){
		List<InstructionArgument> arguments = (List<InstructionArgument>)mock(List.class);
		InstructionArgument argument = (InstructionArgument)mock(InstructionArgument.class);
		when(arguments.size()).thenReturn(1);
		int value = 0x00000003;
		when(argument.isRegister()).thenReturn(true);
		when(argument.getValue()).thenReturn(value);
		when(arguments.get(0)).thenReturn(argument);
		arguments.add(argument);
		InstrPush push = new InstrPush(arguments);
		assertEquals(3, RegisterUtil.getRegisterIndex((int)argument.getValue()));
		verify(argument, times(1)).isRegister();
		verify(arguments, times(1)).size();
		verify(arguments, times(3)).get(0);
		verify(argument, times(3)).getValue();
	}
	
	@Test
	public void TestPushExecute(){
		InstrPush push = (InstrPush)mock(InstrPush.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getMemory()).thenReturn(memory);
		when(registers.getRegisterValue(15)).thenReturn(10);
		when(registers.getRegisterValue(0)).thenReturn(10);
		when(push.execute(computer)).thenCallRealMethod();
		push.execute(computer);
		verify(computer, times(3)).getRegisters();
		verify(computer, times(1)).getMemory();
		verify(memory, times(1)).setLocation(10, 10);
		verify(registers, times(1)).getRegisterValue(0);
		verify(push, times(1)).execute(computer);
	}
	
	@Test
	public void TestPopConstruction(){
		List<InstructionArgument> arguments = (List<InstructionArgument>)mock(List.class);
		InstructionArgument argument = (InstructionArgument)mock(InstructionArgument.class);
		when(arguments.size()).thenReturn(1);
		when(arguments.get(0)).thenReturn(argument);
		when(argument.isRegister()).thenReturn(true);
		int value = 0x00000003;
		when(argument.getValue()).thenReturn(value);
		arguments.add(argument);
		InstrPop pop = new InstrPop(arguments);
		verify(argument, times(1)).isRegister();
		verify(arguments, times(1)).size();
		verify(arguments, times(3)).get(0);
		verify(argument, times(2)).getValue();
	}
	
	@Test
	public void TestPopExecute(){
		InstrPop pop = (InstrPop)mock(InstrPop.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getMemory()).thenReturn(memory);
		when(registers.getRegisterValue(15)).thenReturn(10);
		when(registers.getRegisterValue(0)).thenReturn(10);
		when(pop.execute(computer)).thenCallRealMethod();
		pop.execute(computer);
		verify(computer, times(3)).getRegisters();
		verify(computer, times(1)).getMemory();
		verify(memory, times(1)).getLocation(11);
		verify(registers, times(1)).getRegisterValue(15);
		verify(registers, times(1)).setRegisterValue(15, 11);
		verify(registers, times(1)).setRegisterValue(0, null);
		verify(pop, times(1)).execute(computer);
	}
	
	@Test
	public void TestCallConstructionAndExecute(){
		List<InstructionArgument> arguments = (List<InstructionArgument>)mock(List.class);
		InstructionArgument argument = (InstructionArgument)mock(InstructionArgument.class);
		when(arguments.size()).thenReturn(1);
		when(arguments.get(0)).thenReturn(argument);
		when(argument.isNumber()).thenReturn(true);
		when(argument.getValue()).thenReturn(73);
		arguments.add(argument);
		InstrCall call = new InstrCall(arguments);
		verify(argument, times(1)).isNumber();
		verify(arguments, times(1)).size();
		verify(arguments, times(2)).get(0);
		verify(argument, times(1)).getValue();
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getMemory()).thenReturn(memory);
		when(registers.getRegisterValue(15)).thenReturn(10);
		when(registers.getProgramCounter()).thenReturn(20);
		call.execute(computer);
		assertEquals(20, registers.getProgramCounter());
		assertEquals(null, memory.getLocation(20));
		assertEquals(10, registers.getRegisterValue(15));
		verify(computer, times(4)).getRegisters();
		verify(computer, times(1)).getMemory();
		verify(memory, times(1)).getLocation(20);
		verify(registers, times(2)).getRegisterValue(15);
		verify(registers, times(2)).getProgramCounter();
	}
	
	@Test
	public void TestRetConstruction(){
		List<InstructionArgument> arguments = (List<InstructionArgument>)mock(List.class);
		when(arguments.isEmpty()).thenReturn(true);
		InstrRet ret = new InstrRet(arguments);
		verify(arguments, times(1)).isEmpty();
	}
	
	@Test
	public void TestRetExecute(){
		InstrRet ret = (InstrRet)mock(InstrRet.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getMemory()).thenReturn(memory);
		when(registers.getRegisterValue(15)).thenReturn(7);
		when(memory.getLocation(anyInt())).thenReturn(7);
		when(ret.execute(computer)).thenCallRealMethod();
		ret.execute(computer);
		verify(computer, times(3)).getRegisters();
		verify(computer, times(1)).getMemory();
		verify(memory, times(1)).getLocation(8);
		verify(registers, times(1)).getRegisterValue(15);
		verify(registers, times(1)).setRegisterValue(15, 8);
		verify(ret, times(1)).execute(computer);
		assertEquals(0, registers.getProgramCounter());
	}
	
}
