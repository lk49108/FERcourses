/**
 * 
 */
package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Application main servlet.
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet(name="MainServlet", urlPatterns={"/servleti/main"})
public class Main extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws javax.servlet.ServletException ,java.io.IOException {
		List<BlogUser> users = DAOProvider.getDAO().getBlogUsers();
		
		req.setAttribute("users", users);
		
		req.getRequestDispatcher("../WEB-INF/pages/index.jsp").forward(req, resp);
	};
	
	
	
}
