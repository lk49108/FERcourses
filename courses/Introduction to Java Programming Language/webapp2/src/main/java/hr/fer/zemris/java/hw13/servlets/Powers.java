package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Creates excel file containing some Integers
 * and their corresponding values which equals integer to (some other integer).
 * Values which will be shown in excel file are defined through
 * URL parameters. 
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet(name="powersServlet", urlPatterns={"/powers", "/powers.jsp"})
public class Powers extends HttpServlet {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int a = 0;
		int b = 0;
		int n = 0;
		
		try {
			a = Integer.parseInt(req.getParameter("a"));
			b = Integer.parseInt(req.getParameter("b"));
			n = Integer.parseInt(req.getParameter("n"));
			if(!(a >= -100 && a <= 100 && b >= -100 && b <= 100 && n>= 1 && n <= 5)){
				throw new IllegalArgumentException();
			}
		} catch(IllegalArgumentException ex){
			String errorMessage = "Wrong parameters were provided:\n"
					+ " a=" + a + ", b=" + (req.getParameter("b") != null ? req.getParameter("b"): 0)
					+ " n=" + (req.getParameter("n") != null ? req.getParameter("b"): 0);
			req.setAttribute("error", errorMessage);

			req.getRequestDispatcher("/WEB-INF/pages/wrongParameters.jsp").forward(req, resp);
		}
		
		if(a > b){
			int c = a;
			a = b;
			b = c;
		}
		
		try {
			HSSFWorkbook hwb = new HSSFWorkbook();
			for(int i = 0; i < n; i++){
				HSSFSheet sheet =  hwb.createSheet((i + 1) + "-th power");

				HSSFRow rowhead=   sheet.createRow(0);

				rowhead.createCell(0).setCellValue("Integer(k)");
				
				rowhead.createCell(1).setCellValue("k^" + (i + 1));

				for(int j = a; j <= b; j++){
					HSSFRow row=   sheet.createRow(j - a + 1);
					
					row.createCell(0).setCellValue(j);
					
					row.createCell(1).setCellValue(Math.pow(j, i + 1));
				}
			}
			
			resp.setContentType("application/vnd.ms-excel");
			
			OutputStream out = resp.getOutputStream();
			
			hwb.write(out);
						
			hwb.close();
		} catch(Exception unhandled){}
	}
}
