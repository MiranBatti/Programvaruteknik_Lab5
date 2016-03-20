package domain;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class StaticJSONSource implements DataSource{
	
	private String jsonFilePath;
	
	@Override
	public String getName() {
		return "Static source value";
	}

	@Override
	public String getUnit() {
		return "Value";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<LocalDate, Double> getData() {
		URL url = getClass().getClassLoader().getResource("static.json");
		File f = new File(url.getPath());
		jsonFilePath = f.toString();
		String jsonFileContent = readFile();
		JsonToMapParser parser = new JsonToMapParser(jsonFileContent);
		
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
	
	private String readFile() {
		Charset encoding = Charset.forName("UTF-8"); 		
		String a = "";
		
		byte[] encoded;
		try {
			encoded = Files.readAllBytes(Paths.get(jsonFilePath));
			a = new String(encoded, encoding);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return a;
	}
	
	public static void main(String[] args) {
		new StaticJSONSource().getData().forEach( (k,v) -> System.out.println(k + " - " + v));
	}
	
}
