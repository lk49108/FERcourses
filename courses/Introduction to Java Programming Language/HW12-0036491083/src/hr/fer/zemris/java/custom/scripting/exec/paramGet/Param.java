package hr.fer.zemris.java.custom.scripting.exec.paramGet;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class, used as part of a strategy described in {@link IParam}.
 * Used for obtaining "ordinary" parameters from
 * {@link RequestContext} instance. Methods 
 * {@link #set(RequestContext, String, String)} and 
 * {@link #del(RequestContext, String)} in this implementation does nothing.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class Param implements IParam {

	@Override
	public String get(RequestContext rContext, String key) {
		return rContext.getParameter(key);
	}

	@Override
	public void del(RequestContext rContext, String key) {
	}

	@Override
	public void set(RequestContext rContext, String name, String value) {
	}

}
