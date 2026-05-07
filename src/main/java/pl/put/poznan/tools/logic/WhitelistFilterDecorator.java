package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Decorator that filters a JSON object to keep only the specified keys.
 * All other keys are removed from the top-level object.
 */
public class WhitelistFilterDecorator extends JsonToolsDecorator {

    private static final Logger logger = LoggerFactory.getLogger(WhitelistFilterDecorator.class);
    private final ObjectMapper mapper = new ObjectMapper();
    private final List<String> keysToKeep;

    /**
     * Constructs a WhitelistFilterDecorator wrapping the given transformer.
     *
     * @param wrapped the transformer to wrap
     * @param keysToKeep the list of keys to retain in the JSON object
     */
    public WhitelistFilterDecorator(JsonToolsTransformer wrapped, List<String> keysToKeep) {
        super(wrapped);
        this.keysToKeep = keysToKeep;
    }

    /**
     * Applies the wrapped transformation first, then filters the result
     * to keep only the specified keys.
     *
     * @param json the input JSON string
     * @return the filtered JSON string, or an error JSON if input is invalid
     */
    @Override
    public String transform(String json) {
        String result = wrapped.transform(json);
        logger.info("Applying whitelist filter with keys: {}", keysToKeep);
        try {
            JsonNode jsonNode = mapper.readTree(result);
            if (jsonNode.isObject()) {
                ((ObjectNode) jsonNode).retain(keysToKeep);
            }
            return mapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            logger.debug("Whitelist filter failed: {}", e.getMessage());
            return "{\"error\":\"Invalid JSON input format.\"}";
        }
    }
}
