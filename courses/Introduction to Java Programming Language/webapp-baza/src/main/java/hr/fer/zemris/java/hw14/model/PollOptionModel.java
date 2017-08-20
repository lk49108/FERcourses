/**
 * 
 */
package hr.fer.zemris.java.hw14.model;

/**
 * This class instance represents one poll option.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class PollOptionModel implements Comparable<PollOptionModel> {

	/**
	 * ID of this poll option.
	 */
	private long id;
	
	/**
	 * Option title.
	 */
	private String optionTitle;
	
	/**
	 * Option link.
	 */
	private String optionLink;
	
	/**
	 * ID of corresponding poll (of which this option are part).
	 */
	private long pollID;
	
	/**
	 * Number of votes which this poll option got from clients.
	 */
	private long votesCount;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	
	/**
	 * This constructor creates instance of this class.
	 * @param optionTitle Option title.
	 * @param optionLink Option link.
	 * @param pollID PollID.
	 * @param votesCount Number of initial votes.
	 */
	public PollOptionModel(String optionTitle, String optionLink, long pollID, long votesCount, long id) {
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.pollID = pollID;
		this.votesCount = votesCount;
		this.id = id;
	}
	
	/**
	 * Default constructor.
	 */
	public PollOptionModel(){}


	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the optionTitle
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * @param optionTitle the optionTitle to set
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * @return the optionLink
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * @param optionLink the optionLink to set
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * @return the pollID
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * @param pollID the pollID to set
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * @return the votesCount
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * @param votesCount the votesCount to set
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}

	@Override
	public int compareTo(PollOptionModel arg) {
		return -(int) (this.votesCount - arg.votesCount);
	}
	
	
	
}
