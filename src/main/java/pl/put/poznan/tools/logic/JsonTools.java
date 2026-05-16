package pl.put.poznan.tools.logic;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Utility class providing standalone operations for processing and analyzing JSON.
 * <p>
 * This class includes basic formatting and filtering methods, as well as a 
 * structural comparison tool (diff) to find additions, removals, 
 * and changes between two JSON nodes.
 * </p>
 */
public class JsonTools {

    private final ObjectMapper mapper;

    public JsonTools() {
        this.mapper = new ObjectMapper();
    }

    public String minify(String json) {
        try {
            JsonNode jsonNode = mapper.readTree(json);
            return mapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            return "{\"error\":\"Invalid JSON input format.\"}";
        }
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
        try {
            JsonNode jsonNode = mapper.readTree(json);
            if (jsonNode.isObject()) {
                ((com.fasterxml.jackson.databind.node.ObjectNode) jsonNode).retain(keysToKeep);
            }
            return mapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            return "{\"error\":\"Invalid JSON input format.\"}";
        }
    }

    public String filterBlacklist(String json, List<String> keysToRemove) {
        try {
            JsonNode jsonNode = mapper.readTree(json);
            if (jsonNode.isObject()) {
                ((com.fasterxml.jackson.databind.node.ObjectNode) jsonNode).remove(keysToRemove);
            }
            return mapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            return "{\"error\":\"Invalid JSON input format.\"}";
        }
    }

    public String compare(String textA, String textB) {
        try {
            JsonNode nodeA = mapper.readTree(textA);
            JsonNode nodeB = mapper.readTree(textB);

            if (nodeA.equals(nodeB)) {
                return "No differences found.";
            }

            StringBuilder diff = new StringBuilder();
            compareNodes("", nodeA, nodeB, diff);
            return diff.toString();
        } catch (JsonProcessingException e) {
            return "Error: Invalid JSON input format.";
        }
    }

    private void compareNodes(String path, JsonNode nodeA, JsonNode nodeB, StringBuilder diff) {
        if (nodeA.equals(nodeB)) {
            return;
        }

        if (nodeA.isObject() && nodeB.isObject()) {
            java.util.Set<String> allKeys = new java.util.LinkedHashSet<>();
            nodeA.fieldNames().forEachRemaining(allKeys::add);
            nodeB.fieldNames().forEachRemaining(allKeys::add);

            for (String key : allKeys) {
                String childPath = path.isEmpty() ? key : path + "." + key;
                if (!nodeA.has(key)) {
                    diff.append("Added: ").append(childPath).append(" = ").append(nodeB.get(key)).append("\n");
                } else if (!nodeB.has(key)) {
                    diff.append("Removed: ").append(childPath).append(" = ").append(nodeA.get(key)).append("\n");
                } else {
                    compareNodes(childPath, nodeA.get(key), nodeB.get(key), diff);
                }
            }
        } else if (nodeA.isArray() && nodeB.isArray()) {
            int maxSize = Math.max(nodeA.size(), nodeB.size());
            for (int i = 0; i < maxSize; i++) {
                String childPath = path + "[" + i + "]";
                if (i >= nodeA.size()) {
                    diff.append("Added: ").append(childPath).append(" = ").append(nodeB.get(i)).append("\n");
                } else if (i >= nodeB.size()) {
                    diff.append("Removed: ").append(childPath).append(" = ").append(nodeA.get(i)).append("\n");
                } else {
                    compareNodes(childPath, nodeA.get(i), nodeB.get(i), diff);
                }
            }
        } else {
            diff.append("Changed: ").append(path).append(" | ").append(nodeA).append(" -> ").append(nodeB).append("\n");
        }
    }
}
