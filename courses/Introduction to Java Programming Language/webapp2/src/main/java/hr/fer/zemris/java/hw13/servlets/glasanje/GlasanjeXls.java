/**
 * 
 */
package hr.fer.zemris.java.hw13.servlets.glasanje;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw13.Util.FileUtil;

/**
 * Creates .xls file containing informations about
 * band-voting results.
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet(name = "glasanjeXlsServlet", urlPatterns = {"/glasanje-xls", "/glasanje-xls.jsp"})
public class GlasanjeXls extends HttpServlet {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		
		String fileNameBand = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		
		Path file = Paths.get(fileName);
		
		Path fileBand = Paths.get(fileNameBand);
		
		if(!Files.exists(file)){
			FileUtil.createVoteResultsFile(file);
		}
		
		List<String> results = FileUtil.readFileRows(file);
		
		List<String> bands = FileUtil.readFileRows(fileBand);
		
		try {
			HSSFWorkbook hwb = new HSSFWorkbook();
			
			HSSFSheet sheet =  hwb.createSheet("Band-voting results");
			
			HSSFRow rowhead = sheet.createRow(0);

			rowhead.createCell(0).setCellValue("Band");
			
			rowhead.createCell(1).setCellValue("Votes");

			for(int i = 0; i < FileUtil.NUM_OF_BANDS; i++){

				HSSFRow row = sheet.createRow(i);

				row.createCell(0).setCellValue(bands.get(i).split("\t")[1]);
				
				row.createCell(1).setCellValue(results.get(i).split("\t")[1]);
			}
			
			resp.setContentType("application/vnd.ms-excel");
			
			OutputStream out = resp.getOutputStream();
			
			hwb.write(out);
						
			hwb.close();
		} catch(Exception unhandled){}
		
	}
}
