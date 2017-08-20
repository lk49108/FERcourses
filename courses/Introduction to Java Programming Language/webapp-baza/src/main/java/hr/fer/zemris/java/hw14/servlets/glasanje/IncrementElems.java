package hr.fer.zemris.java.hw14.servlets.glasanje;

/**
 * Enumeration which is used for defining which elements of
 * pollOptions model can be incremented. It stops user
 * from increasing element(like id) which is not to be incremented.
 * @author Leonardo Kokot
 * @version 1.0
 */
public enum IncrementElems {

	/**
	 * Representation of element which can be increased.
	 */
	voteCounter ("votesCount");
	
	/**
	 * String property.
	 */
	private final String elem;
	
	/**
	 * Private constructor which does not allow user to create any further instances of this class.
	 * @param elem Property which is to be set.
	 */
	private IncrementElems(String elem){
		this.elem = elem;
	}
	
	/**
	 * Returns {@link #elem} property.
	 * @return {@link #elem}.
	 */
	public String getElem(){
		return this.elem;
	}
}
