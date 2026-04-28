package pl.put.poznan.tools.logic;

import java.util.List;

public class JsonTools {

    public JsonTools() {
        // Constructor no longer requires a transforms array
    }

    public String minify(String json) {
        // TODO: Implement JSON minification logic
        return json;
    }

    public String beautify(String json) {
        // TODO: Implement JSON beautification (indentation) logic
        return json;
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
