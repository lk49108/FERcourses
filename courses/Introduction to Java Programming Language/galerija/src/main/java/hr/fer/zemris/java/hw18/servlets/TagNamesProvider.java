/**
 * 
 */
package hr.fer.zemris.java.hw18.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw18.persistent.Provider;

/**
 * Returns paths of the pictures which contains provided tag. Used as part of
 * AJAX response on .jsp file.
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet(urlPatterns={"/servlets/tagProvider"})
public class TagNamesProvider extends HttpServlet {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!Provider.isInitialized()){
			
			Provider.initialize(req.getServletContext().getRealPath(
					"/WEB-INF/opisnik.txt"
					));
		}
		
		String tag = req.getParameter("tag");

		List<String> tagOwners = Provider.getTagOwners(tag);
		
		String write = "";
		
		boolean first = true;
		for(String tagOwner : tagOwners){
			if(first){
				first = false;
				write = write + tagOwner;
			} else {
				write = write + "#" + tagOwner;
			}
		}

		
		resp.getWriter().write(write);
		resp.getWriter().flush();
	}
}
