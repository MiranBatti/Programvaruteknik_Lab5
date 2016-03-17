package compare;

import domain.DataSource;
import domain.FootballGoalsSource;
import domain.TemperatureSource;

public class DataSourceComparator {

	public DataSource goals;
	public DataSource temperatures;
	public DataCollectionBuilder builder;
	public DataCollection result;
	
	public DataSourceComparator() {
		goals = new FootballGoalsSource();
		temperatures = new TemperatureSource();
		builder = new DataCollectionBuilder(goals, temperatures, Resolution.DAY);
		result = builder.getResult();
	}
	
	public DataSourceComparator(Resolution res) {
		goals = new FootballGoalsSource();
		temperatures = new TemperatureSource();
		builder = new DataCollectionBuilder(goals, temperatures, res);
		result = builder.getResult();
	}
	
	public DataSourceComparator(DataSource source1, DataSource source2) {
		builder = new DataCollectionBuilder(source1, source2, Resolution.DAY);
		result = builder.getResult();
	}
	
	public DataSourceComparator(DataSource source1, DataSource source2, Resolution res) {
		builder = new DataCollectionBuilder(source1, source2, res);
		result = builder.getResult();
	}
	
	public String getData() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("{");
		sb.append("\"Data\": [");
		
		result.getData().forEach((date, match) -> {
			sb.append(
		            "{\"Date\":" + "\"" + date + "\"" + "," + 
					"\"Goals\":" + match.getXValue() + "," +
					"\"Temperature\":" + match.getYValue() + " },");
		});
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]}");
		
		return sb.toString();
	}
}
