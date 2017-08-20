/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw6.demo5;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * This program represents usage of data streams.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class StudentDemo {

	/**
	 * Method from which this program starts its execution.
	 * @param args Command line arguments which are not used here.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		List<String> lines = Files.readAllLines(Paths.get("./studenti.txt"), 
				StandardCharsets.UTF_8);
		List<StudentRecord> records = null;
		try{
			records = convert(lines);
		} catch (ClassCastException ex){
			System.err.println("Provided .txt file is wrongly formatted.");
		}
		
		long broj = records.stream()
		.filter(p -> p.getMiPoints() + p.getZiPoints() > 25)
		.count();
		
		System.out.println("Number of students whose sum of points\n" 
		+ "achieved on MI and ZI greater than 40 is: " + broj);
		
		
		long broj5 = records.stream()
				.filter(p -> p.getGrade() == 5)
				.count();
		
		System.out.println("Number of students whit a grade " 
				+ "equal to 5 is: " + broj5);
		
		
		List<StudentRecord> odlikasi = records.stream()
				.filter(p -> p.getGrade() == 5)
				.collect(Collectors.toList());
		
		odlikasi.stream()
		.forEach(p -> System.out.println(p));
		
		System.out.println();
		
		List<StudentRecord> odlikasiSortirano = records.stream()
				.filter(p -> p.getGrade() == 5)
				.sorted((p1, p2) -> Double.compare(p2.getLabPoints() + p2.getMiPoints()
				+ p2.getZiPoints(), p1.getLabPoints() + p1.getMiPoints()
				+ p1.getZiPoints()))
				.collect(Collectors.toList());
		
		odlikasiSortirano.stream()
		.forEach(p -> System.out.println(p));
		
		
		List<String> nepolozeniJMBAGovi = records.stream()
				.filter(p -> p.getGrade() == 1)
				.sorted((p1, p2) -> p1.getJmbag().compareTo(p2.getJmbag()))
				.map(p -> p.toString())
				.collect(Collectors.toList());
				
		System.out.println();
		
		nepolozeniJMBAGovi.stream()
		.forEach(p -> System.out.println(p));
			
		System.out.println();
		
		Map<Integer, Integer> mapaPoOcjenama2 = records.stream()
				.collect(Collectors.toMap(student -> student.getGrade(),(p) -> 1, 
						new BinaryOperator<Integer>() {
							public Integer apply(Integer arg0, Integer arg1) {
								return arg0 + arg1;
						};
				}));
		
		for(int i = 1; i <=5; i++){
			System.out.println(mapaPoOcjenama2.get(i));
		}
		
		System.out.println();
		System.out.println();
		System.out.println();
		
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = records.stream()
				.collect(Collectors.partitioningBy(p -> p.getGrade() > 1));
		
		List<StudentRecord> prosli = prolazNeprolaz.get(true);
		List<StudentRecord> pali = prolazNeprolaz.get(false);
		
		System.out.println("Prosli:");
		System.out.println();
		
		for(StudentRecord record : prosli){
			System.out.println(record);
		}

		System.out.println();
		System.out.println();
		System.out.println("Pali:");
		System.out.println();
		
		for(StudentRecord record : pali){
			System.out.println(record);
		}
	}

	/**
	 * This method is used for creation of a list 
	 * of student indexes in this particular class.
	 * @param list Lines read from provided .txt file.
	 * @return List containing student records.
	 * @throws ClassCastException if .txt file is not formated in right way.
	 */
	private static List<StudentRecord> convert(List<String> list) {
		List<StudentRecord> internalStudentList = new ArrayList<>();
		for(String line : list){
			String[] lineParts = line.split("\\s+");
			try{
				internalStudentList.add(new StudentRecord(lineParts[0], lineParts[1], lineParts[2], Double.parseDouble(lineParts[3]), Double.parseDouble(lineParts[4]), Double.parseDouble(lineParts[5]), Integer.parseInt(lineParts[6])));
			} catch (ClassCastException ex){
				throw ex;
			} catch (ArrayIndexOutOfBoundsException ex){
				return internalStudentList;
			}
		}
		return internalStudentList;
	}
}
