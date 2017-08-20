package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.forms.BlogEntryForm;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Interface toward dataBase, i.e. persistent part of this web-application. 
 * @author Leonardo Kokot
 * @version 1.0
 */
public interface DAO {

	/**
	 * Obtains entry with provided id.
	 * @param id id.
	 * @return BlogEntry instance.
	 * @throws DAOException
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Obtains user with provided id.
	 * @param id id.
	 * @return BlogUser instance.
	 * @throws DAOException
	 */
	public BlogUser getBlogUser(Long id) throws DAOException;
	
	/**
	 * Obtains all registered users from database.
	 * @return List of registered users.
	 * @throws DAOException
	 */
	public List<BlogUser> getBlogUsers() throws DAOException;
	
	/**
	 * Obtains user determined by nick and password hash.
	 * @param nick nick.
	 * @param passwordHash password hash.
	 * @return BlogUser instance, or null if there is no such user in database.
	 * @throws DAOException
	 */
	public BlogUser getUserNickAndPassword(String nick, String passwordHash) throws DAOException;

	/**
	 * Obtains user determined by nick from database.
	 * @param nick nick.
	 * @return BlogUser instance or null if there is no user with provided nick in database.
	 * @throws DAOException
	 */
	public BlogUser getUserViaNick(String nick) throws DAOException;

	/**
	 * Obtains user determined by email from database.
	 * @param nick nick.
	 * @return BlogUser instance or null if there is no user with provided email in database.
	 * @throws DAOException
	 */
	public BlogUser getUserViaEmail(String email) throws DAOException;

	/**
	 * Saves user instance into database.
	 * @param user BlogUser instance to be saved.
	 * @throws DAOException
	 */
	public void saveUser(BlogUser user) throws DAOException;

	/**
	 * Obtains provided user blog entries.
	 * @param user User, represented by its nick String.
	 * @return List of blog entries corresponding to provided user.
	 * @throws DAOException
	 */
	public List<BlogEntry> getUserBlogEntries(String user) throws DAOException;

	/**
	 * Creates blog entry and stores it into database.
	 * @param blogEntryForm Blog entry form from which BlogEntry instance is created
	 * and then saved into database.
	 * @param creator Creator of blog entry.
	 * @return BlogEntry instance.
	 * @throws DAOException
	 */
	public BlogEntry createBlogEntry(BlogEntryForm blogEntryForm, BlogUser creator) throws DAOException;

	/**
	 * Updates blog entry from database and stores it back into database.
	 * @param blogEntryForm Blog entry form which id is used to obtain right 
	 * blog entry from database.
	 * @return BlogEntry instance.
	 * @throws DAOException
	 */
	public BlogEntry updateBlogEntry(BlogEntryForm blogEntryForm) throws DAOException;
	
	/**
	 * Saves comment into database.
	 * @param comment Comment to be saved.
	 * @throws DAOException
	 */
	public void saveComment(BlogComment comment) throws DAOException;
}