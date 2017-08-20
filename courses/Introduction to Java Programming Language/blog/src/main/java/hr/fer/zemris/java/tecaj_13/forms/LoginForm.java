/**
 * 
 */
package hr.fer.zemris.java.tecaj_13.forms;



import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.util.DigestUtil;

/**
 * Login form.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class LoginForm extends AbstractForm{
	
	/**
	 * Nick.
	 */
	private String nick;
	
	/**
	 * Password.
	 */
	private String password;

	
	/**
	 * Constructor.
	 */
	public LoginForm(){
		this.nick = "";
		this.password = "";
	}
	
	
	@Override
	public void fillFromHttpRequest(HttpServletRequest req){
		this.nick = prepare(req.getParameter("nick"));
		this.password = prepare(req.getParameter("password"));
	}
	
	@Override
	public void validate(){
		mistakes.clear();
		
		if(this.nick.isEmpty()){
			mistakes.put("nick", "Nick is mandatory.");
		} else {
			if(DAOProvider.getDAO().getUserViaNick(this.nick) == null){
				mistakes.put("nick", "Nick does not exist.");
			}
		}
		if(this.password.isEmpty()){
			mistakes.put("password", "Password is mandatory");
		} else {
			if(DAOProvider.getDAO().getUserNickAndPassword(nick, DigestUtil.digest(password)) == null && DAOProvider.getDAO().getUserViaNick(this.nick) != null){
				mistakes.put("password", "Wrong password.");
			}
		}
		
		
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
	 * Provides appropriate user from this form.
	 * @return BlogUser.
	 */
	public BlogUser fillUser() {
		return DAOProvider.getDAO().getUserViaNick(this.nick);
	}
	
	
}
