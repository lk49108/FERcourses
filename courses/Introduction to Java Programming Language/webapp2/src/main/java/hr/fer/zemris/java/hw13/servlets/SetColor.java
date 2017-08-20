package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sets appropriate color to user's session.
 * Forwards request to homepage of this application.
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet(name="setColorServlet", urlPatterns={"/setColor.jsp","/setColor"})
public class SetColor extends HttpServlet{

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This list holds all allowed colors in map. Before color of
	 * application is changed it is checked if this color is contained in
	 * this list. This is made in that way to prevent user manually write
	 * color he wants to URL path. Available colors for user are listed in web-application section
	 * named "Background color chooser".
	 */
	private final List<String> allowedColors = new ArrayList<>(); 
	
	{
		allowedColors.add("white");
		allowedColors.add("red");
		allowedColors.add("green");
		allowedColors.add("cyan");
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(allowedColors.contains(req.getParameter("color").toLowerCase())){
			req.getSession().setAttribute("pickedBgCol", req.getParameter("color"));
		}
		req.getRequestDispatcher("/WEB-INF/pages/colors.jsp").forward(req, resp);
	}
}
