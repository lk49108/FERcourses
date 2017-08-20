package hr.fer.zemris.java.cstr;

/**
 * This class offers similar functionality as the old
 * official implementation of the {@link String} class.
 * It represents unmodifiable strings. It is used in working 
 * with strings.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class CString {
	
	/**
	 * Instance variable(reference to char array) of this class which serves as a container for 
	 * chars that a string is contained of.
	 */
	private final char[] value;
	
	/**
	 * Position from which in <code>value</code> this string starts.
	 */
	private final int offset;
	
	/**
	 * Size of a string.
	 */
	private final int count;
	
	/**
	 * This constructor is used for creating new CString instance outside from this class.
	 * This constructor is made for user.
	 * This is a constructor which creates an instance of CString class.
	 * It copies Characters from an array into this string. If
	 * Characters which are to be copied actually do not exist(i.e. we would go out of 
	 * array bounds while copying Characters) string is still created by copying Characters
	 * until we come to the end of an array.
	 * @param data Reference to Character array from which chars 
	 * are copied into string that is being created.
	 * @param offset Starting position from which characters are copied, if less than one, it
	 * is set to 0.
	 * @param length Number of characters which are being copied.
	 * @throws NullPointerException if null pointer is provided.
	 */
	public CString(char[] data, int offset, int length){
		if(data == null){
			throw new NullPointerException();
		}
		if(offset < 0){
			offset = 0;
		}
		char[] tmp;
		if(data.length - offset < length){
			count = data.length - offset;
			tmp = new char[count];
			for(int i = offset; i < data.length; i++){
				tmp[i - offset] = data[i]; 
			}
		} else {
			count = length;
			tmp = new char[count];
			for(int i = offset; i < offset + length; i++){
				tmp[i - offset] = data[i]; 
			}
		}
		this.offset = offset;
		value = tmp;
	}
	
	
	/**
	 * This constructor is used locally, it does not create new Character array for to produce
	 * new CString. It just makes new reference to preexisting Character array.
	 * This is a constructor which creates an instance of CString class.
	 * It copies Characters from an array into this string. If
	 * Characters which are to be copied actually do not exist(i.e. we would go out of 
	 * array bounds while copying Characters) string is still created by copying Characters
	 * until we come to the end of an array.
	 * @param data Reference to Character array from which chars 
	 * are copied into string that is being created.
	 * @param offset Starting position from which characters are copied.
	 * @param length Number of characters which are being copied.
	 * @throws NullPointerException if null pointer is provided.
	 */
	private CString(char[] data, int offset, int length, Object ref){
		if(data == null){
			throw new NullPointerException();
		}
		if(offset < 0){
			offset = 0;
		}
		value = data;
		if(data.length - offset < length){
			count = data.length - offset;
		} else {
			count = length;
		}
		this.offset = offset;
	}
	
	/**
	 * This is a constructor which creates an instance of this 
	 * class (it creates a string) by copying Characters from given array of Characters into internal 
	 * array of character. This constructor acctualy delegates his work to 
	 * another constructor.
	 * @param data Reference to an array from which Characters are being copied.
	 * @throws NullPointerException if null reference is provided.
	 */
	public CString(char[] data){
		this(data, 0, data.length);
	}
	
	public CString(CString original){
		if(original.length() < original.value.length){
			value = new char[original.length()];
			count = original.length();
			offset = 0;
		}
		else{
			value = original.value;
			count = original.length();
			offset = original.offset;
		}
	}
	
	/**
	 * This is a static method which returns new CString
	 * object which has the same character data as given Javas's String object.
	 * @param s String that is being converted to CString and then returned.
	 * @return New instance of CString. 
	 */
	public static CString fromString(String s){
		return new CString(s.toCharArray());
	}
	
	/**
	 * This is an instance method for getting the length of this string. 
	 * @return Length of an array to which value instance variable is reference.
	 */
	public int length(){
		return count;
	}
	
	/**
	 * This method is used for obtaining a character from a specific
	 * index inside of CString.
	 * @param index Index of a Character which is to be returned.
	 * @return Character at the spot in CString specified by index.
	 * @throws IndexOutOfBoundsException if index parameter is 
	 * illegal(i.e. less than 0 or greater or equal than CString length).
	 */
	public char charAt(int index){
		if(index <  0 || index > count - 1){
			throw new IndexOutOfBoundsException();
		}
		return value[index + offset];
	}
	
	/**
	 * This method returns content of this CString
	 * as Character array.
	 * @return Character array representing this CString.
	 */
	public char[] toCharArray(){
		char[] helperArray= new char[count];
		for(int i = 0; i < count; i++){
			helperArray[i] = this.value[i + offset];
		}
		return helperArray;
	}
	
	/**
	 * Method that converts CString to String and returns it.
	 * Both Strings has the same Characters in themselves.
	 */
	public String toString(){
		return new String(this.toCharArray());
	}
	
	/**
	 * This method checks whether given Character is contained in this CString.
	 * If this statement is true, returns index of first occurrence of this Character.
	 * Otherwise, if there is no such Character in this CString, it returns -1.
	 * @param c Character that is checked for being contained in this CString.
	 * @return Integer representing first occurrence of c in this CString or -1 if
	 * such Character does not exist in this CString.
	 */
	public int indexOf(char c){
		for(int i = 0; i < this.length(); i++){
			if(this.toCharArray()[i] == c){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * This method checks whether this CString starts
	 * with another CString. 
	 * @param s CString for which it is being checked if it is contained at the beginning 
	 * of this CString.
	 * @return True if s is contained at the beginning of this CString.
	 * Otherwise, false is returned.
	 */
	public boolean startsWith(CString s){
		for(int i = 0; i < s.length(); i++){
			if(this.toCharArray()[i] == s.toCharArray()[i]){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method checks whether this CString ends
	 * with another CString. 
	 * @param s CString for which it is being checked if it is contained at the end 
	 * of this CString.
	 * @return True if s is contained at the end of this CString.
	 * Otherwise, false is returned.
	 */
	public boolean endsWith(CString s){
		for(int i = 0; i < s.length(); i++){
			if(this.toCharArray()[this.length() - i - 1] == s.toCharArray()[s.length() - i - 1]){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method returns true if this CString contains given CString at any position,
	 * false otherwise.
	 * @param s CString for which it is being checked if it is contained in this CString.
	 * @return True if s is contained in this CString, false otherwise.
	 */
	public boolean contains(CString s){
		for(int i = 0; i < this.length() - s.length(); i++){
			boolean help = true;
			for(int j = 0; j < s.length(); j++){
				if(this.charAt(i + j) != s.charAt(j)){
					help = false;
				}
			}
			if(help){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method returns new CString which 
	 * represents a part of original string; 
	 * position endIndex does not belong to the substring.
	 * @param startIndex Index from which substring starts inside of
	 * this CString.
	 * @param endIndex Index which represent the end of newly created substring.
	 * @return Substring of this CString as an instance of CString class.
	 * @throws IllegalArgumentException if startIndex<0 or endIndex < startIndex.
	 */
	public CString substring(int startIndex, int endIndex){
		if(!(endIndex >= startIndex || startIndex >= 0 || endIndex + this.offset >= value.length)){
			throw new IllegalArgumentException();
		}
		return new CString(this.value, this.offset + startIndex, endIndex - startIndex, null);
	}
	
	/**
	 * This method returns new CString which represents starting part of original string
	 * and is of length n. If n is larger than length of this CString whole string is returned.
	 * @param n Length of CString that is returned.
	 * @return New instance of CString.
	 */
	public CString left(int n){
		if(n < 0){
			throw new IllegalArgumentException();
		}
		return new CString(this.value, this.offset, n, null);
	}
	
	/**
	 * This method returns new CString which represents ending part of original string
	 * and is of length n. If n is larger than length of this CString whole string is returned.
	 * @param n Length of CString that is returned.
	 * @return New instance of CString.
	 */
	public CString right(int n){
		if(n < 0){
			throw new IllegalArgumentException();
		}
		return new CString(this.value, this.offset+this.length() - n, n, null);
	}
	
	/**
	 * This method creates a new CString which is concatenation of current and
	 * given string and returns this newly created CString.
	 * @param s CString which is being concatenated.
	 * @return Concatenation of two CStrings.
	 */
	public CString add(CString s){
		char[] helper = new char[this.length() + s.length()];
		int i = 0;
		for(char sign : this.toCharArray()){
			helper[i] = sign;
			i++;
		}
		for(char sign : s.toCharArray()){
			helper[i] = sign;
			i++;
		}
		return new CString(helper, 0, helper.length, null);
	}
	
	/**
	 * This method creates a new CString in which
	 * each occurrence of old character is replaced with new character.
	 * @param oldChar Replaced character.
	 * @param newChar Replacing character.
	 */
	public CString replaceAll(char oldChar, char newChar){
		char[] container = new char[this.length()];
		int i = 0;
		for(char setter : this.toCharArray()){
			if(setter == oldChar){
				container[i] = newChar;
			}
			else{
				container[i] = setter;
			}
			i++;
		}
		return new CString(container);
	}
	
	
	/**
	 * This method creates a new CString
	 * in which each occurrence of old substring 
	 * is replaced with the new substring.
	 * @param oldStr Replaced substring.
	 * @param newStr Replacing s(ubs)tring.
	 */
	public CString replaceAll(CString oldStr, CString newStr){
		if(oldStr.length() == 0){
			return emptyOldStr(this, newStr);
		}
		char[] container = this.toCharArray();
		for(int i = 0; i < container.length - oldStr.length() + 1; i++){
			boolean isItHere = true; 
			for(int j = 0; j < oldStr.length(); j++){
				if(container[i + j] != oldStr.toCharArray()[j]){
					isItHere = false;
					break;
				}
			}
			if(isItHere){
				char[] newString = new char[container.length - (oldStr.length() - newStr.length())];
				for(int k = 0; k < container.length - (oldStr.length() - newStr.length()); k++){
					if(k < i){
						newString[k] = container[k]; 
					}
					else if(k >= i && k < i + newStr.length()){
						newString[k] = newStr.toCharArray()[k - i];
					}
					else{
						newString[k] = container[k - (newStr.length() - oldStr.length())];
					}
				}
				i += newStr.length() - 1;
				container = newString;
			}
		}
		return new CString(container);
	}
	
	/**
	 * This method is a helper method
	 * for replacing all empty substrings in old string
	 * with a replacing substring.
	 * @param string String in which this replacement is done.
	 * @param newStr String which exchanges empty substring in string.
	 * @return Newly created instance of CString.
	 */
	private CString emptyOldStr(CString string, CString newStr){
		char[] array = new char[string.length() * (1 + newStr.length()) + newStr.length()];
		int j = 0;
		for(int i = 0; i < array.length; i++){
			if(i % (newStr.length() + 1) == newStr.length()){
				array[i] = string.toCharArray()[j++];
			}
			else {
				array[i] = newStr.toCharArray()[i % (newStr.length() + 1)]; 
			}
		}
		return new CString(array);
	}
	
}
