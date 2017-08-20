package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import hr.fer.zemris.java.hw14.dao.DAOPollProvider;
import hr.fer.zemris.java.hw14.model.PollModel;

/**
 * Servlet of main(home) page of application.
 * It just forwards request to appropriate JSP-file
 * and fills attribute list with list of available polls which are 
 * then displayed on this JSP-file.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
@WebServlet(name="indexServlet", urlPatterns={"/index.html"})
public class Index extends HttpServlet {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				
		List<PollModel> polls = DAOPollProvider.getDao().getBasicInputList();
		
		req.setAttribute("pollOptions", polls);
		
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}

}
