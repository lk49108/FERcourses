package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This interface is used as a root for
 * all other worker classes, i.e. threads with special purpose.
 * @author Leonardo Kokot
 * @version 1.0
 */
public interface IWebWorker {

	/**
	 * @param RequestContext instance.
	 */
	public void processRequest(RequestContext context);
	
}
