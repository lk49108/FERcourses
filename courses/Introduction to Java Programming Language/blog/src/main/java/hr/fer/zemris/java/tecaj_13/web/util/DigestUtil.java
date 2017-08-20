/**
 * 
 */
package hr.fer.zemris.java.tecaj_13.web.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class which implements utilization methods for digesting 
 * passwords.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class DigestUtil {

	/**
	 * Determines type of digesting.
	 */
	private final static String TYPE_OF_DIGESTING = "SHA-1";
	
	/**
	 * Charset used in digesting.
	 */
	private static final Charset CHARSET = Charset.defaultCharset();
	
	/**
	 * Digests provided password.
	 * @param password Password.
	 * @return String representation of digested password.
	 */
	public static String digest(String password){
		String digestedPassword = null;
		try {
			MessageDigest digester = MessageDigest.getInstance(TYPE_OF_DIGESTING);
			byte[] digestedBytes = digester.digest(password.getBytes(CHARSET));
			digestedPassword = DigestUtil.byteToHex(digestedBytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return digestedPassword;
	}
	
	/**
	 * This method is used for turning array of bytes
	 * into hexadecimal incription of the same.
	 * @param bites Array of bites.
	 * @return String representation of hexadecimal incription 
	 * of array of bites provided through bites argument. 
	 */
	public static String byteToHex(byte[] bites){
		StringBuilder builder = new StringBuilder();
		for(byte b : bites){
			builder.append(String.format("%02X", b));
		}
		return builder.toString().toLowerCase();
	}
	
	/**
	 * This method is used for turning hexadecimal
	 * incription into array of bites.
	 * @param hexForm String representing hexadecimal incription.
	 * @return Array of bytes incripted.
	 */
	public static byte[] hexToByte(String hexForm){
		byte[] storage = new byte[hexForm.length() / 2];
		for(int i = 0; i < storage.length; i++){
			storage[i] = (byte) (Byte.parseByte(hexForm.substring(2 * i, 2 * i + 1), 16) * 16 
					+ Byte.parseByte(hexForm.substring(2 * i + 1, 2 * i + 2), 16));
		}
		return storage;
	}
}
