package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import compare.DataSourceComparator;
import domain.DataSource;
import errorhandler.ArrayIsEmptyExcpetion;
import factory.DataSourceFactory;

/**
 * Servlet implementation class ServletTest
 */
@WebServlet("/ServletTest")
public class ServletTest extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	public DataSourceComparator gt;
	public DataSourceFactory factory;
	public List<String> availableSources = new ArrayList<String>();
	
    /**
     * Default constructor. 
     */
    public ServletTest() {
    	factory = new DataSourceFactory();
    	createAvailableSources();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		response.setContentType("application/json;charset=UTF-8");
        Boolean pretty = Boolean.valueOf(request.getParameter("pretty"));
        JsonFormatter format = new JsonFormatter();
        String datasource1 = request.getParameter("datasource1");
        String datasource2 = request.getParameter("datasource2"); 
        String sourcesString[] = getSources(datasource1, datasource2);
        
        try {
            DataSource[] datasources = factory.getSource(sourcesString[0], sourcesString[1]);
            gt = new DataSourceComparator(datasources[0], datasources[1]);
		} catch (ArrayIsEmptyExcpetion e) {
			response.getWriter().print(e.getMessage());
			response.getWriter().close();
		}
        
        response.getWriter().append((pretty ? format.format(gt.getData()) : gt.getData()));
	}

	private void createAvailableSources() {
        availableSources.add("goals");
        availableSources.add("temperature");
        availableSources.add("gold");
        availableSources.add("spectators");
        availableSources.add("static");
	}
	
	private String[] getSources(String datasource1, String datasource2) throws IllegalArgumentException{		
		String[] sources = new String[2];
		for (int i = 0; i < availableSources.size(); i++) {
			if(availableSources.get(i).equals(datasource1)) {
	        	sources[0] = availableSources.get(i);
	        } 
			if(availableSources.get(i).equals(datasource2)) {
	        	sources[1] = availableSources.get(i);
	        }
		}
		if(sources.equals(null))
			throw new IllegalArgumentException();
		return sources;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
