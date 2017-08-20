/**
 * 
 */
package hr.fer.zemris.java.tecaj_13.forms;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;



/**
 * Blog entry form.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class BlogEntryForm extends AbstractForm {

	/**
	 * 
	 */
	private String id;
			
	/**
	 * Title.
	 */
	private String title;
	
	/**
	 * Text.
	 */
	private String text;
	
	/**
	 * Constructor.
	 */
	public BlogEntryForm() {
		this.id="";
		this.title="";
		this.text="";
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void fillFromHttpRequest(HttpServletRequest req){
		this.id=prepare(req.getParameter("id"));
		this.title=prepare(req.getParameter("title"));
		this.text=prepare(req.getParameter("text"));
	}
	
	
	@Override
	public void validate(){
		mistakes.clear();
				
		if(this.text.isEmpty()){
			mistakes.put("text", "At least some text must be written.");
		}
		if(this.title.isEmpty()){
			mistakes.put("title", "Title is mandatory");
		}
	}
	
	/**
	 * Fills entry from this form.
	 * @param creator Creator of form.
	 * @return BlogEntry instance.
	 */
	public BlogEntry fillEntry(BlogUser creator) {
		if(id.isEmpty()){
			return DAOProvider.getDAO().createBlogEntry(this, creator);
		}
		return DAOProvider.getDAO().updateBlogEntry(this);
	}
	
	/**
	 * Fills form with existing content.
	 * @param id Id of blog entry.
	 */
	public void fillExistingEntryAsForm(Long id){
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
		this.title = entry.getTitle();
		this.text = entry.getText();
		this.id = id.toString();
	}
}
