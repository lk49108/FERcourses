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

import hr.fer.zemris.java.hw18.persistent.Provider;

/**
 * Obtains appropriate picture description(picture is determined through 'path' parameter).
 * It then writes this description String to resp.getWriter().write() method.
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet(urlPatterns={"/servlets/pictureDescription"})
public class PictureDescription extends HttpServlet {

	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String desc = Provider.getDescription(req.getParameter("path"));
		
		resp.getWriter().write(desc);
		resp.getWriter().flush();
		
	}
}
