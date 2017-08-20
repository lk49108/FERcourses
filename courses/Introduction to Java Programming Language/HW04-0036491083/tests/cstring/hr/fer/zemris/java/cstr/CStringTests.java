package hr.fer.zemris.java.cstr;

import static org.junit.Assert.*;

import org.junit.Test;

public class CStringTests {
	
	@Test (expected = NullPointerException.class)
	public void testNullPointer1(){
		int a = 2;
		int b = 3;
		new CString(null, a, b);
	}
	
	@Test 
	public void testWhenSizeISBiggerThanArraySizeAllows(){
		char[] a = {'a', 'b', 'c', 'd'};
		assertEquals("3 is an excpected size of a newly created CString",  new CString(a, 1, 5).length(), 3);
	}
	
	@Test 
	public void checkingConstructorAndCharAtMethod(){
		char[] a = {'a', 'b', 'c', 'd', 'e', 'f'};
		checkCharactersInString(new CString(a), a);
	}
	
	@Test 
	public void checking_fromString_StaticMethodAnd_toCharArray_Method(){
		String string = "Bok, ja sam Leonardo.";
		assertArrayEquals(CString.fromString(string).toCharArray(), string.toCharArray());
	}
	
	@Test 
	public void StringsShouldBeEqual(){
		String string = "Dobar dan.";
		assertEquals(CString.fromString(string).toString(), string);
	}
	
	@Test 
	public void excpectedMinusOne(){
		char[] a = {'a', 'b', 'c', 'd'};
		assertEquals(-1, new CString(a).indexOf('e'));
	}
	
	@Test 
	public void excpectedTrue(){
		CString cstring1 = CString.fromString("Bokerino.");
		CString cstring2 = CString.fromString("Bok");
		assertTrue(cstring1.startsWith(cstring2));
	}
	
	@Test 
	public void excpectedFalse(){
		CString cstring1 = CString.fromString("Bokerino.");
		CString cstring2 = CString.fromString("rin");
		assertFalse(cstring1.endsWith(cstring2));
	}
	
	@Test 
	public void excpectedTrue2(){
		CString cstring1 = CString.fromString("Bok, kako si?");
		CString cstring2 = CString.fromString(", kak");
		assertTrue(cstring1.contains(cstring2));
	}
	
	@Test 
	public void excpectedFalse2(){
		CString cstring1 = CString.fromString("Bok, kako si?");
		CString cstring2 = CString.fromString(",  kak");
		assertFalse(cstring1.contains(cstring2));
	}
	
	@Test 
	public void substringTest(){
		CString cstring1 = CString.fromString("Teče voda.");
		CString cstring2 = cstring1.substring(2, 6);
		assertEquals("če v", cstring2.toString());
	}
	
	@Test 
	public void leftTest(){
		CString cstring1 = CString.fromString("Teče.");
		CString cstring2 = cstring1.left(10);
		CString cstring3 = cstring1.left(2);
		assertEquals("Teče.", cstring2.toString());
		assertEquals("Te", cstring3.toString());
	}
	
	@Test 
	public void rightTestAndSubstringAdvanced(){
		CString cstring1 = CString.fromString("Varoš,marob,zagreš.");
		CString cstring2 = cstring1.right(10);
		CString cstring3 = cstring2.substring(2, 5);
		CString cstring4 = cstring3.right(2);
		assertEquals("ob,zagreš.", cstring2.toString());
		assertEquals(",za", cstring3.toString());
		assertEquals("za", cstring4.toString());
	}
	
	@Test 
	public void addTest(){
		CString cstring1 = CString.fromString("Varoš,");
		char[] a = {'a', 'b', 'c'};
		CString cstring2 = new CString(a);
		CString cstring3 = cstring1.add(cstring2);
		assertEquals("Varoš,abc", cstring3.toString());
	}
	
	@Test 
	public void replaceAllTest1(){
		CString cstring1 = CString.fromString("Alamalamgalam");
		CString cstring2 = cstring1.replaceAll('m', 'I');
		assertEquals("AlaIalaIgalaI", cstring2.toString());
	}
	
	@Test 
	public void replaceAllTest2(){
		CString cstring1 = CString.fromString("AMito");
		CString cstring2 = cstring1.replaceAll(CString.fromString("Mito"), CString.fromString("gur"));
		assertEquals("Agur", cstring2.toString());
	}
	
	@Test 
	public void replaceAllTest3(){
		CString cstring1 = CString.fromString("ababab");
		CString cstring2 = cstring1.replaceAll(CString.fromString("ab"), CString.fromString("abab"));
		assertEquals("abababababab", cstring2.toString());
	}
	
	// Helper method.
	private void checkCharactersInString(CString string, char[] niz){
		int i = 0;
		for(char pom : niz){
			assertEquals(string.charAt(i), pom);
			i++;
		}
	}
	
}
