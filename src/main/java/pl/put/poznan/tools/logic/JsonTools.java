package pl.put.poznan.tools.logic;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonTools {

    private final ObjectMapper mapper;

    public JsonTools() {
        this.mapper = new ObjectMapper();
    }

    public String minify(String json) {
        // TODO: Implement JSON minification logic
        return json;
    }

    public String beautify(String json) {
        try {
            JsonNode jsonNode = mapper.readTree(json);
                        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
            
        } catch (JsonProcessingException e) {
            return "{\n  \"error\": \"Invalid JSON input format.\"\n}";
        }
    }

    public String filterWhitelist(String json, List<String> keysToKeep) {
        // TODO: Implement logic to keep ONLY the specified keys
        return json;
    }

    public String filterBlacklist(String json, List<String> keysToRemove) {
        // TODO: Implement logic to remove ONLY the specified keys
        return json;
    }

    public String compare(String textA, String textB) {
        // TODO: Implement diff tool logic to find differences between two texts
        return "Differences will be shown here."; 
    }
}
