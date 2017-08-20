package hr.fer.zemris.java.simplecomp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.executionUnit.ExecutionUnitImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;
import hr.fer.zemris.java.simplecomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;

/**
 * This program is used to work with programs
 * written in .txt files and to execut them.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class Simulator {
	
	/**
	 * This is the method from which program starts its execution.
	 * @param args Commandline arguments, can be used for
	 * obtaining path to .txt file in which program is written.
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		String path = null;
		
		if(args.length != 1){
			System.out.println("Please, insert path to file with asembly code.");
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(System.in)
					);
			
			path = reader.readLine().trim();
		} else {
			path = args[0].trim();
		}
		
		if(!Files.exists(Paths.get(path))){
			System.err.println("Given path to .txt file do not exist.");
			System.exit(1);
		}
		
		try{
			// Stvori računalo s 256 memorijskih lokacija i 16 registara
			Computer comp = new ComputerImpl(256, 16);
			
			// Stvori objekt koji zna stvarati primjerke instrukcija
			InstructionCreator creator = new InstructionCreatorImpl(
					"hr.fer.zemris.java.simplecomp.impl.instructions");
			
			// Napuni memoriju računala programom iz datoteke; instrukcije stvaraj
			// uporabom predanog objekta za stvaranje instrukcija
			ProgramParser.parse(
			path,
			comp,
			creator
			);
			
			// Stvori izvršnu jedinicu
			ExecutionUnit exec = new ExecutionUnitImpl();
			// Izvedi program
			exec.go(comp);
		} catch (Exception ex){
			System.err.println("Something went wrong while executing program "
					+ "writtten in " + path.toString() + ".");
			System.err.print(ex.getStackTrace());
		}
		
	}
}
