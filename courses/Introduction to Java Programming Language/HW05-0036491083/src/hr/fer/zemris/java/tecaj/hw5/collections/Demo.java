package hr.fer.zemris.java.tecaj.hw5.collections;

public class Demo {

	
	public static void main(String[] args) {
		// create collection:
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		for(SimpleHashtable.TableEntry<String,Integer> pair : examMarks) {
		System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}
		
		System.out.println();
		System.out.println();
		for(SimpleHashtable.TableEntry<String,Integer> pair1 : examMarks) {
			for(SimpleHashtable.TableEntry<String,Integer> pair2 : examMarks) {
				System.out.printf("(%s => %d) - (%s => %d)%n",pair1.getKey(), pair1.getValue(),pair2.getKey(), pair2.getValue());
			}
		}
		
	}
}


	