package hr.fer.zemris.java.hw13.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class which provides methods for writing
 * and reading from .txt files used in voting
 * for favorite band. There is also method which
 * creates voting count file if latter do not exist.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class FileUtil {

	/**
	 * Number of bands which are available to be voted for.
	 */
	public static int NUM_OF_BANDS = 4;
	
	/**
	 * Reads all lines from file and arranges them in {@link List}.
	 * @param file {@link Path} instance representing file which is to be read.
	 * @return List consisting lines of provided file.
	 * @throws IOException
	 */
	public static List<String> readFileRows(Path file){
		List<String> lines = new ArrayList<>();

		try(BufferedReader iStream = new BufferedReader(Files.newBufferedReader(file, StandardCharsets.UTF_8))){
			
			String line = null;
			while(true){
				line = iStream.readLine();
				if(line == null || line.isEmpty()) break;
				lines.add(line);
			}
			
		} catch(IOException ex){
			System.out.println("Error occured while reading " + file + " file.");
		}
		
		return lines;

	}
	
	/**
	 * Creates voting results file.
	 * @param file {@link Path} instance representing file which is about to be created.
	 * @throws IOException
	 */
	public static void createVoteResultsFile(Path file) {		
		try {
			file.toFile().createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try(BufferedWriter oStream = new BufferedWriter(Files.newBufferedWriter(file, StandardCharsets.UTF_8))) {
			
			for(int i = 0; i < NUM_OF_BANDS; i++){
				String forWrite = i + "\t0";
				oStream.write(forWrite);
				oStream.newLine();
			}
			
		} catch (IOException e) {
			System.out.println("Error occured while creating " + file + " file.");		
		} 
				
		
	}
	
	/**
	 * Updates appropriate band votes counter in provided file.
	 * @param file provided file.
	 * @param band Ordinary number of band which votes number counter has to be updated.
	 * @throws IOException 
	 */
	public static void updateVoteFile(Path file, int band){
		if(band <= 0) return;
		
		List<String> readedLines = readFileRows(file);
				
		String[] changeLine = readedLines.get(band - 1).split("\t");
		
		int voteCountNew = Integer.parseInt(changeLine[1].trim()) + 1;
		
		readedLines.set(band - 1, changeLine[0].trim() + "\t" + voteCountNew);
				
		try(BufferedWriter oStream = new BufferedWriter(Files.newBufferedWriter(file, StandardCharsets.UTF_8))){
			for(String row : readedLines){
				oStream.write(row);
				oStream.newLine();
			}
		} catch(IOException ex){
			System.out.println("Error occured while updating " + file + " file.");
		}
		
	}
}
