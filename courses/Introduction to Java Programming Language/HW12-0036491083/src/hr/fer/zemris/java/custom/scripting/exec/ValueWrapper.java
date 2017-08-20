package hr.fer.zemris.java.custom.scripting.exec;

import javax.management.RuntimeErrorException;

/**
 * This class is used as a value wrapper for 
 * actual value stored in it. This value stored can be of any type
 * that allows, implemented arithmetical and logical operations,
 * to be executed on them..
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ValueWrapper {

	/**
	 * This instance variable is a read-write property of 
	 * type Object. It is used for storing value which can be
	 * of type Double, Integer, String and null.
	 */
	private Object value;
	
	/**
	 * This constructor creates
	 * instance of this class and it 
	 * also initializes value instance variable to 
	 * specific type and value.
	 * @param value Object type parameter which is stored in instance of this class.
	 * @throws RuntimeErrorException if wrong input was provided.
	 */
	public ValueWrapper(Object value) {
		try{
			value = prepareForExecution(value, false);
		} catch (RuntimeErrorException ex){
			throw ex;
		}
		this.value = value;
	}
	
	/**
	 * This is a getter for value instance variable.
	 * @return the value instance variable.
	 */
	public Object getValue() {
		return value;
	}


	/**
	 * This is a setter for value instance variable.
	 * @param value Value to which instance variable value is being set to.
	 */
	public void setValue(Object value) {
		try{
			value = prepareForExecution(value, false);
		} catch (RuntimeErrorException ex){
			throw ex;
		}
		this.value = value;
	}

	/**
	 * This method is used for execution of arithmetic operation of summing
	 * on parameter stored in instance of this class and value provided as parameter.
	 * @param incValue Object type parameter which is used as a second operand in this
	 * arithmetic operation.
	 * @throws RuntimeErrorException if provided parameter is not legal.
	 */
	public void increment(Object incValue){
		try{
			incValue = prepareForExecution(incValue, true);
			this.value = prepareForExecution(value, true);
		} catch (RuntimeErrorException ex){
			throw ex;
		}
		this.value = new OperationAdd().operationExecute(this.value, incValue);
	}
	
	/**
	 * This method is used for execution of arithmetic operation of subtracting
	 * on parameter stored in instance of this class and value provided as parameter.
	 * @param decValue Object type parameter which is used as a second operand in this
	 * arithmetic operation.
	 * @throws RuntimeErrorException if provided parameter is not legal.
	 */
	public void decrement(Object decValue){
		try{
			decValue = prepareForExecution(decValue, false);
			this.value = prepareForExecution(value, true);
		} catch (RuntimeErrorException ex){
			throw ex;
		}
		this.value = new OperationSubtract().operationExecute(this.value, decValue);	
	}
	
	/**
	 * This method is used for execution of arithmetic operation of multiplication
	 * on parameter stored in instance of this class and value provided as parameter.
	 * @param mulValue Object type parameter which is used as a second operand in this
	 * arithmetic operation.
	 * @throws RuntimeErrorException if provided parameter is not legal.
	 */
	public void multiply(Object mulValue){
		try{
			mulValue = prepareForExecution(mulValue, false);
			this.value = prepareForExecution(value, true);
		} catch (RuntimeErrorException ex){
			throw ex;
		}
		this.value = new OperationMultiply().operationExecute(this.value, mulValue);
	}
	
	/**
	 * This method is used for execution of arithmetic operation of division
	 * on parameter stored in instance of this class and value provided as parameter.
	 * Division with zero is not allowed. 
	 * @param divValue Object type parameter which is used as a second operand in this
	 * arithmetic operation.
	 * @throws RuntimeErrorException if provided parameter is not legal.
	 * @throws IllegalArgumentException if provided parameter equals one.
	 */
	public void divide(Object divValue){
		try{
			divValue = prepareForExecution(divValue, false);
			this.value = prepareForExecution(value, true);
		} catch (RuntimeErrorException ex){
			throw ex;
		}
		try{
			if((Integer)divValue == 0){
				throw new IllegalArgumentException("Dividing by zero is not legal.");
			}
		} catch (ClassCastException ex){
			if((Double)divValue == 0.0){
				throw new IllegalArgumentException("Dividing by zero is not legal.");
			}
		}
		this.value = new OperationDivide().operationExecute(this.value, divValue);
	}
	
	/**
	 * This method is used for comparison of
	 * parameter stored in instance of this class and value provided as parameter.
	 * @param withValue Object type parameter which is used as a second operand in this
	 * arithmetic operation.
	 * @return value > 0 if this value is greater than withValue parameter.
	 * Value < 0 is being returned if this value is less than withValue parameter.
	 * 0 is returned if values are equal. 
	 * @throws RuntimeErrorException if provided parameter is not legal.
	 */
	public int numCompare(Object withValue){
		try{
			withValue = prepareForExecution(withValue, true);
			this.value = prepareForExecution(value, true);
		} catch (RuntimeErrorException ex){
			throw ex;
		}
		return (Integer)(new OperationCompare().operationExecute(this.value, withValue));
	}
	
	/**
	 * This method is used as a helper method for preparing parameters
	 * of public methods and constructors in this class for further usage
	 * in this class.
	 * @param valueProvided Object type parameter which is to be
	 * prepared for further usage in this class.
	 * @return Object type value which corresponds to given parameter(but it can be of different
	 * type).
	 * @throws RuntimeErrorException if given parameter is no legal.
	 */
	private Object prepareForExecution(Object valueProvided, boolean parseString){
		if(!( valueProvided instanceof Integer || valueProvided instanceof Double 
				|| valueProvided instanceof String || valueProvided == null)){
			throw new RuntimeErrorException(new Error("Value provided "
					+ "must be of type of String, Integer, Double or null, "
					+ "provided parameter was of type" + valueProvided.getClass().getName()));
		}
		if(valueProvided == null){
			return 0;
		}
		if(valueProvided instanceof String && parseString){
			try{
				valueProvided = parseString(((String) valueProvided).trim());
			} catch (RuntimeErrorException ex){
				throw ex;
			}
		}
		return valueProvided;
	}
	
	/**
	 * This method is used for parsing given String into appropriate 
	 * type(Integer or Double).
	 * @param string String which is to be parsed.
	 * @return Object type value which represents parsed parameter.
	 * @throws RuntimeErrorException if given String is not parsable to
	 * any of previously mentioned type.
	 */
	private Object parseString(String string){
		try{
			return Integer.parseInt(string);
		} catch (NumberFormatException ex){
			try{
				return Double.parseDouble(string);
			} catch (NumberFormatException ex2){
				throw new RuntimeErrorException(new Error("String provided can not be"
						+ "parsed to either Integer or Double object."));
			}
		}
	}
	
}
