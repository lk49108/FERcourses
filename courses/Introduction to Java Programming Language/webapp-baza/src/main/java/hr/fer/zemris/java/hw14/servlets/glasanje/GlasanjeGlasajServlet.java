package hr.fer.zemris.java.hw14.servlets.glasanje;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOPollOptionProvider;

/**
 * This servlet increments votesCounter for specific poll option and then redirects request
 * to {@link GlasanjeRezultatiServlet} servlet.
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet(name = "glasanje-glasajServlet", urlPatterns={"/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long IDvote = -1;
		
		try {
			
			IDvote = Long.parseLong(req.getParameter("id"));
			if(IDvote < 1){
				throw new IllegalArgumentException();
			}
			
			DAOPollOptionProvider.getDao().incrementElement(IDvote, IncrementElems.voteCounter);
			
			String pollID = req.getParameter("pollID");
			
			resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati?pollID="+pollID);

		} catch(IllegalArgumentException | NullPointerException ex){
			req.setAttribute("error", "Wrong argument provided, or argument was not provided.");
			req.getRequestDispatcher("/WEB-INF/pages/wrongParameters.jsp");
		}
		
	}
}