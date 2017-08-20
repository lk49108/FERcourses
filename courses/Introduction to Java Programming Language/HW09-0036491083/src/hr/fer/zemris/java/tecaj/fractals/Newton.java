/**
 * 
 */
package hr.fer.zemris.java.tecaj.fractals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.tecaj.hw9.complex.Complex;
import hr.fer.zemris.java.tecaj.hw9.complex.ComplexPolynomial;
import hr.fer.zemris.java.tecaj.hw9.complex.ComplexRootedPolynomial;

/**
 * This program allows user to enter roots of 
 * a {@link ComplexPolynomial} and then it starts fractal viewer
 * and displays the corresponding fractal.
 * General syntax for complex numbers is of form a + ib or a - ib where
 * parts that are zero can be dropped, but
 * not both (empty string is not legal complex number); 
 * for example, zero can be given as 0, i0, 0+i0, 0-i0. If
 * there is 'i' present but no b is given, it is assumed that b=1.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class Newton {

	/**
	 * This is the main method from which program {@link #Newton()} starts its execution.
	 * @param args Command line arguments which are not used in this program. 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\n" 
				+ "Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		Complex[] roots = getRoots(reader);
		
		if(reader != null){
			try {
				reader.close();
			} catch (IOException ex){}
		}
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		
		ComplexRootedPolynomial polynomialRooted = new ComplexRootedPolynomial(roots);
		
		Newton_Raphson.setPOLYNOMIAL(polynomialRooted);
		
		FractalViewer.show(new MyProducer(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new ThreadFactory() {
			
			@Override
			public Thread newThread(Runnable r) {
		                Thread t = Executors.defaultThreadFactory().newThread(r);
		                t.setDaemon(true);
		                return t;
			}
		})));	

	}
	
	/**
	 * Allows user to provide complex roots, processes
	 * input and returns array of all provided roots. 
	 * @param reader {@link BufferedReader} instance which is used to read users input.
	 * @return {@link Complex} numbers array, users input.
	 * @throws IOException 
	 */
	private static Complex[] getRoots(BufferedReader reader) throws IOException {
		List<Complex> list = new ArrayList<>();
		int rootIndex = 1;
		while(true){
			System.out.print("Root " + rootIndex + "> ");
			String readedRoot = reader.readLine().trim();
			if(readedRoot.length() == 0){
				System.out.println("Some kind of input is expected, but none was provided.");
				continue;
			}
			if(readedRoot.toLowerCase().equals("done")){
				break;
			}
			try {
				list.add(parseComplex(readedRoot));
			} catch (IllegalArgumentException ex){
				ex.getMessage();
				continue;
			} catch (Exception e) {
				System.out.println("Wrong input, please provide another.");
				continue;
			}
			rootIndex++;
			continue;
		}
		return list.toArray(new Complex[list.size()]);
	}

	/**
	 * Parses complex number and based on result creates appropriate
	 * {@link Complex} instance.
	 * @param readedRoot String representing complex number that is to be parsed.
	 * @return {@link Complex} number.
	 * @throws IllegalArgumentException If wrong format of complex number was provided.
	 */
	private static Complex parseComplex(String readedRoot) throws IllegalArgumentException {
		if(readedRoot.toLowerCase().equals("i")) {
			return new Complex(0, 1);
		}
		if(readedRoot.toLowerCase().equals("-i")) {
			return new Complex(0, -1);
		}
		boolean isImaginary = (readedRoot.indexOf('i') != -1);
		boolean isReal = false;
		if(!isImaginary){
			isReal = true;
		} else if(readedRoot.indexOf('i') > firstIndexOfDigit(readedRoot)){
			isReal = true;
		}
		double imaginaryPart = 0;
		double realPart = 0;
		if(isReal){
			realPart = extractRealPart(readedRoot); 
		}
		if(isImaginary){
			imaginaryPart = extractImaginaryPart(readedRoot);
		}
		return new Complex(realPart, imaginaryPart);
	}
	
	/**
	 * Extracts imaginary part of a {@link Complex} number.
	 * @param readedRoot String representation of {@link Complex} number.
	 * @return Imaginary part of complex number as double value.
	 */
	private static double extractImaginaryPart(String readedRoot) {
		int indexOfi = readedRoot.toLowerCase().indexOf("i");
		if(indexOfi == readedRoot.length() - 1){
			return 1;
		}
		double parsedImaginaryPart = 0;
		try {
			parsedImaginaryPart = Double.parseDouble(
					readedRoot.substring(indexOfi + 1, readedRoot.length()));
		} catch (NumberFormatException ex){
			throw new IllegalArgumentException("Wrong input format.");
		}
		if(indexOfi == 0) {
			return parsedImaginaryPart;
		} else if(readedRoot.charAt(indexOfi - 1) == '-' 
				|| readedRoot.charAt(indexOfi - 2) == '-'){
			return -parsedImaginaryPart;
		}
		return parsedImaginaryPart;
	}

	/**
	 * Extracts real part of a {@link Complex} number.
	 * @param readedRoot String representation of {@link Complex} number.
	 * @return Real part of complex number as double value.
	 */
	private static double extractRealPart(String readedRoot) {
		int endIndex = 0;
		double parsedRealPart = 0;
		try {
			while(true){
				if(Character.isDigit(readedRoot.charAt(endIndex)) 
						&& (endIndex == readedRoot.length() - 1 
						|| Character.isWhitespace(readedRoot.charAt(endIndex + 1)))){
					parsedRealPart = Double.parseDouble(readedRoot.substring(0, endIndex + 1));
					break;
				} else {
					endIndex++;
				}
			}
		} catch (NumberFormatException ex){
			throw new IllegalArgumentException("Wrong input format.");
		}
		return parsedRealPart;
	}

	/**
	 * Derives index of first digit in provided String and
	 * returns that same index.
	 * @param readedRoot String for which
	 * is check first appearance of digit.
	 * @return Index of first digit.
	 */
	private static int firstIndexOfDigit(String readedRoot) {
		int i = 0;
		while(!Character.isDigit(readedRoot.charAt(i))){
			i++;
		}
		return i;
	}
}
