package factory;

import java.util.HashMap;
import java.util.Map;

import domain.DataSource;
import domain.FootballGoalsSource;
import domain.GoldPriceSource;
import domain.SpectatorsSource;
import domain.StaticJSONSource;
import domain.TemperatureSource;
import errorhandler.ArrayIsEmptyExcpetion;

public class DataSourceFactory {
	private Map<String, DataSource> datasources;
	private DataSource[] list;
	
	public DataSourceFactory() {
		datasources = new HashMap<String, DataSource>();
		mapSources();
		list = new DataSource[2];
	}

	
	public DataSource[] getSource(String datasource1, String datasource2) throws ArrayIsEmptyExcpetion{
		
		list[0] = datasources.get(datasource1);
		list[1] = datasources.get(datasource2);
		
		if(list[0] == null || list[1] == null) 
			throw new ArrayIsEmptyExcpetion("{error:\"Datasource does not exist\"}");
		
		return list;
	}
	
	private void mapSources() {
		datasources.put("goals", new FootballGoalsSource());
		datasources.put("temperature", new TemperatureSource());
		datasources.put("gold", new GoldPriceSource());
		datasources.put("spectators", new SpectatorsSource());
		datasources.put("static", new StaticJSONSource());
	}
	
}
