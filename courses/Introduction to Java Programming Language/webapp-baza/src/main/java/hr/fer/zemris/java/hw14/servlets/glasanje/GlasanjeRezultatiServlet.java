package hr.fer.zemris.java.hw14.servlets.glasanje;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOPollOptionProvider;
import hr.fer.zemris.java.hw14.model.PollOptionModel;

/**
 * This servlet obtains voting results from poll defined with provided "pollID" parameter.
 * It arranges results in list (decreasing order) and attaches this list to appropriate
 * request attribute. At the end, request is forwarded to .jsp file which then renders voting results to user.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
@WebServlet(name = "glasanjeRezultatiServlet", urlPatterns = {"/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet{

	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			long pollID = Long.parseLong(req.getParameter("pollID"));
		
			List<PollOptionModel> options = DAOPollOptionProvider.getDao().getBasicInputList();
			List<PollOptionModel> optionsWithRightID = options.stream()
					.filter(t -> t.getPollID() == pollID)
					.collect(Collectors.toList());
			
			if(optionsWithRightID.isEmpty()){
				throw new IllegalArgumentException();
			}
			
			Collections.sort(optionsWithRightID);
			req.setAttribute("resultList", optionsWithRightID);
			
			req.getRequestDispatcher("/WEB-INF/pages/glasanje/glasanjeRez.jsp").forward(req, resp);
		} catch(IllegalArgumentException | NullPointerException ex){
			req.setAttribute("error", "Wrong argument provided, or argument was not provided.");
			req.getRequestDispatcher("/WEB-INF/pages/wrongParameters.jsp").forward(req, resp);;
		}
	}
	
	
}
