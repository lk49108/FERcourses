package hr.fer.zemris.demo;
	
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.List;
	
/**
 * This class is just demo class for testing mochitos.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class DemoTest {
	@Test
	public void test1() {
		@SuppressWarnings("unchecked")
		List<Integer> list = (List<Integer>)mock(List.class);
		when(list.get(0)).thenReturn(5);
		when(list.get(1)).thenReturn(6);
		int rez = metoda(list);
		assertEquals(17, rez);
		verify(list,times(1)).get(0);
		verify(list,times(2)).get(1);
		verify(list, times(1)).set(3, 17);
}
	
/**
* Ova metoda bi trebala u listu na poziciju 3 upisati
* vrijednost koja je jednaka zbroju elementa na poziciji 0
* i dva puta elementa na poziciji 1.
* @param list ulaz i izlaz
* @return izračunatu sumu
*/
public int metoda(List<Integer> list) {
int suma = list.get(0) + list.get(1) + list.get(1);
list.set(3, suma);
return suma;
}
}