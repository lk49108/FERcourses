package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet of main(home) page of application.
 * It just forwards request to appropriate JSP-file.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
@WebServlet(name="indexServlet", urlPatterns={"/index.html","/index.htm", "/index"})
public class Index extends HttpServlet {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}
	
}
