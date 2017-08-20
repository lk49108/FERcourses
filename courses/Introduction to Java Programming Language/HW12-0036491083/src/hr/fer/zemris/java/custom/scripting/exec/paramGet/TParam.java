package hr.fer.zemris.java.custom.scripting.exec.paramGet;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class, used as part of a strategy described in {@link IParam}.
 * Used for obtaining, deleting and setting "temporary" parameters from
 * {@link RequestContext} instance.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class TParam implements IParam{

	@Override
	public String get(RequestContext rContext, String name) {
		return rContext.getTemporaryParameter(name);
	}

	@Override
	public void del(RequestContext rContext, String name) {
		rContext.removeTemporaryParameter(name);
	}

	@Override
	public void set(RequestContext rContext, String name, String value) {
		rContext.setTemporaryParameter(name, value);
	}

}
