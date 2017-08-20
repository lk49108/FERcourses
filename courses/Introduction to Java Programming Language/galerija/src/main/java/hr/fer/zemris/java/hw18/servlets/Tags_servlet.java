/**
 * 
 */
package hr.fer.zemris.java.hw18.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides all tag names being used by this web-application pictures.
 * Writes tag-names String to resp.getWriter().write(); method. This String is used as part
 * of AJAX response on .jsp page.
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet(urlPatterns={"/servlets/tags"})
public class Tags_servlet extends HttpServlet {

	/**
	 * serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathFile = req.getServletContext().getRealPath(
				"/WEB-INF/opisnik.txt"
				);

		Path path = Paths.get(pathFile);

		Set<String> set = new TreeSet<>();
		
		try(BufferedReader reader = Files.newBufferedReader(path)){
			String line = null;
			int i = 0;
			while(true){

				line = reader.readLine();
				if(line == null || line.isEmpty()) break;
				i++;
				if(i % 3 == 0){
					String[] parts = line.split(",");
					for(String part : parts){
						set.add(part.trim());

					}
				}

				
			} 
						
			String response = "";

			boolean first = true;
			for(String tag : set){
				if(first){
					first = false;
					response = response + tag;
				} else {
					response = response + "#" + tag;
				}
			}
			
			resp.setContentType("text/plain;charset=UTF-8"); 
			
			resp.getWriter().write(response);
			
		} catch(IOException ex){}
		
		resp.getWriter().flush();

	}
	
}
