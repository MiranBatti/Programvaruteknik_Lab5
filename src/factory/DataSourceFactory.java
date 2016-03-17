package factory;

import java.util.HashMap;
import java.util.Map;

import domain.DataSource;
import domain.FootballGoalsSource;
import domain.TemperatureSource;
import errorhandler.ArrayIsEmptyExcpetion;

public class DataSourceFactory {
	private FootballGoalsSource goals;
	private TemperatureSource temperature;
	private Map<String, DataSource> datasources;
	private DataSource[] list;
	
	public DataSourceFactory() {
		goals = new FootballGoalsSource();
		temperature = new TemperatureSource();
		datasources = new HashMap<String, DataSource>();
		datasources.put("goals", goals);
		datasources.put("temperature", temperature);
		list = new DataSource[2];
	}

	
	public DataSource[] getSource(String datasource1, String datasource2) throws ArrayIsEmptyExcpetion{
		
		list[0] = datasources.get(datasource1);
		list[1] = datasources.get(datasource2);
		
		if(list[0] == null || list[1] == null) 
			throw new ArrayIsEmptyExcpetion("Missing datasource");
		
		return list;
	}
	
}
