package hr.fer.zemris.java.tecaj.hwo7.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw07.crypto.Crypto;

public class Tests {

	@Test
	public void testbyteToHexMethod(){
		Crypto cripto = new Crypto();
		byte[] bites = {2, 2, 3};
		assertEquals("020203", Crypto.byteToHex(bites));
	}

	@Test
	public void testbyteToHexMethod2(){
		Crypto cripto = new Crypto();
		byte[] bites = {25, -22, -1};
		assertEquals("19eaff", Crypto.byteToHex(bites));
	}
	
	@Test
	public void testbyteToHexMethod3(){
		Crypto cripto = new Crypto();
		byte[] bites = {5, 73, -55};
		assertEquals("0549c9", Crypto.byteToHex(bites));
	}
	
	@Test
	public void testhexToByteMethod(){
		Crypto cripto = new Crypto();
		byte[] bites = Crypto.hexToByte("0d00");
		assertEquals(13, bites[0]);
		assertEquals(0, bites[1]);
	}
	
	@Test
	public void testhexToByteMethod2(){
		Crypto cripto = new Crypto();
		byte[] bites = Crypto.hexToByte("0d00ffcc");
		assertEquals(13, bites[0]);
		assertEquals(0, bites[1]);
		assertEquals(-1, bites[2]);
		assertEquals(-52, bites[3]);
		assertEquals(4, bites.length);
	}
}
