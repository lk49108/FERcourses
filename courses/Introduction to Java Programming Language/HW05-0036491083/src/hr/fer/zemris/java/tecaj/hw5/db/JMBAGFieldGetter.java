package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * This class implements {@link IFieldValueGetter} interface.
 * It is used for obtaining student's JMBAG.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class JMBAGFieldGetter implements IFieldValueGetter{

	@Override
	public String get(StudentRecord record) {
		return record.getJMBAG();
	}

}
