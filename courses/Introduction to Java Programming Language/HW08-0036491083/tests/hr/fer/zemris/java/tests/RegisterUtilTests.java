package hr.fer.zemris.java.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.simplecomp.RegisterUtil;

/**
 * This class holds tests for different versions of register 
 * addressing used in programs.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class RegisterUtilTests {

	@Test
	public void testUnpacking1() {
	int registerDescriptor = 0x1000102;
	assertEquals(2, RegisterUtil.getRegisterIndex(registerDescriptor));
	assertEquals(1, RegisterUtil.getRegisterOffset(registerDescriptor));
	assertEquals(true, RegisterUtil.isIndirect(registerDescriptor));
	}
	
	@Test
	public void testUnpacking2() {
	int registerDescriptor = 0x1FFFF02;
	assertEquals(2, RegisterUtil.getRegisterIndex(registerDescriptor));
	assertEquals(-1, RegisterUtil.getRegisterOffset(registerDescriptor));
	assertEquals(true, RegisterUtil.isIndirect(registerDescriptor));
	}
	
	@Test
	public void testUnpacking3() {
	int registerDescriptor = 0x18000FE;
	assertEquals(254, RegisterUtil.getRegisterIndex(registerDescriptor));
	assertEquals(-32768, RegisterUtil.getRegisterOffset(registerDescriptor));
	assertEquals(true, RegisterUtil.isIndirect(registerDescriptor));
	}
	
	@Test
	public void testUnpacking4() {
	int registerDescriptor = 0x17FFFFF;
	assertEquals(255, RegisterUtil.getRegisterIndex(registerDescriptor));
	assertEquals(32767, RegisterUtil.getRegisterOffset(registerDescriptor));
	assertEquals(true, RegisterUtil.isIndirect(registerDescriptor));
	}
	
	@Test
	public void TestSomeRegAddresings(){
		assertEquals(false, RegisterUtil.isIndirect(0x00000003));
		assertEquals(true, RegisterUtil.isIndirect(0x01000003));
		assertEquals(5, RegisterUtil.getRegisterOffset(0x01000503));
		assertEquals(35, RegisterUtil.getRegisterIndex(0x00000023));
		assertEquals(51, RegisterUtil.getRegisterIndex(0x01000033));
		assertEquals(73, RegisterUtil.getRegisterOffset(0x01004903));
		assertEquals(14, RegisterUtil.getRegisterOffset(0x01000e03));
	}
	
}
