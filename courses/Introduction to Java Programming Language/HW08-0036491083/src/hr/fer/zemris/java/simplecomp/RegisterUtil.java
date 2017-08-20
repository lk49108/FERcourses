package hr.fer.zemris.java.simplecomp;

/**
 * This class methods are used in working with CPU's instructions,
 * i.e. it is used for working with parameters in instructions which
 * are consisted of registers.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class RegisterUtil {

	/**
	 * This method extracts registers index out of registerDescriptor
	 * parameter and returns it.
	 * @param registerDescriptor  Integer, register descriptor.
	 * @return Register's index.
	 */
	public static int getRegisterIndex(int registerDescriptor) {
		return registerDescriptor & 0b11111111;
	}
		
	/**
	 * This method checks if addressing is indirect.
	 * @param registerDescriptor Integer, register descriptor.
	 * @return True if addressing is indirect, false otherwise.
	 */
	public static boolean isIndirect(int registerDescriptor) {
		return (registerDescriptor >> 24) % 2 == 1;
	}
	
	/**
	 * This method is used for obtaining register offset.
	 * @param registerDescriptor Integer, register descriptor.
	 * @return Register offset.
	 */
	public static int getRegisterOffset(int registerDescriptor) {
		registerDescriptor = (registerDescriptor >> 8) & 0xffff;
		if((registerDescriptor & 0x8000) == 0) return registerDescriptor;
		return registerDescriptor | 0xffff0000;
	}
}
