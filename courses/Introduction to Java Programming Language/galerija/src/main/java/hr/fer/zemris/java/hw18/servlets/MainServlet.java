/**
 * 
 */
package hr.fer.zemris.java.hw18.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is applications main servlet. It just makes sure that when user
 * enters web-application root URL it is redirected to main and only jsp page of this web application.
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet(urlPatterns={"/index.html", "/index.htm", "/"})
public class MainServlet extends HttpServlet {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/page.jsp").forward(req, resp);
	}
}
