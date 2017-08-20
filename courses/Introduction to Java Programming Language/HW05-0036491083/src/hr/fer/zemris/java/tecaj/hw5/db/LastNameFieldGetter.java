package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * This class implements {@link IFieldValueGetter} interface.
 * It is used for obtaining student's last name.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class LastNameFieldGetter implements IFieldValueGetter{
	
	@Override
	public String get(StudentRecord record) {
		return record.getLastName();
	}
}
