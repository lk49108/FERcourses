package hr.fer.zemris.java.tecaj.hw6.demo5;

/**
 * Each instance of this class represents
 * one student record in specific class.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class StudentRecord {

	/**
	 * This property holds String type data
	 * that is actualy specific number which represents particular student.
	 */
	private final String jmbag;
	
	/**
	 * This property contains student's last name.
	 */
	private final String lastName;
	
	/**
	 * This property holds student's first name.
	 */
	private final String name;
	
	/**
	 * This property holds number of points that student achieved 
	 * at first exam.
	 */
	private final double miPoints;
	
	/**
	 * This property holds number of points that student achieved 
	 * at final exam.
	 */
	private final double ziPoints;
	
	/**
	 * This property holds number of points that student achieved 
	 * at laboratory work.
	 */
	private final double labPoints;
	
	/**
	 * This property holds information about final grade of this student at
	 * this specific class.
	 */
	private final int grade;

	/**
	 * This constructor creates an instance of this class.
	 * @param jmbag
	 * @param lastName
	 * @param name
	 * @param miPoints
	 * @param ziPoints
	 * @param labPoints
	 * @param grade
	 */
	public StudentRecord(String jmbag, String lastName, String name, double miPoints, double ziPoints, double labPoints, int grade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.name = name;
		this.miPoints = miPoints;
		this.ziPoints = ziPoints;
		this.labPoints = labPoints;
		this.grade = grade;
	}

	/**
	 * This getter returns student's jmbag.
	 * @return the jmbag.
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * This getter returns student's last name.
	 * @return the lastName.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * This getter returns student's first name.
	 * @return the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * This getter returns student's miPoints.
	 * @return the miPoints
	 */
	public double getMiPoints() {
		return miPoints;
	}

	/**
	 * This getter returns student's ziPoints.
	 * @return the ziPoints
	 */
	public double getZiPoints() {
		return ziPoints;
	}

	/**
	 * This getter returns student's laboratory points.
	 * @return the labPoints
	 */
	public double getLabPoints() {
		return labPoints;
	}

	/**
	 * This getter returns student's grade.
	 * @return the grade
	 */
	public int getGrade() {
		return grade;
	}
	
	@Override
	public String toString() {
		return "Student: Lastname " + lastName + ", " + name + ", " 
	+ jmbag + ", " + "MIPoints => " + miPoints + ", " + "ZIPoints => " 
				+ ziPoints  + ", " + "LABPoints => " +
				+ labPoints + ", " + grade;
	}
}
