package hr.fer.zemris.java.hw13.servlets.glasanje;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.Util.FileUtil;

/**
 * Loads bands which are part of survey and
 * forwards request to appropriate JSP-file with appropriate attribute
 * set to such value that JSP-file can find out band names from them. 
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet(name = "glasanjeServlet", urlPatterns = {"/glasanje", "/glasanje.jsp"})
public class GlasanjeServlet extends HttpServlet{

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		
		List<String> rows = FileUtil.readFileRows(Paths.get(fileName));
		
		List<String> bands = new ArrayList<>();
		
		for(String row : rows){
			String[] partsOfRow = row.split("\t");
			
			bands.add(partsOfRow[1].trim());
		}
		
		req.setAttribute("bands", bands);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanje/glasanjeIndex.jsp").forward(req, resp);
	}
	
}
