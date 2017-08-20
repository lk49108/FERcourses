package hr.fer.zemris.java.hw14.servlets.glasanje;

import java.io.IOException;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOPollOptionProvider;
import hr.fer.zemris.java.hw14.dao.DAOPollProvider;
import hr.fer.zemris.java.hw14.model.PollOptionModel;

/**
 * This servlet obtains available pollOptions for
 * selected poll(defined by provided parameter pollID).
 * It arranges poll options into list which is set as
 * attribute to request and then request is forwarded to appropriate .jsp file.
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet(name = "glasanjeServlet", urlPatterns = {"/glasanje"})
public class GlasanjeServlet extends HttpServlet{

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				
		try {
			final long pollID = Long.parseLong(req.getParameter("pollID"));
			
			List<PollOptionModel> options = DAOPollOptionProvider.getDao().getBasicInputList();
			
			List<PollOptionModel> optionsWithRightID = options.stream()
					.filter(t -> t.getPollID() == pollID)
					.collect(Collectors.toList());
			
			if(optionsWithRightID.isEmpty()){
				throw new IllegalArgumentException();
			}
			
			req.setAttribute("descriptor", DAOPollProvider.getDao().getInput(pollID));
			req.setAttribute("pollOptions", optionsWithRightID);
			
			req.getRequestDispatcher("/WEB-INF/pages/glasanje/glasanjeIndex.jsp").forward(req, resp);
		} catch (IllegalArgumentException | NullPointerException ex){
			req.setAttribute("error", "Wrong pollID, or there is no provided pollID.");
			req.getRequestDispatcher("/WEB-INF/pages/wrongParameters.jsp").forward(req, resp);
		}
		
	}
	
}
