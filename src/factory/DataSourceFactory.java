package factory;

import compare.DataCollectionBuilder;
import compare.Resolution;
import domain.DataSource;
import domain.FootballGoalsSource;
import domain.TemperatureSource;

public class DataSourceFactory {
	private FootballGoalsSource football;
	private TemperatureSource temperature;
	private Resolution resolution;
	
	public DataSourceFactory() {
		football = new FootballGoalsSource();
		temperature = new TemperatureSource();
		resolution = Resolution.DAY;
	}
	
	public DataSourceFactory(Resolution res) {
		football = new FootballGoalsSource();
		temperature = new TemperatureSource();
		resolution = res;
	}
	
	public DataCollectionBuilder getSource(String datasource1, String datasource2) {
		DataSource firstSource = null, secondSource = null;
		
		if(datasource1.equals("football") || datasource2.equals("football")) {
			firstSource = football;
		} 
		
		if(datasource1.equals("temperature") || datasource2.equals("temperature")) {
			secondSource = temperature;
		}
		
		return new DataCollectionBuilder(firstSource, secondSource, resolution);
	}
	
}
