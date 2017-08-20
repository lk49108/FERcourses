package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Blog comment class.
 * @author Leonardo Kokot
 * @version 1.0
 */
@Entity
@Table(name="blog_comments")
public class BlogComment {

	/**
	 * Id.
	 */
	@Id @GeneratedValue
	private Long id;
	
	/**
	 * Blog entry.
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	private BlogEntry blogEntry;
	
	/**
	 * User's e-mail.
	 */
	@Column(nullable=false, length=60)
	private String usersEMail;
	
	/**
	 * Message.
	 */
	@Column(nullable=false, length=2*1024)
	private String message;
	
	/**
	 * Time this comment was posted on.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date postedOn;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the blogEntry
	 */
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * @param blogEntry the blogEntry to set
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
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
	 * @return the postedOn
	 */
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * @param postedOn the postedOn to set
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}