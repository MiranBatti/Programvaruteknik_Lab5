package compare;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import domain.DataSource;

public class DataCollectionBuilder {

	private DataSource xData;
	private DataSource yData;
	@SuppressWarnings("unused")
	private Resolution resolution;
	private Map<String, List<MatchedDataPair>> resultData;
	private Map<String, MatchedDataPair> finalResult;
	private String title;

	public DataCollectionBuilder(DataSource xData, DataSource yData, Resolution resolution) {
		this.xData = xData;
		this.yData = yData;
		this.resolution = resolution;
		finalResult = new HashMap<>();
		resultData = new HashMap<>();
	}

	public DataCollectionBuilder(DataSource xData, DataSource yData, Resolution resolution, String title) {
		this(xData, yData, resolution);
		this.title = title;
	}

	public String getTitle() {
		return (title != null) ? title : xData.getName() + " / " + yData.getName();
	}
	
	public DataCollection getResult() {
		addMatchesToResult();	
		createFinalFromResult();
		return new DataCollection(getTitle(), xData.getUnit(), yData.getUnit(), finalResult);
	}

	private void addMatchesToResult() {
		Map<LocalDate, Double> xMap = xData.getData();
		Map<LocalDate, Double> yMap = yData.getData();
		@SuppressWarnings("unused")
		int i = 0;
		for (Entry<LocalDate, Double> xSize : xMap.entrySet()) {
			for (Entry<LocalDate, Double> ySize : yMap.entrySet()) {
				if(xSize.getKey().equals(ySize.getKey())) {
					addToResultData(xSize.getKey().toString(), new MatchedDataPair(xSize.getValue(), ySize.getValue()));
				}
			}
		}
		
//		xData.getData().forEach( (xKey, xData) -> {
//			yData.getData().forEach( (yKey, yData) -> {
//				if(resolution.areSame(xKey, yKey)) {
//					addToResultData(resolution.getKey(xKey), new MatchedDataPair(xData, yData));
//				}
//			});
//		});
	}

	private void addToResultData(String key, MatchedDataPair match) {
		if(resultData.containsKey(key)) {
			List<MatchedDataPair> existing = resultData.get(key);
			existing.add(match);
		} else {
			List<MatchedDataPair> matches = new ArrayList<>();
			matches.add(match);
			resultData.put(key, matches);
		}
	}

	private void createFinalFromResult() {
		resultData.forEach((key, list) -> {
			
			Double sumX = 0.0;
			Double sumY = 0.0;
			
			for (int i = 0; i < list.size(); i++) {
				sumX += list.get(i).getXValue();
				sumY += list.get(i).getYValue();
			}
			
			Double averageX = sumX / list.size();
			Double averageY = sumY / list.size();
			finalResult.put(key, new MatchedDataPair(roundTwoDecimals(averageX), roundTwoDecimals(averageY)));
			
		});
	}
	private double roundTwoDecimals(double d){
		return ((double) Math.round(d * 100) / 100);
	}


}
