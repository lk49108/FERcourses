package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * This interface is used as a root for classes which
 * are used for getting specific data from students record through
 * get method.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public interface IFieldValueGetter {

	/**
	 * This method is used for getting specific data from students record.
	 * Which data will be obtained through this method depends on its implementation.
	 * @param record Student record from which specific data is received.
	 * @return String holding specific data from students record.
	 */
	public String get(StudentRecord record);
}
