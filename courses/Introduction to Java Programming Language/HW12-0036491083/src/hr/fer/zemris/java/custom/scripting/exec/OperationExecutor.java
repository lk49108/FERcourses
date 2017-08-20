/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.exec;

/**
 * This interface is used as a root for construction of 
 * strategy pattern. It holds only one abstract method.
 * @author Leonardo Kokot
 * @version 1.0
 */
public interface OperationExecutor {
	/**
	 * This method is used for executing specific(depends on class on which it is called)
	 * arithmetic or logical operation on two given given parameters. It returns result of operation. 
	 * @param param1 First parameter of arithmetic operation.
	 * @param param2 Second parameter included in arithmetic operation.
	 * @return Result of arithmetic operation.
	 */
	Object operationExecute(Object param1, Object param2);
}
