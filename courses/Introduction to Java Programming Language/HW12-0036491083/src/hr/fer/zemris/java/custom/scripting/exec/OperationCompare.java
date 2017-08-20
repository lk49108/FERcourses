package hr.fer.zemris.java.custom.scripting.exec;

/**
 * This class implements {@link OperationExecutor} interface.
 * It defines operationExecute to behave as logical operation
 * of comparing.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class OperationCompare implements OperationExecutor{

	@Override
	public Object operationExecute(Object param1, Object param2) {
		if(param1 instanceof Double && param2 instanceof Double){
			return ((Double)((Double)param1 - (Double)param2)).intValue();
		}
		if(param2 instanceof Double){
			return ((Double)((Integer)param1 -(Double)param2)).intValue();
		}
		if(param1 instanceof Double){
			return ((Double)((Double)param1 -(Integer)param2)).intValue();
		}
		return (Integer)param1 - (Integer)param2;
	}

}
