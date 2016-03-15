package domain;


import com.owlike.genson.Genson;
import java.util.Map;


/**
 *
 * @author thomas
 */
public class JsonToMapParser {

    private final String json;

    public JsonToMapParser(String json) {
        this.json = json;
    }
    
    @SuppressWarnings("unchecked")
	public Map<String, Object> getResult() {
        return new Genson().deserialize(json, Map.class);
    }
}
