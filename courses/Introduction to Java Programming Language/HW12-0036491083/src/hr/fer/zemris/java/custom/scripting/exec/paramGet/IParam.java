package hr.fer.zemris.java.custom.scripting.exec.paramGet;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Interface used as a root for strategy which
 * is used for obtaining and deleting appropriate
 * type of parameter in {@link RequestContext} instance.
 * @author Leonardo Kokot
 * @version 1.0
 */
public interface IParam {

	/**
	 * Method used for obtaining appropriate value
	 * associated with provided key 
	 * in also provided {@link RequestContext}.
	 * @param rContext Provided {@link RequestContext} instance.
	 * @param name Provided key.
	 * @return String associated with provided key.
	 */
	 String get(RequestContext rContext, String name);
	
	/**
	 * Method used for deleting appropriate value
	 * associated with provided key 
	 * in also provided {@link RequestContext}.
	 * @param rContext Provided {@link RequestContext} instance.
	 * @param name Provided key.
	 */
	 void del(RequestContext rContext, String name);

	/**
	 * Method used for setting provided value,
	 * with a key which is provided as name parameter,  
	 * in also provided {@link RequestContext}.
	 * @param rContext Provided {@link RequestContext} instance.
	 * @param name Provided key.
	 * @param value Value which to be set.
	 */
	void set(RequestContext rContext, String name, String value);
	
}
