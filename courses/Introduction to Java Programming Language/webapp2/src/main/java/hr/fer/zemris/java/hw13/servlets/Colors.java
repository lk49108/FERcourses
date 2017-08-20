package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Forwards request to colors.jsp file.
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet(name="colorsServlet", urlPatterns={"/colors","/colors.jsp"})
public class Colors extends HttpServlet {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/colors.jsp").forward(req, resp);
	}
}
