package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class/program is used for 
 * cripting and decripting files.
 * It is also used for obtaining digest of specific file,, 
 * and checking of the same.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class Crypto {
	
	/**
	 * This is a method from which program starts its execution.
	 * @param args Command line arguments, used for obtaining files name and
	 * what kind of operation will this program do.
	 */
	public static void main(String[] args) {
		if(args.length == 2 && args[0].toLowerCase().equals("checksha")){
			digest(args[1]);
		}
		else if(args.length == 3 && args[0].toLowerCase().equals("encrypt")){
			cription("encrypt", args[1], args[2]);
		}
		else if(args.length == 3 && args[0].toLowerCase().equals("decrypt")){
			cription("decrypt", args[1], args[2]);
		}
	}
	
	/**
	 * This method is used for checking if
	 * expected digest equals the real one.
	 * @param nameOfFile String representin file name which
	 * is being digested.
	 */
	private static void digest(String nameOfFile){
		System.out.print("Please provide excpected sha-256 digest for " 
				+ nameOfFile + ":\n> "); 
		byte[] buffer = new byte[1024];
		int readedDataSize;
		try (InputStream input = new BufferedInputStream(
				new FileInputStream(
						new File(nameOfFile)));
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(new BufferedInputStream(System.in))
						);){
			String expectedSha = reader.readLine();
			MessageDigest digester = MessageDigest.getInstance("SHA-256");
			while(true){
				readedDataSize = input.read(buffer);
				if(readedDataSize < 1) break;
				digester.update(buffer, 0, readedDataSize);
			}
			byte[] hash = digester.digest();
			byte[] expected = hexToByte(expectedSha);
			if(checkEqualArr(hash, expected)){
				System.out.println("Digesting completed. Digest of " + nameOfFile
						+ " matches excpected digest.");
			} else {
				System.out.println("Digesting completed. Digest of " + nameOfFile
						+ " does not match the excpected digest."
						+ "\nDigest was: " + byteToHex(expected));
			}
		} catch (NoSuchAlgorithmException | IOException ex) {
			ex.printStackTrace();
		} 
	}
	
	/**
	 * This method is used for creating environment suitable for encryption
	 * or decryption, i.e. it creates inptu and output stream, creates instance of
	 * cipher and delegates work of cryption to method crypt.
	 * @param mode Mode to which cipher will be set to, i.e. will it
	 * decrypt or encrypt file.
	 * @param fileSrc Name of file source.
	 * @param fileDest Name of file destination.
	 */
	private static void cription(String mode, String fileSrc, String fileDest){
		System.out.println("Please provide password as "
				+ "hex-encoded text (16 bytes, i.e. 32 hex-digits:\n>");
		try(BufferedReader reader = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(System.in))
				);
			InputStream input = new BufferedInputStream(
					new FileInputStream(
						new File("./" + fileSrc)));
			OutputStream output = new BufferedOutputStream(
					new FileOutputStream(
						new File("./" + fileDest)))){
			String keyText = reader.readLine();
			System.out.println("Please provide initialization vector"
					+ " as hex-encoded text (32 hex-digits):\n>");
			String ivText = reader.readLine();
			SecretKeySpec keySpec = new SecretKeySpec(hexToByte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(hexToByte(ivText));
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(mode.equals("encrypt") ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			crypt(cipher, input, output);
		} catch (IOException | InvalidKeyException | InvalidAlgorithmParameterException 
				| NoSuchPaddingException | NoSuchAlgorithmException 
				| IllegalBlockSizeException | BadPaddingException ex){
			System.out.println(ex.getStackTrace());
		}
	}
	
	/**
	 * This method is used for encryption/decryption of file which is obtained through input stream. 
	 * @param cipher Cipher instance variable. It executes cryption.
 	 * @param input Input stream from which file is read.
	 * @param output Output stream through which cripted file is being created.
	 * @throws IOException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private static void crypt(Cipher cipher, InputStream input, OutputStream output) throws IOException, IllegalBlockSizeException, BadPaddingException{
		byte[] buffer = new byte[1024];
		int readedSize;
		byte[] decBytes;
		while(true){
			readedSize = input.read(buffer);
			if(readedSize < 1)
				break;
			decBytes = cipher.update(buffer, 0, readedSize);
			output.write(decBytes, 0, decBytes.length);
		}
		decBytes = cipher.doFinal();
		output.write(decBytes, 0, decBytes.length);
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
	
	/**
	 * This method is used for checking if two provided arrays are equal.
	 * @param arr1 First array.
	 * @param arr2 Second array.
	 * @return True if statement is true, false otherwise.
	 */
	private static boolean checkEqualArr(byte[] arr1, byte[] arr2){
		if(arr1.length != arr2.length) return false;
		for(int i = 0; i < arr1.length; i++){
			if(arr1[i] != arr2[i]) return false;
		}
		return true;
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
}
