/**
 * 
 */
package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Calculates time which passed from application start up
 * and forwards request to appInfo.jsp file with appropriate attribute
 * set to String representation of time this application is running.
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet(name = "appInfoServlet", urlPatterns = {"/appinfo.jsp", "/appinfo"})
public class AppInfo extends HttpServlet {

	/**
	 * How many milliseconds are there in one second. 
	 */
	private static final long SECOND = 1000;
	
	/**
	 * How many milliseconds are there in one minute. 
	 */
	private static final long MINUTE = SECOND * 60;

	/**
	 * How many milliseconds are there in one hour. 
	 */
	private static final long HOUR = MINUTE * 60;

	/**
	 * How many milliseconds are there in one day. 
	 */
	private static final long DAY = HOUR * 24;

	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		long currentTime = System.currentTimeMillis();

		long startUpTime = (long)req.getServletContext().getAttribute("startTime");
		
		String time = calculateTime(currentTime, startUpTime);
		
		req.setAttribute("time", time);
		
		req.getRequestDispatcher("/WEB-INF/pages/appInfo.jsp").forward(req, resp);
	}

	/**
	 * Calculates time this application is running and returns appropriate String representation 
	 * of time this application is running.
	 * @param currentTime Current time in milliseconds.
	 * @param startUpTime StartUp time in milliseconds.
	 * @return String representation of time this application is running.
	 */
	private String calculateTime(long currentTime, long startUpTime) {
		long diff = currentTime - startUpTime;
		String time = "";
		
			long value = diff/(DAY);
			time += value + " days ";
			diff -= value * (DAY);

			value = diff/HOUR;
			time += value + " hours ";
			diff -= value * (HOUR);
			
			value = diff/MINUTE;
			time += value + " minutes ";
			diff -= value * (MINUTE);
		
			value = diff/SECOND;
			time += value + " seconds and ";
			diff -= value * (SECOND);
		
		if(diff > 0){
			time += diff + " milliseconds";
		}
		
		return time;
	}

}