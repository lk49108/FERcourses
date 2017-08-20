package hr.fer.zemris.java.tecaj.hw5.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * This program reads data from file,
 * and based on read data it 
 * creates database for students 
 * which data is contained in this file. 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class StudentDB {
	/**
	 * This is a method from which program starts its execution.
	 * @param args Command line arguments, they are not used.
	 */
	public static void main(String[] args) throws IOException {
		
		List<String> lines = Files.readAllLines(Paths.get("./prva.txt"),StandardCharsets.UTF_8);
		
		StudentDatabase database = new StudentDatabase(lines);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		String readedLine;
		
		while(true){
			System.out.print("> ");
			
			readedLine = reader.readLine().trim();
			
			if(readedLine.toUpperCase().equals("EXIT")){
				System.out.println("Goodbye");
				System.exit(0);
			}
			
			if(seperateOrder(readedLine) == null){
				System.out.println("Wrong input.");
				System.out.println();
				continue;
			}
			
			if(seperateOrder(readedLine).equals("indexquery")){
				readedLine = readedLine.substring("indexquery".length()).trim();
				String JMBAG;
				try{
					 JMBAG = parseIndexQuery(readedLine);
				} catch(IllegalArgumentException ex){
					System.out.println("Wrong input syntax.");
					continue;
				}
				StudentRecord student = database.forJMBAG(JMBAG);
				if(student == null){
					System.out.println("No such student.");
					System.out.println();
					continue;
				}
				int JMBAGlength = 10;
				writeTableIndex(student, student.getLastName().length(), student.getFirstName().length(), JMBAGlength);
				printLastTableBorder(JMBAGlength, student.getLastName().length(), student.getFirstName().length());
				System.out.printf("Records selected: 1%n");
				System.out.println();
				continue;
			}
			
			//Must be query!!!
			readedLine = readedLine.substring("query".length()).trim();
			QueryFilter filterinho;
			try {
				filterinho = new QueryFilter(readedLine);
			} catch (IllegalArgumentException ex){
				System.out.println("Wrong input syntax.");
				continue;
			}
			List<StudentRecord> appropriateStudents = database.filter(filterinho);
			if(appropriateStudents.size() == 0){
				System.out.println("Records selected: 0");
				System.out.println();
				continue;
			}
			printOut(appropriateStudents);
			System.out.println();
			continue;
		}
	}
	
	/**
	 * This method is used for printing out student records which passed through selection process.
	 * @param appropriateStudents List of student records being printed out.
	 */
	private static void printOut(List<StudentRecord> appropriateStudents){
		StudentRecord record;
		int lastNameSize = 0;
		int firstNameSize = 0;
		int JMBAGlength = 10;
		for(int i = 0; i < appropriateStudents.size(); i++){
			record = appropriateStudents.get(i);
			if(record.getLastName().length() > lastNameSize){
				lastNameSize = record.getLastName().length();
			}
			if(record.getFirstName().length() > firstNameSize){
				firstNameSize = record.getFirstName().length();
			}
		}
		for(int i = 0; i < appropriateStudents.size(); i++){
			writeTableIndex(appropriateStudents.get(i), lastNameSize, firstNameSize, JMBAGlength);
		}
		printLastTableBorder(JMBAGlength, lastNameSize, firstNameSize);
		System.out.printf("Records selected: %d%n", appropriateStudents.size());
	}
	
	/**
	 * This method is used for finding out if command provided is "indexquery" type
	 * or it is "query" type.
	 * @param readedLine String representing command.
	 * @return String "indexquery" if given command is of "indexquery" type.
	 * String "query" if given command is of "query" type.
	 * Null is returned if given command satisfies any of two predescribed types.
	 */
	private static String seperateOrder(String readedLine){
		if(readedLine.toLowerCase().startsWith("indexquery")){
			return "indexquery";
		}
		if(readedLine.toLowerCase().startsWith("query")){
			return "query";
		}
		return null;
	}
	
	/**
	 * This method is used for parsing indexquery command.
	 * @param string "indexquery" type command.
	 * @return String representing JMBAG based on which students record is obtained.
	 */
	private static String parseIndexQuery(String string){
		if(string.toUpperCase().indexOf("JMBAG") == -1){
			throw new IllegalArgumentException("Index query command was not written correctly.");
		}
		if(string.indexOf('=') == -1){
			throw new IllegalArgumentException("Index query command was not written correctly.");
		}
		String[] subStrings = string.split("=");
		int beginIndex = subStrings[1].indexOf('\"');
		int endIndex = subStrings[1].indexOf('\"', beginIndex + 1);
		return subStrings[1].substring(beginIndex + 1, endIndex);
	}
	
	/**
	 * This method is used for printing out content of student records which satisfied
	 * given conditions.
	 * @param student Record which is currently being printed out.
	 * @param lastNameSize Length of students last name. 
	 * @param firstNameSize Length of students first name.
	 * @param JMBAGLength Length of students JMBAG.
	 */
	private static void writeTableIndex(StudentRecord student, int lastNameSize, int firstNameSize, int JMBAGLength){
		StringBuilder builder = new StringBuilder();
		builder.append("+");
		for(int i = 0; i < JMBAGLength + 2; i++){
			builder.append("=");
		}
		builder.append("+");
		for(int i = 0; i < lastNameSize + 2; i++){
			builder.append("=");
		}
		builder.append("+");
		for(int i = 0; i < firstNameSize + 2; i++){
			builder.append("=");
		}
		builder.append("+");
		for(int i = 0; i < 3; i++){
			builder.append("=");
		}
		builder.append("+");
		System.out.println(builder.toString());
		builder.delete(0, builder.length());
		builder.append("| ").append(student.getJMBAG()).append(" | ").append(student.getLastName());
		for(int i = 0; i < lastNameSize - student.getLastName().length(); i++){
			builder.append(" ");
		}
		builder.append(" | ").append(student.getFirstName());
		for(int i = 0; i < firstNameSize - student.getFirstName().length(); i++){
			builder.append(" ");
		}
		builder.append(" | ");
		builder.append(student.getFinalGrade()).append(" |");
		System.out.println(builder.toString());
	}
	
	/**
	 * This method is used for printing out last border of table based on a given parameters.
	 * @param JMBAGlength JMBAg length.
	 * @param lastNameSize Last name length.
	 * @param firstNameSize First name length.
	 */
	private static void printLastTableBorder(int JMBAGlength, int lastNameSize, int firstNameSize){
		StringBuilder builder = new StringBuilder().append("+");
		for(int i = 0; i < JMBAGlength + 2; i++){
			builder.append("=");
		}
		builder.append("+");
		for(int i = 0; i < lastNameSize + 2; i++){
			builder.append("=");
		}
		builder.append("+");
		for(int i = 0; i < firstNameSize + 2; i++){
			builder.append("=");
		}
		builder.append("+");
		for(int i = 0; i < 3; i++){
			builder.append("=");
		}
		builder.append("+");
		System.out.println(builder.toString());
	}
}
