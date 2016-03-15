package servlet;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import compare.GoalsTemperatureComparison;
import domain.DataSource;
import factory.DataSourceFactory;

/**
 * Servlet implementation class ServletTest
 */
@WebServlet("/ServletTest")
public class ServletTest extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	public GoalsTemperatureComparison gt;
	public DataSourceFactory factory;
	
	  /**
     * Default constructor. 
     */
	public ServletTest() {
		
		factory = new DataSourceFactory();
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
        
        String firstSource = "", secondSource = "";
        
        if("football".equals(datasource1)) {
        	firstSource = "football";
        	response.getWriter().append("*******\n");
        } 
        
        if("temperature".equals(datasource2)) {
        	secondSource = "temperature";
        	response.getWriter().append("Temp\n");
        }
        
        gt = new GoalsTemperatureComparison(factory.getSource(firstSource, secondSource));
        
        response.getWriter().append((pretty ? format.format(gt.getData()) : gt.getData()));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
