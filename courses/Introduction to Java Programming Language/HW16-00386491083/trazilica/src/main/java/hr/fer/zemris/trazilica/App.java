package hr.fer.zemris.trazilica;

import java.io.IOException;

import hr.fer.zemris.trazilica.shell.Shell;

/**
 * Program starts its execution from this class main method.
 * It is expected from user to provide main directory path to this program
 * as command line argument.
 * @author Leonardo Kokot
 * @version 1.0
 */ 
public class App {
	
	/**
	 * Program starts its execution from this method.
	 * @param args Command line arguments.
	 * @throws IOException 
	 */
    public static void main( String[] args ) throws IOException{
    	if(args.length != 1){
    		System.out.println("Wrong number of parameters.");
    	}
    	
    	Shell shell = new Shell(args[0].trim());
    	
    
    }


}
