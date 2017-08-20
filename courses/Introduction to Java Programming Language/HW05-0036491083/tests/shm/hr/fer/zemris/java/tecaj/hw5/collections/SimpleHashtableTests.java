package hr.fer.zemris.java.tecaj.hw5.collections;

import static org.junit.Assert.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.Test;

public class SimpleHashtableTests {

	@Test (expected = IllegalArgumentException.class)
	public void testCheckIllegalSizeParameter(){
		new SimpleHashtable<String, Integer>(0);
	}
	
	@Test
	public void testZeroSize(){
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		assertEquals(0, table.size());
	}
	
	@Test
	public void testOneAddingToTableMethods(){
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		table.put("Ines", 73);
		assertEquals(1, table.size());
		assertEquals("[Ines=73]", table.toString());
	}
	
	@Test
	public void testFewAddings(){
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		table.put("Ines", 73);
		table.put("Leo", -1);
		table.put("Marko", 5);
		table.put("Ivan", 5);
		assertEquals(4, table.size());
		assertEquals("[Marko=5, Ines=73, Leo=-1, Ivan=5]", table.toString());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testNullKey(){
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		table.put(null, 3);
	}
	
	@Test 
	public void testgetMethod(){
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		table.put("Ines", 5);
		assertEquals(new Integer(5), (Integer) table.get("Ines"));
	}
	
	@Test 
	public void testgetMethodNull(){
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		table.put("Matija", null);
		assertEquals(null, table.get("Ines"));
	}
	
	@Test 
	public void testremoveMethod(){
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		table.put("Ines", 73);
		table.put("Leo", -1);
		table.put("Marko", 5);
		table.put("Ivan", 5);
		table.remove("Marko");
		assertEquals(3, table.size());
		assertEquals("[Ines=73, Leo=-1, Ivan=5]", table.toString());
		assertEquals(3, table.size());
		assertEquals(null, table.get("Marko"));
	}
	
	@Test
	public void testOverridingValue(){
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		table.put("Ines", 73);
		table.put("Ines", 5);
		assertEquals(5, table.get("Ines").intValue());
		assertEquals(1, table.size());
	}
	
	@Test
	public void testremoveMethodAndIsEmpty(){
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		table.put("Matija", 10);
		table.remove("Matija");
		assertEquals(0, table.size());
		assertEquals(true, table.isEmpty());
	}
	
	@Test
	public void testContainsKey(){
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		table.put("Ines", 73);
		table.put("Leo", 6);
		assertEquals(true, table.containsKey("Ines"));
		assertEquals(true, table.containsKey("Leo"));
		assertEquals(false, table.containsKey("Netko"));
	}
	
	@Test
	public void testContainsValue(){
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		table.put("Ines", 73);
		table.put("Leo", 6);
		table.put("Matija", 10);
		table.put("Ivan", 10);
		assertEquals(true, table.containsValue(73));
		assertEquals(true, table.containsValue(10));
		assertEquals(false, table.containsValue(53));
	}
	
	@Test
	public void testContainsValueWithOverRiding(){
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		table.put("Ines", 73);
		table.put("Ines", 5);
		assertEquals(true, table.containsValue(5));
		assertEquals(false, table.containsValue(73));
		assertEquals(false, table.containsValue(0));
	}
	
	@Test
	public void testIsEmpty(){
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		assertEquals(true, table.isEmpty());
	}
	
	@Test
	public void testExcpectedAllocation(){
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(2);
		table.put("Ines", 73);
		table.put("Leo", 1);
		table.put("Matija", 10);
		table.put("Ivan", 10);
		table.put("Petra", 5);
		table.put("Nik", 10);
		table.put("Lea", 5);
		assertEquals(7, table.size());
	}
	
	@Test
	public void testIterator(){
		// create collection:
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				iter.remove(); // sam iterator kontrolirano uklanja trenutni element
			}
		}
		assertEquals("[Ante=2, Jasna=2, Kristina=5]",examMarks.toString());
	}
	
	@Test (expected = IllegalStateException.class)
	public void testTwoTimesRemoveInIteratorInaRow(){
		// create collection:
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				iter.remove();
				iter.remove();
			}
		}
	}
	
	@Test (expected = ConcurrentModificationException.class)
	public void testModificationsOutsideOfIteratorWhileIterating(){
		// create collection:
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				examMarks.remove("Ivana");
			}
		}
	}
}

