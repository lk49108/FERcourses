import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack;
import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

public class ObjectMultistackJunitTests {

	@Test
	public void testValueWrapperCreation(){
		ValueWrapper wrapper = new ValueWrapper(null); 
		assertEquals(true, wrapper.getValue().equals(0));
	}

	@Test
	public void testValueWrapperCreation2(){
		ValueWrapper wrapper = new ValueWrapper(2); 
		assertTrue(wrapper.getValue() instanceof Integer);
	}
	
	@Test
	public void testValueWrapperCreation3(){
		ValueWrapper wrapper = new ValueWrapper(2.3); 
		assertTrue(wrapper.getValue() instanceof Double);
	}
	
	@Test
	public void testValueWrapperCreation4(){
		ValueWrapper wrapper = new ValueWrapper(2e5); 
		assertTrue(wrapper.getValue() instanceof Double);
	}
	
	@Test
	public void testValueWrapperCreation5(){
		ValueWrapper wrapper = new ValueWrapper("5.73"); 
		assertTrue(wrapper.getValue() instanceof Double);
	}
	
	@Test
	public void testValueWrapperCreation6(){
		ValueWrapper wrapper = new ValueWrapper("6.35e-2"); 
		assertTrue(wrapper.getValue() instanceof Double);
	}
	
	@Test
	public void testValueWrapperCreation7(){
		ValueWrapper wrapper = new ValueWrapper("7.43E3"); 
		assertTrue(wrapper.getValue() instanceof Double);
	}
	
	@Test
	public void testValueWrapperCreation8(){
		ValueWrapper wrapper = new ValueWrapper("5"); 
		assertTrue(wrapper.getValue() instanceof Integer);
	}
	
	@Test(expected = RuntimeException.class)
	public void testValueWrapperCreation9FalseInput(){
		ValueWrapper wrapper = new ValueWrapper("5.3.4"); 
	}
	
	@Test(expected = RuntimeException.class)
	public void testValueWrapperCreation9FalseInput2(){
		ValueWrapper wrapper = new ValueWrapper(new Object()); 
	}
	
	@Test
	public void testValueWrapperOperations(){
		ValueWrapper wrapper = new ValueWrapper("5"); 
		wrapper.increment("7");
		assertTrue(wrapper.getValue() instanceof Integer);
		assertEquals(true, wrapper.getValue().equals(12));
	}
	
	@Test
	public void testValueWrapperOperations2(){
		ValueWrapper wrapper = new ValueWrapper("5"); 
		wrapper.decrement("7");
		assertEquals(-2, wrapper.getValue());
	}
	
	@Test
	public void testValueWrapperOperations3(){
		ValueWrapper wrapper = new ValueWrapper("5.6"); 
		wrapper.increment("7");
		assertEquals(12.6, wrapper.getValue());
		assertTrue(wrapper.getValue() instanceof Double);
	}
	
	@Test
	public void testValueWrapperOperations4(){
		ValueWrapper wrapper = new ValueWrapper("5.1"); 
		wrapper.decrement("6.0");
		assertTrue(Math.abs((double)wrapper.getValue() + 0.9) <= 0.000001);
		assertTrue(wrapper.getValue() instanceof Double);
	}
	
	@Test
	public void testValueWrapperOperations5(){
		ValueWrapper wrapper = new ValueWrapper("7"); 
		wrapper.multiply(6);
		assertEquals(42, wrapper.getValue());
		assertTrue(wrapper.getValue() instanceof Integer);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testValueWrapperOperations6DividingByZero(){
		ValueWrapper wrapper = new ValueWrapper("7"); 
		wrapper.divide(null);
	}
	
	@Test
	public void testValueWrapperOperations7(){
		ValueWrapper wrapper = new ValueWrapper("7"); 
		wrapper.divide(6);
		assertEquals(1, wrapper.getValue());
		assertTrue(wrapper.getValue() instanceof Integer);
	}
	
	@Test
	public void testValueWrapperOperations8(){
		ValueWrapper wrapper = new ValueWrapper("7.0"); 
		wrapper.decrement(null);
		assertEquals(7.0, wrapper.getValue());
		assertTrue(wrapper.getValue() instanceof Double);
	}
	
	@Test
	public void testValueWrapperOperations9(){
		ValueWrapper wrapper = new ValueWrapper("7.0"); 	
		assertTrue(wrapper.numCompare(null) > 0);
	}
	
	@Test
	public void testValueWrapperOperations10(){
		ValueWrapper wrapper = new ValueWrapper("7e-2"); 	
		assertTrue(wrapper.numCompare("24") < 0);
	}
	
	@Test
	public void testValueWrapperOperations11(){
		ValueWrapper wrapper = new ValueWrapper(null); 	
		assertTrue(wrapper.numCompare(null) == 0);
	}
	
	@Test
	public void testValueWrapperOperations12(){
		ValueWrapper wrapper = new ValueWrapper(7e-34); 	
		assertTrue(wrapper.numCompare(null) == 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testObjectMultistack(){
		ObjectMultistack multistack = new ObjectMultistack();	
		multistack.push(null, new ValueWrapper(56));
	}
	
	@Test
	public void testObjectMultistack2(){
		ObjectMultistack multistack = new ObjectMultistack();	
		multistack.push("Štef", new ValueWrapper(56));
		assertEquals(56, multistack.peek("Štef").getValue());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testObjectMultistack3(){
		ObjectMultistack multistack = new ObjectMultistack();	
		multistack.push("Štef", new ValueWrapper(56));
		multistack.peek("Štefica");
	}
	
	@Test
	public void testObjectMultistack4(){
		ObjectMultistack multistack = new ObjectMultistack();	
		multistack.push("Štef", new ValueWrapper(56.4));
		assertEquals(56.4, multistack.peek("Štef").getValue());
		assertEquals(56.4, multistack.pop("Štef").getValue());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testObjectMultistack5(){
		ObjectMultistack multistack = new ObjectMultistack();	
		multistack.push("Štef", new ValueWrapper(56));
		multistack.pop("Štef");
		multistack.pop("Štef");
	}
	
	@Test
	public void testObjectMultistack6(){
		ObjectMultistack multistack = new ObjectMultistack();	
		multistack.push("Štef", new ValueWrapper(56));	
		multistack.push("Štef", new ValueWrapper("73.3"));
		assertEquals(73.3, multistack.peek("Štef").getValue());
		assertEquals(73.3, multistack.pop("Štef").getValue());
		assertEquals(56, multistack.peek("Štef").getValue());	
		assertEquals(56, multistack.pop("Štef").getValue());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testObjectMultistack7(){
		ObjectMultistack multistack = new ObjectMultistack();	
		multistack.push("Štef", new ValueWrapper(56));	
		multistack.push("Štef", new ValueWrapper("73.3"));
		assertEquals(73.3, multistack.peek("Štef").getValue());
		assertEquals(73.3, multistack.pop("Štef").getValue());
		assertEquals(56, multistack.peek("Štef").getValue());	
		assertEquals(56, multistack.pop("Štef").getValue());
		assertEquals(56, multistack.peek("Štef").getValue());
	}
}
