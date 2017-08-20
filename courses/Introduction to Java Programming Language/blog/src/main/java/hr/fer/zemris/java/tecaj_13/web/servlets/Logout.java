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
 * Logout servlet, used in logout process.
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet("/servleti/logout")
public class Logout extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().removeAttribute("current.user");
		resp.sendRedirect(req.getContextPath()+"/servleti/main");
	}
}
