package hr.fer.zemris.java.hw13.servlets.glasanje;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.Util.FileUtil;

/**
 * This servlet obtains vote for specific band from
 * client and updates survey vote-counter file.
 * It redirects request to another servlet("glasanjeRezultatiServlet").
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet(name = "glasanje-glasajServlet", urlPatterns={"/glasanje-glasaj", "/glasanje-glasaj.jsp"})
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int vote = -1;
		
		try {
			vote = Integer.parseInt(req.getParameter("id"));
			if(vote < 1 || vote > FileUtil.NUM_OF_BANDS){
				throw new IllegalArgumentException();
			}
		} catch(IllegalArgumentException | NullPointerException ex){
			req.setAttribute("error", "Wrong argument provided, or argument was not provided.");
			req.getRequestDispatcher("/WEB-INF/pages/wrongParameters.jsp");
		}
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
	
		Path file = Paths.get(fileName);
					
		if(!Files.exists(file)){
			FileUtil.createVoteResultsFile(file);
		}
		
		FileUtil.updateVoteFile(file, vote);
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}