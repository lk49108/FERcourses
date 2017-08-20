package hr.fer.zemris.java.hw14.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.sql.DataSource;

import hr.fer.zemris.java.hw14.sql.SQLConnectionProvider;

/**
 * Filters all of clients request toward servlets which use database and
 * sets thread dependent connection to SQLConnectionProvider class(this connection is
 * then used in real servlets). When servlet is done with processing request it
 * removes connection(which was before set).
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebFilter(filterName="filter",urlPatterns={"/index.html", "/glasanje", "/glasanje-glasaj", "/glasanje-rezultati", "/glasanje-xls", "/glasanje-grafika"})
public class PollConnectionFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {	
		DataSource ds = (DataSource)request.getServletContext().getAttribute("dbpool");
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			throw new IOException("Baza podataka nije dostupna.", e);
		}
		SQLConnectionProvider.setConnection(con);
		try {
			chain.doFilter(request, response);
		} finally {
			SQLConnectionProvider.setConnection(null);
			try { con.close(); } catch(SQLException ignorable) {}
		}
	}

	@Override
	public void destroy() {		
	}

	
}
