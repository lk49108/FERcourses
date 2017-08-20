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

import hr.fer.zemris.java.tecaj_13.forms.RegisterForm;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet used in register process.
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet("/servleti/register")
public class Register extends HttpServlet {

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
	
	private void doReq(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RegisterForm form = new RegisterForm();
		
		form.fillFromHttpRequest(req);
		
		form.validate();
		
		if(form.hasMistakes()){
			form.setPassword("");
			req.setAttribute("formular", form);
			req.getRequestDispatcher("../WEB-INF/pages/registerForm.jsp").forward(req, resp);
			return;
		}
				
		BlogUser blogUser = form.makeUser();
		
		req.getSession().setAttribute("current.user", blogUser);
		
		resp.sendRedirect(req.getContextPath()+"/servleti/author/"+blogUser.getNick());
	}
}
