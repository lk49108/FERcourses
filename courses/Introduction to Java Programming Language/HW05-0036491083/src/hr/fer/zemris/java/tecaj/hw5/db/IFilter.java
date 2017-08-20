package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * This interface(classes that implements it) is used for checking if given students record satisfies 
 * specific conditions.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public interface IFilter {
	/**
	 * This method checks if given student's record satisfies some
	 * conditions.
	 * @param record Student's record that is provided.
	 * @return True if student's record satisfies given conditions, false otherwise.
	 */
	public boolean accepts(StudentRecord record);
}
