/**
 * 
 */
package hr.fer.zemris.java.hw14.model;

/**
 * Model which represents one row in Polls table, i.e. it represents one poll.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class PollModel {

	/**
	 * Poll id.
	 */
	private long id;
	
	/**
	 * Poll title.
	 */
	private String title;
	
	/**
	 * Poll message.
	 */
	private String message;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
	
}
