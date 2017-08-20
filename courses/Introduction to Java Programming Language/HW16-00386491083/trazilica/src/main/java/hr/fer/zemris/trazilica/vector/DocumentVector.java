/**
 * 
 */
package hr.fer.zemris.trazilica.vector;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Vector which represents specific document.
 * It extends {@link NDimVectorAbstract} class.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class DocumentVector extends NDimVectorAbstract {

	/**
	 * Path representation of document which this
	 * instance of this vector represents.
	 */
	private Path directory;
			
	/**
	 * Map containing all words this document contains.
	 */
	private Map<String, Integer> wordsDocContain = new HashMap<String, Integer>();
	
	/**
	 * Constructor.
	 * Array of doubles, dimensions of this vector.
	 * @param dimensionsc Array of doubles, dimensions of this vector.
	 * @param directory Path of file.
	 */
	public DocumentVector(double[] dimensions, Path directory){
		this.dimensions = dimensions;
		this.directory = directory;
	}

	/**
	 * Constructor.
	 * @param directory File path.
 	 */
	public DocumentVector(Path directory){
		this.directory = directory;
	}
	
	/**
	 * Constructor.
	 */
	public DocumentVector() {
	}

	/**
	 * Adds a word to list of all words contained in this file 	
	 * @param word to be inserted into this local list of words.
	 */
	public void putWord(String word){
		if(!this.wordsDocContain.containsKey(word)){
			wordsDocContain.put(word, 1);

			return;
		} 
		
		wordsDocContain.put(word, wordsDocContain.get(word) + 1);
	}
	
	/**
	 * @return the directory
	 */
	public Path getDirectory() {
		return directory;
	}

	/**
	 * @return the wordsDocContain
	 */
	public Map<String, Integer> getWordsDocContain() {
		return wordsDocContain;
	}

	/**
	 * @param wordsDocContain the wordsDocContain to set
	 */
	public void setWordsDocContain(Map<String, Integer> wordsDocContain) {
		this.wordsDocContain = wordsDocContain;
	}
	
	/**
	 * Checks if this instance contains provided word.
	 * @param word Word which is being checked to be contained in this instance, i.e. file.
	 * @return True if this instance contains provided word, false is returned otherwise.
	 */
	public boolean containsWord(String word){
		if(this.wordsDocContain.containsKey(word) && this.wordsDocContain.get(word) > 0) return true;
		return false;
	}

	/**
	 * Quantity of words this instance contains and that equals provided one. 
	 * @param word Word.
	 * @return int number.
	 */
	public int numberOfWords(String word){
		if(!this.wordsDocContain.containsKey(word)) return 0;
		return this.wordsDocContain.get(word);
	}
	
	/**
	 *
	 * @param directory the directory to set
	 */
	public void setDirectory(Path directory) {
		this.directory = directory;
	}

	
}
