package compare;

import domain.DataSource;
import domain.SpectatorsSource;
import domain.StaticJSONSource;

public class DataSourceComparator {

	public DataSource source1;
	public DataSource source2;
	public DataCollectionBuilder builder;
	public DataCollection result;
	
	public DataSourceComparator(DataSource source1, DataSource source2) {
		this.source1 = source1;
		this.source2 = source2;
		builder = new DataCollectionBuilder(source1, source2, Resolution.DAY);
		result = builder.getResult();
	}
	
	public DataSourceComparator(DataSource source1, DataSource source2, Resolution res) {
		this.source1 = source1;
		this.source2 = source2;
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
					"\"" + source1.getName() + "\":" + match.getXValue() + "," +
					"\"" + source2.getName() + "\":" + match.getYValue() + " },");
		});
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]}");
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		SpectatorsSource a = new SpectatorsSource();
		StaticJSONSource c = new StaticJSONSource();
		
		DataSourceComparator comp = new DataSourceComparator(c, a);
		
		System.out.println(comp.getData());
	}
	
}
