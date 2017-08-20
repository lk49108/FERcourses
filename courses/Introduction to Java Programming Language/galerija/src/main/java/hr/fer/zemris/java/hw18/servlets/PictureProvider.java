/**
 * 
 */
package hr.fer.zemris.java.hw18.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet is used as a picture src for all pictures being 
 * rendered on .jsp page(except thumbnail ones).
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet(urlPatterns={"/servlets/pictureProvider"})
public class PictureProvider extends HttpServlet {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathFile = req.getServletContext().getRealPath(
				"/WEB-INF/slike/" + req.getParameter("path")
				);	
		
		Path path = Paths.get(pathFile);


		BufferedImage image = ImageIO.read(path.toFile());

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", baos);
		byte[] bytes = baos.toByteArray();		
		resp.setContentType("image/jpg");
		resp.getOutputStream().write(bytes);
		resp.getOutputStream().flush();
	
	}
}
