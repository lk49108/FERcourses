/**
 * 
 */
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
 * Calculates values for sin(x) and cos(x) functions.
 * Range of parameter x for which trigonometric functions
 * are calculated is obtained through parameters a and b.
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet(name="trigonometricServlet", urlPatterns={"/trigonometric","/trigonometric.jsp"})
public class TrigonometricServlet extends HttpServlet {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int a = 0;
		try{
			a = Integer.parseInt(req.getParameter("a"));
		} catch(NumberFormatException unprocessed){}
		
		int b = 360;
		try{
			b = Integer.parseInt(req.getParameter("b"));
		} catch(NumberFormatException unprocessed){}
		
		if(a > b){
			//swap(a, b);
			int c = a;
			a = b;
			b = c;
		} else if(b > a + 720){
			b = a + 720;
		}
		
		List<Integer> angles = new ArrayList<>();
		List<Double> sin = new ArrayList<>();
		List<Double> cos = new ArrayList<>();
		for(int i  = a;  i <= b; i++){
			angles.add(i);
			sin.add(Math.sin(Math.toRadians(i)));
			cos.add(Math.cos(Math.toRadians(i)));
		}
		
		req.setAttribute("angles", angles);
		req.setAttribute("sin", sin);
		req.setAttribute("cos", cos);
		req.setAttribute("numOfElems", b - a + 1);
		
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}

	
}
