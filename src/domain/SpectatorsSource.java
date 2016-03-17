package domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class SpectatorsSource implements DataSource {
	@Override
	public String getName() {
		return "Publik";
	}

	@Override
	public String getUnit() {
		return "Antal åskådare";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<LocalDate, Double> getData() {
		UrlFetcher fetcher = new UrlFetcher(
				"http://api.everysport.com/v1/events?apikey=1769e0fdbeabd60f479b1dcaff03bf5c&league=63925&limit=240");
		JsonToMapParser parser = new JsonToMapParser(fetcher.getContent());
		Map<String, Object> data = parser.getResult();
		Map<LocalDate, Double> result = new TreeMap<>();
		List<Map> events = filterSpectators((List<Map>) data.get("events"));
		
		for (Map event : events) {
			LocalDate date = LocalDate.parse(event.get("startDate").toString().substring(0, 10));
			int spectators = Integer.parseInt(event.get("spectators").toString());
			addGoalsToDate(result, date, spectators);
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	private List<Map> filterSpectators(List<Map> events) {
		return  events.stream()
				.filter(event -> (((Map) event.get("facts")) != null))
				.filter(event -> (((Map)event.get("facts")).get("spectators")) != null)
				.collect(Collectors.toList());

//		return events.stream()
//				.filter(event -> ((Map) ((Map) event.get("facts")).get("spectators") != null))
//				.collect(Collectors.toList());
	}

	private void addGoalsToDate(Map<LocalDate, Double> result, LocalDate date, int goals) {
		if (!result.containsKey(date)) {
			result.put(date, new Double(goals));
		} else {
			result.put(date, result.get(date) + goals);
		}
	}
	
	public void test() {
		UrlFetcher fetcher = new UrlFetcher(
				"http://api.everysport.com/v1/events?apikey=1769e0fdbeabd60f479b1dcaff03bf5c&league=63925&limit=240");
		JsonToMapParser parser = new JsonToMapParser(fetcher.getContent());
		Map<String, Object> data = parser.getResult();
		Map<LocalDate, Double> result = new TreeMap<>();
		List<Map> events = filterSpectators((List<Map>) data.get("events"));
		
		for (Map event : events) {
			LocalDate date = LocalDate.parse(event.get("startDate").toString().substring(0, 10));
			Map facts = (Map) event.get("facts");
			Long spectators = (Long) facts.get("spectators");
			System.out.println(date.toString() + " - " + spectators);
		}
		
	}
	
	public static void main(String[] args) {
		new SpectatorsSource().test();
	}
	
}
