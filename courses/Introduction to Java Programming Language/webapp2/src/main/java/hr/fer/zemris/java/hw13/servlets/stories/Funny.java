/**
 * 
 */
package hr.fer.zemris.java.hw13.servlets.stories;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Chooses randomly selected color and forwards request to appropriate
 * JSP-file with attribute "color" set to color defining value. 
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet(name="storiesFunnyServlet", urlPatterns={"/stories/funny.jsp", "/stories/funny"})
public class Funny extends HttpServlet {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Random rand = new Random();
		int red = rand.nextInt(256);
		int green = rand.nextInt(256);
		int blue = rand.nextInt(256);
		
		String hex = String.format("%02X%02x%02x", red & 0xff, blue & 0xff, green & 0xff);
		
		req.setAttribute("color", hex);
		
		req.getRequestDispatcher("/WEB-INF/pages/stories/funny.jsp").forward(req, resp);
	}
	
}
