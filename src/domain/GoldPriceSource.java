package domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class GoldPriceSource implements DataSource{
	@Override
	public String getName() {
		return "Gold prices (EUR)";
	}

	@Override
	public String getUnit() {
		return "EUR";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<LocalDate, Double> getData() {
		UrlFetcher fetcher = new UrlFetcher(
				"https://www.quandl.com/api/v3/datasets/WGC/GOLD_DAILY_EUR.json?auth_token=eZo5XJoA7oV4kzs4DxCx&start_date=2013-12-29");
		JsonToMapParser parser = new JsonToMapParser(fetcher.getContent());
		Map<String, Object> datasets = (Map)parser.getResult().get("dataset");
		Map<LocalDate, Double> result = new TreeMap<>();
		List<List> data = (List<List>) datasets.get("data");
		
		List<LocalDate> dates = mapLocalDates(data);
		List<Double> values = mapValues(data);
		
		addToResult(result, dates, values);
		
		return result;
	}

	@SuppressWarnings("rawtypes")
	private List<LocalDate> mapLocalDates(List<List> dates) {
		return dates.stream()
				.map(date -> LocalDate.parse(date.get(0).toString()))
				.collect(Collectors.toList());
	}
	
	@SuppressWarnings("rawtypes")
	private List<Double> mapValues(List<List> values) {
		return values.stream()
				.map(value -> Double.parseDouble(value.get(1).toString()))
				.collect(Collectors.toList());
	}

	private void addToResult(Map<LocalDate, Double> result, List<LocalDate> dates, List<Double> values) {
		for (int i = 0; i < dates.size(); i++) {
			result.put(dates.get(i), values.get(i));
		}
	}
	
	public static void main(String[] args) {
		new GoldPriceSource().getData().forEach( (k,v) -> System.out.println(k + " - " + v));
	}
	
}
