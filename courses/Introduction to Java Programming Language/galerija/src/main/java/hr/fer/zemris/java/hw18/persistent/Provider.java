/**
 * 
 */
package hr.fer.zemris.java.hw18.persistent;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class holds static public methods which are used for
 * obtaining informations about every picture taking part of web-page.
 * It allows manipulation with thumbnails, allows tag-owner fetching etc.  
 * @author Leonardo Kokot
 * @version 1.0
 */
public class Provider {

	/**
	 * Variable holding informations if this class content was initialized.
	 */
	public static boolean initialized = false;
	
	/**
	 * List of all {@link Image} instances taking part in web-app.
	 */
	public static List<Image> list = new ArrayList<>();
	
	/**
	 * Returns picture description determined by provided picture's path.
	 * @param path Picture's path.
	 * @return Appropriate picture description.
	 */
	public static String getDescription(String path){
		String desc = "";
		for(Image i : list){
			if(i.path.equals(path)){
				desc += i.description + "#" + i.getTagsAsString(); 
				break;
			}
		}
		
		return desc;
	}
	
	/**
	 * Checks if specific picture was thumbnailed yet, picture
	 * is represented by corresponding picture path.
	 * @param path Picture's path.
	 * @return True if picture was thumbnailed yet, false is returned otherwise.
	 */
	public static boolean isThumbnailed(String path) {
		for(Image i : list){
			if(i.path.equals(path)){
				return i.thumbnailed;
			}
		}
		return false;
	}
	
	/**
	 * Returns list of picture paths which is dependent on provided tag.
	 * If picture contains provided tag, that same picture's path is 
	 * added to list, otherwise, if picture does not contain provided tag, that same's
	 * picture path is not included in path list.
	 * @param tag Tag based on which path list is created and returned.
	 * @return List of picture paths.
	 */
	public static List<String> getTagOwners(String tag){

		List<String> l = new ArrayList<>();
		for(Image im : list){
			if(im.containsTag(tag)){

				l.add(im.path);
			}
		}
		return l;
	}

	/**
	 * Checks if this class content has been initialized yet.
	 * @return True if content is initilized, false is returned otherwise.
	 */
	public static boolean isInitialized(){
		return initialized;
	}
	
	/**
	 * This method should be called as first method from this class, to initialize
	 * this class content.
	 * @param string String representation of path to main .txt file.
	 */
	public static void initialize(String string) {
		initialized = true;
		String name = null;
		String description = null;
		Path path = Paths.get(string);

		try(BufferedReader reader = Files.newBufferedReader(path)){
			String line = null;
			int i = 0;
			while(true){
				i++;

				line = reader.readLine();

				if(line == null || line.isEmpty()) break;

				if(i % 3 == 1){
					name = line.trim();
				} else if(i % 3 == 2){
					description = line.trim();
				} else {
					String[] parts = line.split(",");

					Provider.list.add(new Image(name, description, parts));
				}

				
			} 
			
			
			
		} catch(IOException ex){}		
	}
	
	/**
	 * Sets picture's(which is being contained in list of pictures)
	 * thumbnailed property to true. This method should be called right after 
	 * picture was thumbnailed. 
	 * @param parameter Path of the picture which property thumbnailed is being set to true.
	 */
	public static void setThumbnailed(String parameter) {
		for(Image i : list){
			if(i.path.equals(parameter)){
				i.thumbnailed = true;
			}
		}
	}
	
	/**
	 * Class which is used as part of {@link Provider} class content.
	 * Each instance of this class represents one picture being used as part of 
	 * web-application.
	 * @author Leonardo Kokot
	 * @version 1.0
	 */
	private static class Image{
		
		/**
		 * Holds information whether picture it represents was thumbnailed yet.
		 */
		private boolean thumbnailed = false;
		
		/**
		 * Path representation of picture.
		 */
		String path;
		
		/**
		 * Picture's description.
		 */
		String description;
		
		/**
		 * Picture's tags.
		 */
		List<String> tags;
		
		
		/**
		 * Constructor.
		 * @param path Picture's path.
		 * @param description Picture's description.
		 * @param tags Picture's tags.
		 */
		public Image(String path, String description, String[] tags) {
			super();
			this.path = path;
			this.description = description;
			this.tags = new ArrayList<>();
			for(int i = 0; i < tags.length; i++){
				this.tags.add(tags[i].trim());
			}
		}
		
		/**
		 * Checks if this instance of this class, i.e. picture it represents contains
		 * provided tag.
		 * @param tag Tag that is checked for being contained by this picture.
		 * @return True if  statement is satisfied, false otherwise.
		 */
		private boolean containsTag(String tag){
			return this.tags.contains(tag);
		}

		/**
		 * @return the path
		 */
		@SuppressWarnings("unused")
		public String getPath() {
			return path;
		}

		/**
		 * @param path the path to set
		 */
		@SuppressWarnings("unused")
		public void setPath(String path) {
			this.path = path;
		}

		/**
		 * @return the description
		 */
		@SuppressWarnings("unused")
		public String getDescription() {
			return description;
		}

		/**
		 * @param description the description to set
		 */
		@SuppressWarnings("unused")
		public void setDescription(String description) {
			this.description = description;
		}

		/**
		 * @return the tags
		 */
		@SuppressWarnings("unused")
		public List<String> getTags() {
			return tags;
		}

		/**
		 * @param tags the tags to set
		 */
		@SuppressWarnings("unused")
		public void setTags(List<String> tags) {
			this.tags = tags;
		}
		
		/**
		 * Returns this picture's tags String representation.
		 * @return New String instance.
		 */
		public String getTagsAsString(){
			String s = "";
			boolean first = true;
			for(String tag : tags){
				if(first){
					s += tag;
					first = false;
				} else {
					s += ", " + tag;
				}
			}
			return s;
		}
		
	}

	

	
}
