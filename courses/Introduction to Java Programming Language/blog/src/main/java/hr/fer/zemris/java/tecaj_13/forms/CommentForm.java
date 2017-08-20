/**
 * 
 */
package hr.fer.zemris.java.tecaj_13.forms;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;

/**
 * Comment form.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class CommentForm extends AbstractForm {

		/**
		 * Users e-mail.
		 */
		private String usersEMail;
		
		/**
		 * Message.
		 */
	    private String message;
	    
	    /**
	     * Constructor.
	     */
	    public CommentForm(){
	    	this.usersEMail = "";
	    	this.message = "";
	    }
	    
		@Override
	    public void fillFromHttpRequest(HttpServletRequest req) {
	        usersEMail = prepare(req.getParameter("usersEMail"));
	        message = prepare(req.getParameter("message"));
	        System.out.println(usersEMail);
	        System.out.println(message);
	    }
	    
	    /**
	     * Fills form from comment.
	     * 
	     * @param comment comment.
	     */
	    public void fillFromComment(BlogComment comment) {
	        usersEMail = comment.getUsersEMail();
	        message = comment.getMessage();
	    }

		@Override
	    public void validate() {
			if(!(usersEMail.isEmpty() || usersEMail.equalsIgnoreCase("Anonymous"))){
				int l = usersEMail.length();
				int p = usersEMail.indexOf('@');
				if(l<3 || p==-1 || p==0 || p==l-1) {
					mistakes.put("usersEMail", "Wrong email format.");
				}
			}
			
	        if (message.isEmpty()) {
	            mistakes.put("message", "You must provide some text as part of a comment.");
	        }
	    }

		/**
	     * @return the usersEMail
	     */
	    public String getUsersEMail() {
	        return usersEMail;
	    }

	    /**
	     * @param usersEMail the usersEMail to set
	     */
	    public void setUsersEMail(String usersEMail) {
	        this.usersEMail = usersEMail;
	    }

	    /**
	     * @return the message
	     */
	    public String getMessage() {
	        return message;
	    }

	    /**
	     * @param message the message to set
	     */
	    public void setMessage(String message) {
	        this.message = message;
	    }

	    /**
	     * Fills comment from form.
	     * 
	     * @param comment Comment to be fulfilled.
	     */
	    public void fillComment(BlogComment comment) {
	        comment.setMessage(message);
	        comment.setUsersEMail(usersEMail);
	    }

}
