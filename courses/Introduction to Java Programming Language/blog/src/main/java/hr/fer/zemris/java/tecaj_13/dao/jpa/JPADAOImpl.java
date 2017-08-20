package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.forms.BlogEntryForm;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Implementation of DAO interface.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public BlogUser getBlogUser(Long id) throws DAOException {
		BlogUser blogUser = JPAEMProvider.getEntityManager().find(BlogUser.class, id);
		return blogUser;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogUser> getBlogUsers() throws DAOException {
		List<BlogUser> users = new ArrayList<>();
		users = (List<BlogUser>)JPAEMProvider.getEntityManager().createQuery("SELECT u FROM BlogUser u").getResultList();
		return users;
	}
	
	@Override
	public BlogUser getUserNickAndPassword(String nick, String passwordHash) throws DAOException {
		@SuppressWarnings("unchecked")
		List<BlogUser> blogUser = (List<BlogUser>) JPAEMProvider.getEntityManager().createQuery("SELECT u FROM BlogUser AS u WHERE u.nick=:nick and u.passwordHash=:passwordHash")
				.setParameter("nick", nick).setParameter("passwordHash", passwordHash).getResultList();
		if(blogUser.isEmpty()) return null;
		return blogUser.get(0);
	}

	@Override
	public BlogUser getUserViaNick(String nick) throws DAOException {
		@SuppressWarnings("unchecked")
		List<BlogUser> users = (List<BlogUser>) JPAEMProvider.getEntityManager().createQuery(
                "select u from BlogUser as u where u.nick=:nick").setParameter("nick", nick).getResultList();
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
   }

	@Override
	public BlogUser getUserViaEmail(String email) throws DAOException {
		@SuppressWarnings("unchecked")
		List<BlogUser> users = (List<BlogUser>) JPAEMProvider.getEntityManager().createQuery(
                "select u from BlogUser as u where u.email=:email").setParameter("email", email).getResultList();
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
	}

	@Override
	public void saveUser(BlogUser user) throws DAOException {
		if(user.getId() == null){
			JPAEMProvider.getEntityManager().persist(user);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogEntry> getUserBlogEntries(String user) throws DAOException {
		List<BlogEntry> entries = (List<BlogEntry>) JPAEMProvider.getEntityManager().createQuery(
                "select e from BlogEntry as e where e.creator=:creator").setParameter("creator", getUserViaNick(user)).getResultList();
		if(entries.isEmpty()){
			return null;
		}
		return entries;
	}

	@Override
	public BlogEntry createBlogEntry(BlogEntryForm blogEntryForm, BlogUser creator) throws DAOException {
		BlogEntry entry = new BlogEntry();
		entry.setCreatedAt(new Date());
		entry.setLastModifiedAt(entry.getCreatedAt());
		entry.setTitle(blogEntryForm.getTitle());
		entry.setText(blogEntryForm.getText());
		entry.setCreator(creator);
		
		JPAEMProvider.getEntityManager().persist(entry);
		
		creator.getBlogEntries().add(entry);
		
		return entry;
	}

	@Override
	public BlogEntry updateBlogEntry(BlogEntryForm blogEntryForm) throws DAOException {
		BlogEntry entry = getBlogEntry(Long.parseLong(blogEntryForm.getId()));
		entry.setLastModifiedAt(new Date());
		entry.setText(blogEntryForm.getText());
		entry.setTitle(blogEntryForm.getTitle());
		return entry;
	}

	@Override
	public void saveComment(BlogComment comment) throws DAOException {
		 EntityManager em = JPAEMProvider.getEntityManager();

	     if (comment.getId() == null) {
	        em.persist(comment);
	     } else {
	        em.merge(comment);
	     }
	}

	
}