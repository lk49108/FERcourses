/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.exec;

import java.text.DecimalFormat;

import hr.fer.zemris.java.custom.scripting.exec.paramGet.IParam;
import hr.fer.zemris.java.custom.scripting.exec.paramGet.PParam;
import hr.fer.zemris.java.custom.scripting.exec.paramGet.Param;
import hr.fer.zemris.java.custom.scripting.exec.paramGet.TParam;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Utility class holding methods for utilizing
 * each function supported by {@link SmartScriptEngine}.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class FunctionsUtil {

	/**
	 * Method which utilizes sinus function.
	 * @param stack Stack from which parameter for function is poped and
	 * then result pushed back onto it.
	 * @param stackKey Key used for obtaining values from stack.
	 */
	public static void sin(ObjectMultistack stack, String stackKey){
		ValueWrapper value = stack.pop(stackKey);
		
		Double result = null;
		
		if(value.getValue() instanceof Integer){
			result = Math.sin((Integer)value.getValue());
		} else {
			result = Math.sin((Double)value.getValue());
		}
				
		value.setValue(result);
		
		stack.push(stackKey, value);
	}
	
	/**
	 * Method which utilizes decimalFormating of number.
	 * @param stack Stack from which parameter for function is poped and
	 * then result pushed back onto it.
	 * @param stackKey Key used for obtaining values from stack.
	 */
	public static void decimalFormat(ObjectMultistack stack, String stackKey){
		ValueWrapper decFormat = stack.pop(stackKey);
		ValueWrapper value = stack.pop(stackKey);
		
		DecimalFormat format = new DecimalFormat((String)decFormat.getValue());
		
		//value.getValue() can not be String in my implementation of ValueWrapper class.
		value.setValue(format.format(value.getValue()));

		stack.push(stackKey, value);
	}

	/**
	 * Method which utilizes duplication of value on top of the stack.
	 * @param stack Stack which topmost value is duplicated.
	 * @param stackKey Key used for obtaining values from stack.
	 */
	public static void dup(ObjectMultistack temporaryStack, String temporarystackkey) {
		ValueWrapper stackValue = temporaryStack.peek(temporarystackkey);
		temporaryStack.push(temporarystackkey, stackValue);
	}

	/**
	 * Method which utilizes swapping of two topmost values on provided stack.
	 * @param stack Stack on which swapping is performed.
	 * @param stackKey Key used for obtaining values from stack.
	 */
	public static void swap(ObjectMultistack temporaryStack, String temporarystackkey) {
		ValueWrapper a = temporaryStack.pop(temporarystackkey);
		ValueWrapper b = temporaryStack.pop(temporarystackkey);
		temporaryStack.push(temporarystackkey, a);
		temporaryStack.push(temporarystackkey, b);
	}

	/**
	 * Method which utilizes action of popping value from top of the stack and then
	 * calling method requestContext.setMimeType(value) on provided requestContext.
	 * @param temporaryStack Stack from which value is pop.
	 * @param stackKey Key used for obtaining values from stack.
	 * @param requestContext {@link RequestContext} instance on which setMymeType(value) method is called.
	 */
	public static void setMimeType(ObjectMultistack temporaryStack, String temporarystackkey, RequestContext requestContext) {
		ValueWrapper mimeTypeString = temporaryStack.pop(temporarystackkey);
		requestContext.setMimeType((String)mimeTypeString.getValue());
	}

	
	public static void paramGet(ObjectMultistack temporaryStack, String temporarystackkey,
			RequestContext requestContext) {
		ValueWrapper defValue = temporaryStack.pop(temporarystackkey);
		ValueWrapper name = temporaryStack.pop(temporarystackkey);
		String value = requestContext.getParameter((String)name.getValue());
		if(value == null){
			temporaryStack.push(temporarystackkey, defValue);
		} else {
			temporaryStack.push(temporarystackkey, new ValueWrapper(value));
		}
	}

	/**
	 * Utilizes action of
	 * obtaining value from requestContext parameters map(which parameters map this 
	 * actually is depends on provided {@link IParam} instance) mapped for
	 * name. It also pushes it onto stack. If there is no such mapping,
	 * it pushes defValue onto stack instead. Conceptually, equals to:
	 * defValue = pop(), name = pop(), value=reqCtx.getSomeKindOfParam(name),
	 * push(value==null ? defValue : value).
	 * @param temporaryStack Stack from which values are pushed and popped.
	 * @param stackKey Key used for obtaining values from stack.
	 * @param requestContext {@link RequestContext} instance.
	 */
	public static void paramGet(ObjectMultistack temporaryStack, String temporarystackkey,
			RequestContext requestContext, IParam param) {
		ValueWrapper defValue = temporaryStack.pop(temporarystackkey);
		ValueWrapper name = temporaryStack.pop(temporarystackkey);
		String value = param.get(requestContext, (String)name.getValue());
		if(value == null){
			temporaryStack.push(temporarystackkey, defValue);
		} else {
			temporaryStack.push(temporarystackkey, new ValueWrapper(value));
		}
	}

	/**
	 * Utilizes action of
	 * setting value to specific parameters map(which parameters map this 
	 * actually is depends on provided {@link IParam} instance).
	 * Conceptually, equals to:
	 * name = pop(), value = pop(), reqCtx.setSomeKindOfParam(name, value).
	 * @param temporaryStack Stack from which values are popped.
	 * @param stackKey Key used for obtaining values from stack.
	 * @param requestContext {@link RequestContext} instance.
	 */
	public static void paramSet(ObjectMultistack temporaryStack, String temporarystackkey,
			RequestContext requestContext, IParam param) {
		ValueWrapper name = temporaryStack.pop(temporarystackkey);
		ValueWrapper value = temporaryStack.pop(temporarystackkey);
		String valueString = changeToStringIfNecessary(value.getValue());
		param.set(requestContext, (String)name.getValue(), (String)valueString);
	}

	/**
	 * Utilizes action of
	 * removing association for name from requestContext specific parameters map
	 * (which map this is depends on provided {@link IParam} instance.
	 * Name is value popped from provided stack.
	 * @param temporaryStack Stack from which values are popped.
	 * @param stackKey Key used for obtaining values from stack.
	 * @param requestContext {@link RequestContext} instance.
	 */
	public static void paramDel(ObjectMultistack temporaryStack, String temporarystackkey,
			RequestContext requestContext, IParam param) {
		ValueWrapper name = temporaryStack.pop(temporarystackkey);
		param.del(requestContext, (String)name.getValue());
	}
	
	/**
	 * Checks if provided value is not a String.
	 * If statement is true, it changes it to be String.
	 * @param value Value which is checked for not being a String. 
	 * @return String representation of provided value.
	 */
	private static String changeToStringIfNecessary(Object value){
		if(value instanceof Integer){
			return Integer.toString((Integer)value);
		} else if(value instanceof Double){
			return Double.toString((Double)value);
		}
		return (String)value;
	}
	
	
}
