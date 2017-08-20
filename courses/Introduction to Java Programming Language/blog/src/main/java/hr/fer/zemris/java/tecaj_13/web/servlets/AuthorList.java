/**
 * 
 */
package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.forms.BlogEntryForm;
import hr.fer.zemris.java.tecaj_13.forms.CommentForm;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet which is called when user wants to see some other(or himself) user entries,
 * comments, want to post new comment or create new entry, etc.
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet("/servleti/author/*")
public class AuthorList extends HttpServlet{

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = req.getPathInfo().substring(1);

		if(!nick.contains("/")){
			
			List<BlogEntry> blogEntries = DAOProvider.getDAO().getUserBlogEntries(nick);

			req.setAttribute("blogEntries", blogEntries);
			
			BlogUser author = DAOProvider.getDAO().getUserViaNick(nick);

			req.setAttribute("author", author);
			
			req.getRequestDispatcher("../../WEB-INF/pages/authorList.jsp").forward(req, resp);
			
			return;
		}
		

		//Check for comment creation
		if(!(nick.split("/")[1].equals("new") || nick.split("/")[1].equals("edit"))){
			prepareEntry(req, resp);
	        CommentForm form = new CommentForm();
	        req.setAttribute("form", form);
	        req.getRequestDispatcher("../../../WEB-INF/pages/showEntry.jsp").forward(req, resp);
			return;
		}
		
		
		
		String type = nick.split("/")[1];
		nick = nick.split("/")[0];
		
		if(req.getSession().getAttribute("current.user") == null || !nick.equals(((BlogUser)req.getSession().getAttribute("current.user")).getNick())){
			req.setAttribute("error", "You are not allowed to do this operation.");
	        req.getRequestDispatcher("../../../WEB-INF/pages/error.jsp").forward(req, resp);
	        return;
		}
		
		BlogEntryForm form = new BlogEntryForm();

		Long id = null;

		if(req.getParameter("id") != null){
			try {

				id = Long.parseLong(req.getParameter("id"));
			} catch(NullPointerException | NumberFormatException ex){
				req.setAttribute("error", "Wrong format of blog entry id.");
		        req.getRequestDispatcher("../../../WEB-INF/pages/error.jsp").forward(req, resp);
		        return;
			}
		}
		
		
		if(type.equals("edit")){

			form.fillExistingEntryAsForm(id);
		}
		

		req.setAttribute("form", form);
		req.setAttribute("type", type);
		
		req.getRequestDispatcher("../../../WEB-INF/pages/entityForm.jsp").forward(req, resp);

	}
	
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getParameter("metoda") != null && req.getParameter("metoda").equals("Objavi")){
			makeComment(req, resp);
			return;
		}
		
		String nick = req.getPathInfo().substring(1);
		
		String type = nick.split("/")[1];
		nick = nick.split("/")[0];
		
		if(req.getSession().getAttribute("current.user") == null || !nick.equals(((BlogUser)req.getSession().getAttribute("current.user")).getNick())){
			req.setAttribute("error", "You are not allowed to do this operation.");
	        req.getRequestDispatcher("../../../WEB-INF/pages/error.jsp").forward(req, resp);
	        return;
		}
		
		BlogEntryForm form = new BlogEntryForm();
		form.fillFromHttpRequest(req);
		
		if(req.getParameter("id") != null) {
			
			try {
				Long id = Long.parseLong(req.getParameter("id"));
			} catch(NullPointerException | NumberFormatException ex){
				req.setAttribute("error", "Wrong format of blog entry id.");
		        req.getRequestDispatcher("../../../WEB-INF/pages/error.jsp").forward(req, resp);
		        return;
			}
			
			form.setId(req.getParameter("id"));
		}
		
		form.validate();
		
		
		if(form.hasMistakes()){
			req.setAttribute("form", form);
			req.setAttribute("type", type);
			req.getRequestDispatcher("../../../WEB-INF/pages/entityForm.jsp").forward(req, resp);
			return;
		}
		
		form.fillEntry((BlogUser)req.getSession().getAttribute("current.user"));
		
		List<BlogEntry> blogEntries = DAOProvider.getDAO().getUserBlogEntries(nick);

		req.setAttribute("blogEntries", blogEntries);
		
		BlogUser author = DAOProvider.getDAO().getUserViaNick(nick);

		req.setAttribute("author", author);
		
		req.getRequestDispatcher("../../../WEB-INF/pages/authorList.jsp").forward(req, resp);
		
	}
	
	/**
	 * Makes comment.
	 * @param req Http request.
	 * @param resp Http response.
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void makeComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			CommentForm form = new CommentForm();
	        form.fillFromHttpRequest(req);
	        if (form.getUsersEMail().isEmpty()) {
	            form.setUsersEMail("Anonymous");
	        }

	        form.validate();

	        if (form.hasMistakes()) {
	        	req.setAttribute("form", form);
	        	prepareEntry(req, resp);
	            req.getRequestDispatcher("../../../WEB-INF/pages/showEntry.jsp").forward(req, resp);
	            return;
	        }

	        String eid = req.getPathInfo().substring(1).split("/")[1];
	        Long id = null;
	        try {
	            id = Long.valueOf(eid);
	        } catch (NullPointerException | NumberFormatException e) {
	        	req.setAttribute("error", "Provided blog entry id wasn't a number.");
		        req.getRequestDispatcher("../../../WEB-INF/pages/error.jsp").forward(req, resp);
	            return;
	        }

	        BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
	        BlogComment comment = new BlogComment();
	        form.fillComment(comment);

	        comment.setBlogEntry(entry);
	        comment.setPostedOn(new Date());
	        
	        entry.getComments().add(comment);
	        
	        DAOProvider.getDAO().saveComment(comment);

	        prepareEntry(req, resp);
	        req.getRequestDispatcher("/WEB-INF/pages/showEntry.jsp").forward(req, resp);

	}


	/**
	 * Prepares for entry presentation.
	 * @param req Http request.
	 * @param resp Http response.
	 * @throws ServletException
	 * @throws IOException
	 */
	private void prepareEntry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = req.getPathInfo().substring(1).split("/")[0];
		
		String eid = req.getPathInfo().substring(1).split("/")[1];

		Long id = null;
	        try {
	            id = Long.valueOf(eid);
	        } catch (NullPointerException | NumberFormatException e) {
	        	req.setAttribute("error", "Provided blog entry id wasn't a number.");
		        req.getRequestDispatcher("../../../WEB-INF/pages/error.jsp").forward(req, resp);
	            return;
	        }

	        DAO dao = DAOProvider.getDAO();

	        BlogUser user = dao.getUserViaNick(nick);
	        if (user == null) {
	        	req.setAttribute("error", "User with nick " + nick + " doesn't exist.");
		        req.getRequestDispatcher("../../../WEB-INF/pages/error.jsp").forward(req, resp);
	            return;
	        }
	        boolean sameUser = false;
	       
	        if (user.equals(req.getSession().getAttribute("current.user"))) {
	            sameUser = true;
	        }

	        BlogEntry entry = dao.getBlogEntry(id);
	        if (entry == null) {
	            req.setAttribute("error", "Blog entry with id " + id.toString() + " doesn't exist.");
		        req.getRequestDispatcher("../../../WEB-INF/pages/error.jsp").forward(req, resp);
	            return;
	        }

	        List<BlogComment> comments = entry.getComments();

	        req.setAttribute("sameUser", sameUser);
	        req.setAttribute("user", user);
	        req.setAttribute("entry", entry);
	        req.setAttribute("comments", comments);
	}
}
