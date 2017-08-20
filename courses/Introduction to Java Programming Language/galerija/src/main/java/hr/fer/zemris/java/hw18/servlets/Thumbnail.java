/**
 * 
 */
package hr.fer.zemris.java.hw18.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw18.persistent.Provider;
import net.coobird.thumbnailator.Thumbnails;

/**
 * Provides thumbnailed pictures to .jsp file. If thumbnailed picture which
 * has to be returned does not exist, it is firstly thumbnailed and then fetched from
 * folder in which it is contained and then sent to .jsp file.
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet(urlPatterns={"/servlets/thumbnails"})
public class Thumbnail extends HttpServlet{

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String pathFile = req.getServletContext().getRealPath(
				"/WEB-INF/slike/" + req.getParameter("tag")
				);
		
		Path path = Paths.get(pathFile);
		Path dest = Paths.get(req.getServletContext().getRealPath(
				"/WEB-INF/thumbnails/" + req.getParameter("tag")
				));
		
		if(!Provider.isThumbnailed(req.getParameter("tag"))){
			createThumbnail(path.toFile(), dest.toFile());
			Provider.setThumbnailed(req.getParameter("tag"));
		}
				
			BufferedImage image = ImageIO.read(dest.toFile());
			
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			byte[] bytes = baos.toByteArray();		
			resp.setContentType("image/jpg");
			resp.getOutputStream().write(bytes);


		
	}

	/**
	 * Creates thumbnailed picture.
	 * @param src Picture which is being thumbnailed.
	 * @param fullPath Thumbnailed picture location.
	 * @throws IOException
	 */
	private void createThumbnail(File src, File fullPath) throws IOException {
		Thumbnails.of(src)
		.size(150, 150)
		.toFile(fullPath);
	}
}
