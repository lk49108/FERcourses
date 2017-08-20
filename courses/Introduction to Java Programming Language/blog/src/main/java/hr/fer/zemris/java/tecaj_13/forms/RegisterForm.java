/**
 * 
 */
package hr.fer.zemris.java.tecaj_13.forms;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.util.DigestUtil;

/**
 * Register form.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class RegisterForm extends AbstractForm{

	/**
	 * First name.
	 */
	private String firstName;
	
	/**
	 * Last name.
	 */
	private String lastName;
	
	/**
	 * Nick.
	 */
	private String nick;
	
	/**
	 * Password.
	 */
	private String password;
	
	/**
	 * E-mail.
	 */
	private String email;
		
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Constructor.
	 */
	public RegisterForm(){
		this.nick = "";
		this.password = "";
		this.firstName = "";
		this.lastName = "";
		this.email = "";
	}
	
	@Override
	public void fillFromHttpRequest(HttpServletRequest req){
		this.nick = prepare(req.getParameter("nick"));
		this.password = prepare(req.getParameter("password"));
		this.lastName = prepare(req.getParameter("lastName"));
		this.firstName = prepare(req.getParameter("firstName"));
		this.email = prepare(req.getParameter("email"));
	}
	
	@Override
	public void validate(){
		mistakes.clear();
		
		if(this.nick.isEmpty()){
			mistakes.put("nick", "Nick is mandatory.");
		} else {
			if(DAOProvider.getDAO().getUserViaNick(this.nick) != null){
				mistakes.put("nick", "Nick already exists.");
			}
		}
		if(this.password.isEmpty()){
			mistakes.put("password", "Password is mandatory");
		} 
		
		if(this.firstName.isEmpty()){
			mistakes.put("firstName", "First name is mandatory.");
		}
		
		if(this.lastName.isEmpty()){
			mistakes.put("lastName", "Last name is mandatory.");
		}
		
		if(this.email.isEmpty()){
			mistakes.put("email", "Email is mandatory.");
		} else if (DAOProvider.getDAO().getUserViaEmail(this.email) != null){
			mistakes.put("email", "Email already used.");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if(l<3 || p==-1 || p==0 || p==l-1) {
				mistakes.put("email", "Wrong email format.");
			}
		}
		
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Makes user.
	 * @return BlogUser instance.
	 */
	public BlogUser makeUser() {
			BlogUser user = new BlogUser();
			user.setEmail(email);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setNick(nick);
			user.setPasswordHash(DigestUtil.digest(password));
			DAOProvider.getDAO().saveUser(user);
			return user;
	}
}
