package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Decorator that filters a JSON object by removing the specified keys.
 * All other keys are preserved in the top-level object.
 */
public class BlacklistFilterDecorator extends JsonToolsDecorator {

    private static final Logger logger = LoggerFactory.getLogger(BlacklistFilterDecorator.class);
    private final ObjectMapper mapper = new ObjectMapper();
    private final List<String> keysToRemove;

    /**
     * Constructs a BlacklistFilterDecorator wrapping the given transformer.
     *
     * @param wrapped the transformer to wrap
     * @param keysToRemove the list of keys to remove from the JSON object
     */
    public BlacklistFilterDecorator(JsonToolsTransformer wrapped, List<String> keysToRemove) {
        super(wrapped);
        this.keysToRemove = keysToRemove;
    }

    /**
     * Applies the wrapped transformation first, then removes the
     * specified keys from the result.
     *
     * @param json the input JSON string
     * @return the filtered JSON string, or an error JSON if input is invalid
     */
    @Override
    public String transform(String json) {
        String result = wrapped.transform(json);
        logger.info("Applying blacklist filter removing keys: {}", keysToRemove);
        try {
            JsonNode jsonNode = mapper.readTree(result);
            if (jsonNode.isObject()) {
                ((ObjectNode) jsonNode).remove(keysToRemove);
            }
            return mapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            logger.debug("Blacklist filter failed: {}", e.getMessage());
            return "{\"error\":\"Invalid JSON input format.\"}";
        }
    }
}
