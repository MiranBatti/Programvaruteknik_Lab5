package compare;


import java.util.Map;

public class DataCollection {
	
	private Map<String, MatchedDataPair> data;
	private String title;
	private String xUnit;
	private String yUnit;
	
	public DataCollection(String title, String xUnit, String yUnit, Map<String, MatchedDataPair> data) {
		this.title = title;
		this.xUnit = xUnit;
		this.yUnit = yUnit;
		this.data = data;
	}

	public Map<String, MatchedDataPair> getData() {
		return data;
	}

	public String getTitle() {
		return title;
	}

	public String getXUnit() {
		return xUnit;
	}

	public String getYUnit() {
		return yUnit;
	}
	
	@Override
	public String toString() {
		return "Title: " + title + ", xUnit: " + xUnit + ", yUnit: " + yUnit;
	}
	@Override
	public boolean equals(Object o){
		DataCollection other = ((DataCollection)o);
		return (getTitle().equals(other.getTitle()) && xUnit.equals(other.getXUnit()) 
				&& yUnit.equals(other.getYUnit()) && data.equals(other.getData()));
	}
	

}
