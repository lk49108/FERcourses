/**
 * 
 */
package hr.fer.zemris.trazilica.shell;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import hr.fer.zemris.trazilica.vector.DocumentVector;

/**
 * This class represents shell through which user communicates
 * with program.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class Shell {

	
	/**
	 * Map containing pairs Path to document and its corresponding {@link DocumentVector} instance.
	 */
	private Map<Path, DocumentVector> documentVectors = new HashMap<Path, DocumentVector>();
	
	/**
	 * List holding vocabulary.
	 */
	List<String> vocabulary = new ArrayList<String>();
	
	/**
	 * Path to file in which there is a list of stoping words for croatian language.
	 */
	private final static String STOP_WORDS_FILE = "E:\\Java_sve\\DZ\\HW16-0036491083\\trazilica\\src\\main\\java\\hr\\fer\\zemris\\trazilica\\shell\\stoppingWordsFile\\zaustavne_rijeci.txt";
	
	/**
	 * List of stopping words for Croatian language.
	 */
	private List<String> stopWords = new ArrayList<String>();
	
	/**
	 * Main directory path.
	 */
	private final Path mainDir;

	/**
	 * Map containing pairs of doubles(tfidf - similarity value) and it's corresponding DoucumentVector instance.
	 */
	private Map<Double, DocumentVector> similarityMap;
	
	/**
	 * Sorted double keys, used for storing similaritity values in it. Keys are used for obtaining appropriate document descriptor instances from
	 * map.
	 */
	private List<Double> keyListSorted;

	/**
	 * Statement used in user-shell comunnication.
	 */
	private String TO_BIG_INDEX_STATEMENT = "There is no result with provided index.";
	
	/**
	 * Statement used in user-shell comunnication.
	 */
	private static final String QUERY_ZERO_WORDS_STATEMENT = "Query does not contain any word";

	/**
	 * Statement used in user-shell comunnication.
	 */
	private static final String COMMAND_DOES_NOT_EXIST_STATEMENT = "Command not recognized";

	/**
	 * Statement used in user-shell comunnication.
	 */
	private static int NUMBER_OF_SHOWN_RESULTS = 10;

	/**
	 * Statement used in user-shell comunnication.
	 */
	private static final String EXIT_STATEMENT = "exit";

	/**
	 * Statement used in user-shell comunnication.
	 */
	private static final String RESULTS_STATEMENT = "results";
	
	/**
	 * Statement used in user-shell comunnication.
	 */
	private static final String TYPE_STATEMENT = "type";
	
	/**
	 * Statement used in user-shell comunnication.
	 */
	private static final String ENTER_COMMAND_STATEMENT = "Enter command > ";
	
	/**
	 * Statement used in user-shell comunnication.
	 */
	private static final String QUERY_STATEMENT = "query";
	
	/**
	 * Statement used in user-shell comunnication.
	 */
	private static final String QUERY_VALID_WORDS_STATEMENT = "Query is: ";
	
	/**
	 * Statement used in user-shell comunnication.
	 */
	private static final String QUERY_RESULTS_STATEMENT = "Best " + NUMBER_OF_SHOWN_RESULTS + " results";
	
	/**
	 * Output stream.
	 */
	private final static OutputStream O_STREAM = System.out;
	
	/**
	 * Input stream.
	 */
	private final static InputStream I_STREAM = System.in;
	
	/**
	 * Reader used in reading users commands from keybord.
	 */
	private final static BufferedReader READER = new BufferedReader(new InputStreamReader(I_STREAM));
	
	/**
	 * Charset used by this shell.
	 */
	private final static Charset CHARSET = Charset.forName("UTF-8");
	
	/**
	 * Prints string to proper output stream (using proper charset).
	 * @param s String to be written.
	 * @throws IOException
	 */
	public void write(String s) throws IOException{
		O_STREAM.write(s.getBytes(CHARSET));
	}
	
	/**
	 * Prints string to proper output stream (using proper charset).
	 * @param s String to be written.
	 * @throws IOException
	 */
	public void writeLn(String s) throws IOException{
		O_STREAM.write(s.getBytes(CHARSET));
		O_STREAM.write(System.lineSeparator().getBytes(CHARSET));
	}
	
	/**
	 * Constructor.
	 * @param dir Main directory used in this program.
	 * @throws IOException
	 */
	public Shell(String dir) throws IOException{
		this.mainDir = Paths.get(dir);
		if(!Files.exists(mainDir) || !Files.isDirectory(mainDir)){
			this.writeLn("Provided main directory is acctualy not a directory.");
		}
		
		readStopingWords();

		buildVocabulary();
		
		createVectors();
		
		this.writeLn("Vocabulary size " + this.vocabularySize());
				
		work();
	}
	
	/**
	 * Method which is called after shell build-up. It implements user's communication
	 * with this program. 
	 * @throws IOException
	 */
	private void work() throws IOException {
		while(true){
			this.writeLn("");

			this.write(ENTER_COMMAND_STATEMENT);
			
			String command = this.read();
			
			if(command.equalsIgnoreCase(EXIT_STATEMENT )){
				this.READER.close();
				this.I_STREAM.close();
				this.O_STREAM.close();
				break;
			}
			
			if(command.equalsIgnoreCase(RESULTS_STATEMENT)){
				resultsCommand();
				continue;
			}
			
			if(command.split("\\s++")[0].equalsIgnoreCase(QUERY_STATEMENT)){
				queryCommand(command.substring(QUERY_STATEMENT.length()));
				continue;
			}
			
			if(command.split("\\s++")[0].equalsIgnoreCase(TYPE_STATEMENT)){
				if(this.keyListSorted == null){
					this.writeLn("This command does not have sense for now");
					continue;
				}
				typeCommand(command.substring(TYPE_STATEMENT.length()).trim());
				continue;
			}
			
			this.writeLn(COMMAND_DOES_NOT_EXIST_STATEMENT);
			
		}
	}

	/**
	 * Method which implements type shell's command.
	 * @param index Index which determines document which content will be printed out to user.
	 * @throws IOException
	 */
	private void typeCommand(String index) throws IOException {
		try {
			int intIndex = Integer.parseInt(index);
			
			if(intIndex >= this.keyListSorted.size() || intIndex < 0){
				this.writeLn(TO_BIG_INDEX_STATEMENT );
				return;
			}
			
			DocumentVector choosedDocument = this.similarityMap.get(this.keyListSorted.get(intIndex));
			
			this.writeLn("------------------------------------------------------------------");
			this.writeLn("Document" + choosedDocument.getDirectory().toString());
			this.writeLn("------------------------------------------------------------------");
			
			try (BufferedInputStream fileReader = new BufferedInputStream(Files.newInputStream(choosedDocument.getDirectory()))){
				
				byte[] buffer = new byte[1024];
				while(true){
					
					int size = fileReader.read(buffer);
					if(size < 1) break;
					
					this.writeLn(new String(buffer, this.CHARSET));
				}
				
			} catch (Exception ex){
				this.writeLn("Error occured while reading file.");
			}
					
			
		} catch (NumberFormatException ex){
			this.writeLn("Wrong command format");
			return;
		}
	}

	/**
	 * Query shell command.
	 * @param words String containing words which user provided as query words.
	 * @throws IOException
	 */
	private void queryCommand(String words) throws IOException {
		String[] wordsArray = words.split("\\s++");
		
		
		List<String> validWords = new ArrayList<>();
		for(String word : wordsArray){
			word = word.trim();

			if(word.isEmpty()) continue;
			
			if(this.stopWords.contains(word)){
				continue;
			}
			
			validWords.add(word);
		}
		
		if(validWords.size() == 0){
			this.writeLn(QUERY_ZERO_WORDS_STATEMENT );
			return;
		}
		
		this.write(QUERY_VALID_WORDS_STATEMENT + "[");
		
		int i = 0;
		for(String word : validWords){
			if(i == validWords.size() - 1){
				this.writeLn(word + "]");
			} else {
				this.write(word + ", ");
			}
			i++;
		}
		
		this.writeLn(QUERY_RESULTS_STATEMENT);
		
		DocumentVector queryVector = createQueryVector(validWords);
		
		this.similarityMap = new HashMap<>();

		for(DocumentVector fileVector : this.documentVectors.values()){
			double similarity = DocumentVector.scalarProduct(queryVector, fileVector);
						
			this.similarityMap.put(similarity, fileVector);
			
			Set<Double> similarityMapKeySet = this.similarityMap.keySet();
			
			this.keyListSorted	=  new ArrayList<>();
			
			this.keyListSorted.addAll(similarityMapKeySet);
			
			Collections.sort(this.keyListSorted, Collections.reverseOrder());
			
		}
		
				
		this.keyListSorted = this.keyListSorted.stream()
				.filter(z -> z > 0)
				.collect(Collectors.toList());

		
		int j = 0;
		for(Double key : this.keyListSorted){
			
			DocumentVector docVector = this.similarityMap.get(key);
			
			this.writeLn("[" + j + "]" + "(" + key + ")" + " " + docVector.getDirectory());
			
			
			
			j++;
		}
		
	}

	/**
	 * Method which creates vector of document containing user's query words.
	 * @param validWords List containing all query words.
	 * @return Appropriate {@link DocumentVector} instance.
	 */
	private DocumentVector createQueryVector(List<String> validWords) {
		DocumentVector queryVector = new DocumentVector();

		for(String word : validWords){
			queryVector.putWord(word.toLowerCase());
		}
		
		double[] dimensions = new double[this.vocabulary.size()];
		
		
		
		for(int i = 0; i < dimensions.length; i++){
			//Getting word for checking
			String word = vocabulary.get(i);
			
			double tfidf = 0;
			
			int tf = 0;
			
			//if part...
			if(queryVector.containsWord(word)){
				tf = queryVector.numberOfWords(word);
			}
			
			double idf = 0;
			
			//Plus one because of this query vector
			int numerator = this.documentVectors.size() + 1;
			
			int denumerator = 0;
			
			for(Path path : this.documentVectors.keySet()){
				DocumentVector docVectorCheck = this.documentVectors.get(path);
				
				if(docVectorCheck.containsWord(word)){
					denumerator++;
				}
			}
			
			if(queryVector.containsWord(word)){
				denumerator++;
			}
			
			idf = Math.log(((double)numerator) / denumerator);
			
			
			tfidf = tf * idf;
			
			dimensions[i] = tfidf;
		}
		
		queryVector.setDimensions(dimensions);
		
		return queryVector;
		
	}

	/**
	 * Method which implements results command.
	 * @throws IOException
	 */
	private void resultsCommand() throws IOException {
		if(this.similarityMap == null){
			this.writeLn("This command does not have any sense for now");
			return;
		}
		
		for(Double key : this.keyListSorted){
			DocumentVector doc = this.similarityMap.get(key);
			this.writeLn(doc.getDirectory().toString());
		}
	}

	/**
	 * Method which does work of reading user's input.
	 * @return Read String.
	 * @throws IOException
	 */
	private String read() throws IOException {
		return READER.readLine().trim();
	}

	/**
	 * Creates vector representations of documents.
	 */
	private void createVectors() {
		for(Path path : this.documentVectors.keySet()){
			DocumentVector docVector = this.documentVectors.get(path);
			
			double[] vector = new double[this.vocabulary.size()];
			
			for(int i = 0; i < vector.length; i++){
				
				//Getting word for checking
				String word = vocabulary.get(i);
				
				double tfidf = 0;
				
				int tf = 0;
				
				//if part...
				if(docVector.containsWord(word)){
					tf = docVector.numberOfWords(word);
				}
				
				double idf = 0;
				
				int numerator = this.documentVectors.size();
				
				int denumerator = 0;
				
				for(Path pathNew : this.documentVectors.keySet()){
					DocumentVector docVectorCheck = this.documentVectors.get(pathNew);
					
					if(docVectorCheck.containsWord(word)){
						denumerator++;
					}
				}
				
				idf = Math.log(((double)numerator) / denumerator);
				
				
				tfidf = tf * idf;
				
				vector[i] = tfidf;
				
			}
			
			docVector.setDimensions(vector);
						
		}
	}

	/**
	 * Method which returns vocabuary size.
	 * @return
	 */
	public int vocabularySize(){
		return this.vocabulary.size();
	}

	/**
	 * Reads stopping words from file and fills with them {@link #stopWords} list.
	 * @throws IOException
	 */
	private void readStopingWords() throws IOException {
		BufferedReader reader = Files.newBufferedReader(Paths.get(this.STOP_WORDS_FILE), this.CHARSET);

		String curLine = null;
		while(true){
			curLine = reader.readLine();
			
			if(curLine == null) break;
			curLine = curLine.trim();
			
			if(curLine.isEmpty()) break;
		
			if(curLine.contains(".")){
				curLine = curLine.replace(".", "");
			}
			
			if(curLine.contains("-")){
				String[] lineParts = curLine.split("-");
				
				for(String linePart : lineParts){
					this.stopWords.add(linePart.trim());
				}
				
				continue;
			}
			
			this.stopWords.add(curLine);
			
		}
		
	}

	/**
	 * Builds vocabulary.
	 * @throws IOException
	 */
	private void buildVocabulary() throws IOException {
		Files.walkFileTree(this.mainDir, new VocabularyCreator());
	}

	/**
	 * Class which implements {@link FileVisitor} and allows visiting all documents
	 * situated in root directory.
	 * @author Leonardo Kokot
	 * @version 1.0
	 */
	private class VocabularyCreator implements FileVisitor<Path> {
		
		/**
		 * Container for all lines of one file.
		 */
		private List<String> fileLines;
		
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			DocumentVector docVect = new DocumentVector(file);
			Shell.this.documentVectors.put(file, docVect);
			
			this.fileLines = Files.readAllLines(file, CHARSET);
			doParsing(docVect);
			
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}
		
		/**
		 * Does parsing of document and fills vocabulary with extracted words(which are valid and
		 * not stopping ones).
		 * @param docVect Document vector instance representing document which parsing is currently being preformed.
		 */
		private void doParsing(DocumentVector docVect) {
			boolean newSentence = false;
			
			int k = 0;
			
			for(String line : fileLines){
				
				
				if(line == null) continue;
				line = line.trim();
				if(line.isEmpty()) continue;
				
				String[] words = line.split("\\s++");
				
				for(String word : words){
					newSentence = false;
					if(k == 0) {
						newSentence = true;
						k++;
					}
					
					if(word == null) continue;
					word = word.trim();
					if(word.isEmpty()) continue;
					if(word.length() == 1 && !Character.isAlphabetic(word.charAt(0))){
						if(word.charAt(0) == '.'){
							k--;
						}
						continue;
					}
					
					char[] letters = word.toCharArray();
					
					for(int i = 0; i < letters.length; i++){
						if(!Character.isAlphabetic(letters[i]) && !(letters[i] == '-')){
							if(letters[i] == '.'){
								k--;
							}
							letters[i] = ' ';
						}
					}
					
					String newWord = new String(letters);

					newWord = newWord.trim();
					
					if(newWord.isEmpty()) continue;
					
					if(newWord.contains(" ")){
						String[] newWords = newWord.split("\\s++");
						for(String finalWord : newWords){
							if(finalWord == null || finalWord.isEmpty()) continue;
							if(newSentence && !Shell.this.stopWords.contains(finalWord.toLowerCase())){

								if(!Shell.this.vocabulary.contains(finalWord.toLowerCase())){
									Shell.this.vocabulary.add(finalWord.toLowerCase());
								}

								docVect.putWord(finalWord.toLowerCase());
							} else if(!newSentence && !Shell.this.stopWords.contains(finalWord)){
								if(!Shell.this.vocabulary.contains(finalWord)){
									Shell.this.vocabulary.add(finalWord);
								}

								docVect.putWord(finalWord);
							}
						}
					} else {
						if(newWord.contains("-") && newWord.indexOf('-') == 0){
							newWord = newWord.substring(1);
						}
						if(newWord.contains("-") && newWord.indexOf('-') == newWord.length() - 1){
							newWord = newWord.substring(0, newWord.length() - 1);
						}
						if(newSentence && !Shell.this.stopWords.contains(newWord.toLowerCase()) && newWord != null && !newWord.isEmpty()){
							if(!Shell.this.vocabulary.contains(newWord.toLowerCase())){
								Shell.this.vocabulary.add(newWord.toLowerCase());
							}
							
							docVect.putWord(newWord.toLowerCase());
						} else if(!newSentence && !Shell.this.stopWords.contains(newWord) && newWord != null && !newWord.isEmpty()){
							if(!Shell.this.vocabulary.contains(newWord)){
								Shell.this.vocabulary.add(newWord);
							}
							
							docVect.putWord(newWord);
						}
					}
				}
			}
		}
		
	}
}
