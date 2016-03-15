package compare;

import domain.DataSource;
import domain.FootballGoalsSource;
import domain.TemperatureSource;

public class GoalsTemperatureComparison {

	public DataSource goals;
	public DataSource temperatures;
	public DataCollectionBuilder builder;
	public DataCollection result;
	
	public GoalsTemperatureComparison() {
		goals = new FootballGoalsSource();
		temperatures = new TemperatureSource();
		builder = new DataCollectionBuilder(goals, temperatures, Resolution.DAY);
		result = builder.getResult();
	}
	
	public GoalsTemperatureComparison(Resolution res) {
		goals = new FootballGoalsSource();
		temperatures = new TemperatureSource();
		builder = new DataCollectionBuilder(goals, temperatures, res);
		result = builder.getResult();
	}
	
	public GoalsTemperatureComparison(DataCollectionBuilder dcb) {
		builder = dcb;
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