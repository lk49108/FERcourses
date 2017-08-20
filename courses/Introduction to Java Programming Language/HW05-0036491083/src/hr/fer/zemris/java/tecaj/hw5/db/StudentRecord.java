package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * Instance of this class represents the data for single student, i.e. its
 * JMBAG, lastName, firstName and finalGrade.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class StudentRecord {

	/**
	 * Instance variable representing JMBAG of this student.
	 */
	private final String JMBAG;
	
	/**
	 * Instance variable representing last name of this student.
	 */
	private final String lastName;
	
	/**
	 * Instance variable representing first name of this student.
	 */
	private final String firstName;
	
	/**
	 * Instance variable representing final grade of this student.
	 */
	private final int finalGrade;

	/**
	 * This constructor creates an instance of this class, i.e. it creates data
	 * for specific student.
	 * @param lastName Last name of this student
	 * @param firstName First name of this student.
	 * @param finalGrade Final grade of this student.
	 */
	public StudentRecord(String JMBAG, String lastName, String firstName, int finalGrade) {
		super();
		this.JMBAG = JMBAG;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}
	
	@Override
	public int hashCode() { 
		return Integer.parseInt(JMBAG);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof StudentRecord)){
			return false;
		}
		StudentRecord second = (StudentRecord) obj;
		return this.JMBAG.equals(second.JMBAG);
	}

	/**
	 * This is a getter for JMBAG instance variable.
	 * @return JMBAG instance varialbe.
	 */
	public String getJMBAG() {
		return JMBAG;
	}

	/**
	 * This is a getter for lastName instance variable.
	 * @return Last name instance variable.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * This is a getter for firstName instance variable.
	 * @return firstName instance variable.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * This is a getter for finalGrade instance variable.
	 * @return finalGrade instance variable.
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
	
}