/**
 * 
 */
package hr.fer.zemris.java.hw13.listeners.applisteners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * {@link ServletContextListener} which, when application is started stores current time
 * in milliseconds to appropriate servlet context attribute. This information is used for rendering
 * information about how long application runs when client opens /appinfo.jsp or /appinfo URLPattern.
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebListener
public class AppTimeRunningListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("startTime", System.currentTimeMillis());
	}

}
