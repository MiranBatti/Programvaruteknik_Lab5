package domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class SpectatorsSource implements DataSource {
	@Override
	public String getName() {
		return "Spectators";
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
		
		addToResult(events, result, data);
		
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	private List<Map> filterSpectators(List<Map> events) {
		return  events.stream()
				.filter(event -> (((Map) event.get("facts")) != null))
				.filter(event -> (((Map)event.get("facts")).get("spectators")) != null)
				.collect(Collectors.toList());
	}
	
	@SuppressWarnings("rawtypes")
	private void addToResult(List<Map> events, Map<LocalDate, Double> result, Map<String, Object> data) {
		for (Map event : events) {
			LocalDate date = LocalDate.parse(event.get("startDate").toString().substring(0, 10));
			Map facts = (Map) event.get("facts");
			Map arena = (Map) ((Map)event.get("facts")).get("arena");
			
			String arenaName = null;
			Double spectators = null;
			
			if(arena != null) {
				arenaName = (String)arena.get("name");
				if(arenaName.equals("Strömvallen")) {
					spectators = Double.parseDouble(facts.get("spectators").toString());
					result.put(date, spectators);						
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new SpectatorsSource().getData().forEach((dates, spectators) -> System.out.println(dates + " - " + spectators));
	}
	
}
