/**
 * 
 */
package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.forms.LoginForm;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet used for login process.
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet(name="loginServlet", urlPatterns={"/servleti/login"})
public class Login extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doReq(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doReq(req, resp);
	}
	
	/**
	 * Method which does both, doPost and doGet job.
	 * @param req Http request.
	 * @param resp Http response.
	 * @throws ServletException 
	 * @throws IOException
	 */
	private void doReq(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LoginForm form = new LoginForm();
		
		form.fillFromHttpRequest(req);
		
		form.validate();
		
		if(form.hasMistakes()){
			form.setPassword("");
			req.setAttribute("formular", form);
			req.getRequestDispatcher("../WEB-INF/pages/index.jsp").forward(req, resp);
			return;
		}
		
		BlogUser user = form.fillUser();
		
		req.getSession().setAttribute("current.user", user);

		resp.sendRedirect(req.getContextPath()+"/servleti/main");
	}
}
