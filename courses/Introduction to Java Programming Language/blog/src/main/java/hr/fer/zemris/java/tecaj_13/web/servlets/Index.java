/**
 * 
 */
package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which is being called as a root for this application. Redirects
 * its request.
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet("/index.jsp")
public class Index extends HttpServlet {

	/**
	 * Serial verison UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(req.getContextPath()+"/servleti/main");
	}
}
