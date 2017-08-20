package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable;

/**
 * This class represents student database.
 * It contains data for each student(its JMBAG, first name,
 * last name and final grade).
 * @author Leonardo Kokot
 * @version 1.0
 */
public class StudentDatabase {
	
	/**
	 * This list holds data for students in a sequence they were added.
	 */
	private ArrayList<StudentRecord> list;
	
	/**
	 * This list holds data for students as a hashTable, it is used for fast retrieval
	 * of students records if their JMBAG is known.
	 */
	private SimpleHashtable<String, StudentRecord> hashTable;
	
	/** 
	 * This constructor creates an internal list and hashtable of
	 * student records. Additionally it also creates
	 * an index for fast retrieval of student records when JMBAG is known.
	 * It initializes <code>list</code> and <code>hashTable</code> instance variables
	 * based on data parameter.
	 * @param data Reference to String array, each element of this array contains
	 * record for specific student.
	 */
	public StudentDatabase(List<String> data){
		list = new ArrayList<>();
		hashTable = new SimpleHashtable<>();
		initInstVar(data);
	}
	
	/**
	 * This method uses index to obtain requested record in O(1).
	 * If record does not exist, the method returns null.
	 * If null is given as parameter null is returned.
	 * @param jmbag JMBAG for which record of corresponding
	 * student is returned.
	 * @return Record of student which JMBAG was provided as parameter.
	 * Null is returned if null is given as parameter or there is no student
	 * in database with a given JMBAG.
	 */
	public StudentRecord forJMBAG(String jmbag){
		return hashTable.get(jmbag);
	}
	
	/**
	 * This method loops through all student records
	 * in its internal list; it calls
	 * accepts method on given filter-object with
	 * current record; each record for which accepts returns true is
	 * added to temporary list and this list is
	 * then returned by the filter method.
	 * @param filter
	 * @return
	 */
	public List<StudentRecord> filter(IFilter filter){
		ArrayList<StudentRecord> temporaryList = new ArrayList<>();
		for(StudentRecord record : list){
			if(filter.accepts(record)){
				temporaryList.add(record);
			}
		}
		return temporaryList;
	}
	
	
	/**
	 * This method is used for creation StudentRecords from a given list
	 * which contains units which stores students data.
	 * @param lines List<String> type reference. This list contains students data
	 * which is to be processed.
	 */
	private void initInstVar(List<String> lines){
		for(int i = 0; i < lines.size(); i++){
			String[] recordSegments = lines.get(i).split("\\s+");
			for(int j = 0; j < recordSegments.length; j++){
				recordSegments[j] = recordSegments[j].trim();
			}
			if(recordSegments.length > 4){
				recordSegments[1] = recordSegments[1] + " " + recordSegments[2];
				StudentRecord record = new StudentRecord(recordSegments[0], recordSegments[1], recordSegments[3], Integer.parseInt(recordSegments[4]));
				list.add(record);
				hashTable.put(recordSegments[0], record);
			}
			else{
				StudentRecord record = new StudentRecord(recordSegments[0], recordSegments[1], recordSegments[2], Integer.parseInt(recordSegments[3]));
				list.add(record);
				hashTable.put(recordSegments[0], record);
			}
			
		}
	}
}
