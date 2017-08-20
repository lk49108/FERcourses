/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.exec;

/**
 * This class implements {@link OperationExecutor} interface.
 * It defines operationExecute to behave as arithmetic operation 
 * of summing.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class OperationAdd implements OperationExecutor{

	@Override
	public Object operationExecute(Object param1, Object param2) {
		if(param1 instanceof Double && param2 instanceof Double){
			return (Double)param1 + (Double)param2;
		}
		if(param2 instanceof Double){
			return (Double)param2 + (Integer)param1;
		}
		if(param1 instanceof Double){
			return (Integer)param2 + (Double)param1;
		}
		return (Integer)param1 + (Integer)param2;
	}

}
