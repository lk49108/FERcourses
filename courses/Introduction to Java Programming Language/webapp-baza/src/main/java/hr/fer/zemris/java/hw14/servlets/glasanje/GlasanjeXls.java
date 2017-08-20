/**
 * 
 */
package hr.fer.zemris.java.hw14.servlets.glasanje;

import java.io.IOException;
import java.io.OutputStream;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw14.dao.DAOPollOptionProvider;
import hr.fer.zemris.java.hw14.model.PollOptionModel;

/**
 * Creates .xls file containing informations about
 * band-voting results.
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet(name = "glasanjeXlsServlet", urlPatterns = {"/glasanje-xls"})
public class GlasanjeXls extends HttpServlet {

	/**
	 * Sheet title.
	 */
	private static final String SHEET_TITLE = "Band-voting results";
	
	/**
	 * First column descriptor.
	 */
	private static final String FIRST_COLUMN = "Band";
	
	/**
	 * Second column descriptor.
	 */
	private static final String SECOND_COLUMN = "Votes";
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		OutputStream out = null;
		HSSFWorkbook hwb = null;
			
			
			try  {
				long pollID = Long.parseLong(req.getParameter("pollID"));
				
				hwb = new HSSFWorkbook();
				
				HSSFSheet sheet =  hwb.createSheet(SHEET_TITLE);
				
				HSSFRow rowhead = sheet.createRow(0);

				rowhead.createCell(0).setCellValue(FIRST_COLUMN);
				
				rowhead.createCell(1).setCellValue(SECOND_COLUMN);
				
				List<PollOptionModel> results = DAOPollOptionProvider.getDao().getBasicInputList();
				
				List<PollOptionModel> rightResults = results.stream()
						.filter(t -> t.getPollID() == pollID)
						.collect(Collectors.toList());
				
				Collections.sort(rightResults);
				
				if(rightResults.isEmpty()){
					throw new IllegalArgumentException();
				}
				
				for(int i = 0; i < rightResults.size(); i++){

					HSSFRow row = sheet.createRow(i);

					row.createCell(0).setCellValue(rightResults.get(i).getOptionTitle());
					
					row.createCell(1).setCellValue(rightResults.get(i).getVotesCount());
				}
				
				resp.setContentType("application/vnd.ms-excel");
				
				out = resp.getOutputStream();
				
				hwb.write(out);
							
			} catch(IllegalArgumentException | NullPointerException ex) {
				req.setAttribute("error", "Wrong parameter pollID, or there was no pollID provided.");
				req.getRequestDispatcher("/WEB-INF/pages/wrongParameters.jsp").forward(req, resp);
			} finally {
				try { hwb.close(); out.close(); } catch (Exception ignorable){}
			}

			
		
	}
}
